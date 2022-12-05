/*
 * Copyright (c) 2022. Gavin Pease and contributors.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 *  of the Software, and to permit persons to whom the Software is furnished to do so, subject to the
 *  following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial
 * portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT
 * OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */

package com.peasenet.gavui;

import com.peasenet.gavui.color.Colors;
import com.peasenet.gavui.math.BoxD;
import com.peasenet.gavui.math.PointD;
import com.peasenet.gavui.util.GuiUtil;
import com.peasenet.gavui.util.GavUISettings;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

import java.util.ArrayList;

/**
 * @author gt3ch1
 * @version 7/1/2022
 * A gui element that allows the user to drag, drop, and scroll through a list of items.
 * By default, this element will show four children at once. There are (totalChildren/toBeShown) pages of children.
 */
public class GuiScroll extends GuiDropdown {

    /**
     * The maximum number of children that can be displayed at once.
     */
    private int maxChildren;

    /**
     * The total number of pages that can be drawn.
     */
    private int numPages;

    /**
     * The current page.
     */
    private int page;

    /**
     * Creates a new scroll like UI element.
     *
     * @param position    - The position of the element.
     * @param width       - The width of the element.
     * @param height      - The height of the element.
     * @param title       - The title of the element.
     * @param maxChildren - The maximum number of children that can be displayed.
     * @param children    - The children of the element.
     */
    public GuiScroll(PointD position, int width, int height, Text title, int maxChildren, ArrayList<Gui> children) {
        super(position, width, height, title);
        children.forEach(this::addElement);
        this.maxChildren = Math.min(children.size(), maxChildren);
        numPages = (int) Math.ceil((double) children.size() / (double) maxChildren);
        page = 0;
    }


    /**
     * Creates a new scroll like UI element.
     *
     * @param position - The position of the element.
     * @param width    - The width of the element.
     * @param height   - The height of the element.
     * @param title    - The title of the element.
     */
    public GuiScroll(PointD position, int width, int height, Text title) {
        super(position, width, height, title);
        maxChildren = 4;
        numPages = (int) Math.ceil((double) children.size() / (double) maxChildren);
        page = 0;
    }

    /**
     * Creates a new scroll like UI element, at the default point of (0,0).
     *
     * @param width  - The width.
     * @param height - The height.
     * @param title  - The title.
     */
    public GuiScroll(int width, int height, Text title) {
        this(new PointD(0, 0), width, height, title);
    }

    @Override
    public void render(MatrixStack matrixStack, TextRenderer tr, int mouseX, int mouseY, float delta) {
        if (isHidden()) return;
        if (isParent()) setBackground(GavUISettings.getColor("gui.color.category"));
        else setBackground(GavUISettings.getColor("gui.color.background"));
        GuiUtil.drawBox(getBackgroundColor().getAsFloatArray(), (int) getX(), (int) getY(), (int) getX2(), (int) getY2() + 1, matrixStack);
        tr.draw(matrixStack, title, (int) getX() + 2, (int) getY() + 2, (GavUISettings.getColor("gui.color.foreground")).getAsInt());
        updateSymbol();
        tr.draw(matrixStack, String.valueOf(symbol), (int) getX2() + symbolOffsetX, (int) getY() + symbolOffsetY, (GavUISettings.getColor("gui.color.foreground")).getAsInt());
        GuiUtil.drawOutline(Colors.WHITE.getAsFloatArray(), (int) getX(), (int) getY(), (int) getX2(), (int) getY2() + 1, matrixStack);

        if (!isOpen()) return;
        resetChildPos();
        children.forEach(child -> {
            if (!child.isParent() && !(child instanceof GuiCycle))
                child.setBackground(GavUISettings.getColor("gui.color.background"));
            child.render(matrixStack, tr, mouseX, mouseY, delta);
        });
        if (shouldDrawScrollBar()) {
            drawScrollBox(matrixStack);
            drawScrollBar(matrixStack);
        }
    }

    @Override
    public boolean mouseScrolled(double x, double y, double scroll) {
        if (hasChildren() && isOpen()) {
            for (var gui : children) {
                if (!gui.isHidden() && gui.mouseWithinGui(x, y) && gui instanceof GuiScroll) {
                    gui.mouseScrolled(x, y, scroll);
                    if (((GuiScroll) gui).isOpen())
                        ((GuiScroll) gui).doScroll(scroll);
                    else {
                        doScroll(scroll);
                    }
                    return true;
                }
            }
        }
        if (mouseWithinGui(x, y))
            doScroll(scroll);
        return super.mouseScrolled(x, y, scroll);
    }

    /**
     * Scrolls the list either up or down based off the given scroll value. A positive value will scroll up,
     * where a negative value will scroll down.
     *
     * @param scroll - The scroll value.
     */
    private void doScroll(double scroll) {
        if (!isOpen()) return;
        if (scroll > 0) scrollUp();
        else scrollDown();
        resetChildPos();
    }

    /**
     * Resets all of the children's positions.
     */
    private void resetChildPos() {
        var modIndex = 0;
        children.forEach(Gui::hide);
        for (int i = page * maxChildren; i < page * maxChildren + maxChildren; i++) {
            if (i >= children.size()) break;
            var gui = children.get(i);
            gui.setPosition(new PointD(getX() + 5, (gui.getHeight() + 2) * (modIndex - 1) + (getY() + getHeight()) + 2));
            switch (getDirection()) {
                case DOWN -> gui.setPosition(new PointD(getX(), getY2() + 2 + (modIndex * 12)));
                case RIGHT -> gui.setPosition(new PointD(getX2() + 0, getY() + (modIndex * 12)));
            }
            if (shouldDrawScrollBar()) gui.shrinkForScrollbar();
            gui.show();
            modIndex++;
        }
    }

    /**
     * Scrolls the page "up" by one.
     */
    protected void scrollUp() {
        if (page > 0) page--;
    }

    /**
     * Scrolls the page "down" by one.
     */
    protected void scrollDown() {
        if (page < numPages - 1) page++;
    }

    /**
     * Whether the scrollbar should be drawn.
     *
     * @return Whether the scrollbar should be drawn.
     */
    private boolean shouldDrawScrollBar() {
        return children.size() > maxChildren;
    }

    /**
     * Draws the box that contains the scrollbar.
     *
     * @param matrixStack - The matrix stack.
     */
    private void drawScrollBox(MatrixStack matrixStack) {
        var scrollBoxX = getX() + getWidth() - 5;
        var scrollBoxY = (getY2()) + 2;
        var scrollBoxHeight = getScrollBoxHeight();
        if (getDirection() == Direction.RIGHT) {
            var firstChild = children.get(0);
            scrollBoxX = firstChild.getX2() + 0;
            scrollBoxY = firstChild.getY();
        }
        GuiUtil.drawBox(Colors.BLACK.getAsFloatArray(), new BoxD(new PointD(scrollBoxX, scrollBoxY), 5, scrollBoxHeight), matrixStack);
        GuiUtil.drawOutline(GavUISettings.getColor("gui.color.foreground").getAsFloatArray(), new BoxD(new PointD(scrollBoxX, scrollBoxY), 5, scrollBoxHeight), matrixStack);
    }

    /**
     * Draws the scrollbar.
     *
     * @param matrixStack - The matrix stack.
     */
    private void drawScrollBar(MatrixStack matrixStack) {
        var scrollBoxHeight = getScrollBoxHeight();
        var firstChild = children.get(0);
        var scrollBarY = (scrollBoxHeight * (page / (double) numPages)) + getY2() + 3;
        var scrollBarX = getX2() - 5.5/2;
        var scrollBarY2 = ((scrollBarY) + (scrollBoxHeight / (numPages)));
        
        if (getDirection() == Direction.RIGHT) {
            // set scrollbarY to (1/page) * scrollBoxHeight
            scrollBarY = (scrollBoxHeight * (page / (double) numPages)) + firstChild.getY() + 1;
            scrollBarX = firstChild.getX2() + 2;
            scrollBarY2 = ((scrollBarY) + (scrollBoxHeight / (numPages)));
        }
        
        GuiUtil.drawBox(Colors.WHITE.getAsFloatArray(), (int) scrollBarX - 1, (int) scrollBarY, (int) scrollBarX + 2, (int) scrollBarY2 - 2, matrixStack);

    }

    /**
     * Gets the height of the scrollbox.
     * a    *
     *
     * @return The height of the scrollbox.
     */
    private double getScrollBoxHeight() {
        return (maxChildren) * getHeight() + maxChildren + (maxChildren - 1);
    }

    @Override
    public void addElement(Gui gui) {
        super.addElement(gui);
        maxChildren = Math.min(children.size(), 4);
        numPages = (int) Math.ceil((double) children.size() / (double) maxChildren);
    }

    @Override
    public boolean mouseClicked(double x, double y, int button) {
        if (isHidden()) return false;
        if (clickedOnChild(x, y, button)) return true;
        if (mouseWithinGui(x, y)) {
            toggleMenu();
            return true;
        }
        return false;
    }

    /**
     * Whether the given mouse coordinates are within the bounds of the child elements. This will return true if
     * a single child is clicked on.
     *
     * @param x      - The mouse x coordinate.
     * @param y      - The mouse y coordinate.
     * @param button - The mouse button.
     * @return True if a child is clicked on, false otherwise.
     */
    private boolean clickedOnChild(double x, double y, int button) {
        for (int i = page * maxChildren; i < page * maxChildren + maxChildren; i++) {
            if (i >= children.size()) break;
            var gui = children.get(i);
            if (gui.isHidden()) return false;
            if (gui.mouseClicked(x, y, button)) {
                for (Gui child : children)
                    if (child instanceof GuiDropdown dropdown && !child.equals(gui) && dropdown.isOpen())
                        dropdown.toggleMenu();
                gui.show();
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        if (super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY)) {
            resetChildPos();
            return true;
        }
        return false;
    }
}
