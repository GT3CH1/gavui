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

import com.peasenet.gavui.color.Color;
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
 * @version 7/6/2022
 * The base class for all gui elements.
 */
public class Gui {

    /**
     * The original position of the gui.
     */
    protected final BoxD defaultPosition;
    /**
     * The list of buttons(mods) in this dropdown.
     */
    protected final ArrayList<Gui> children = new ArrayList<>();
    /**
     * The title of the gui.
     */
    protected Text title;
    /**
     * The symbol to be drawn to the left of the end of the box (like a checkbox, empty box, or arrow).
     */
    char symbol;
    /**
     * The offset used for the symbol (x).
     */
    int symbolOffsetX = -10;
    /**
     * The offset used for the symbol (y).
     */
    int symbolOffsetY = 2;
    /**
     * Whether this element is a parent.
     */
    private boolean isParent = false;
    /**
     * The box that contains the gui.
     */
    private BoxD box;
    /**
     * The background color of the gui.
     */
    private Color backgroundColor = Colors.INDIGO;
    /**
     * Whether this gui is currently being dragged.
     */
    private boolean dragging;
    /**
     * Whether this gui is currently hidden.
     */
    private boolean hidden;
    /**
     * Whether this gui has been shrunk to fit a scrollbar.
     */
    private boolean shrunkForScroll = false;

    /**
     * Creates a new GUI menu.
     *
     * @param topLeft - The top left corner of the gui.
     * @param width   - The width of the gui.
     * @param height  - The height of the gui.
     * @param title   - The title of the gui.
     */
    public Gui(PointD topLeft, int width, int height, Text title) {
        box = new BoxD(topLeft, width, height);
        defaultPosition = BoxD.copy(box);
        this.title = title;
        dragging = false;
    }

    /**
     * Whether this element is a parent element.
     *
     * @return True if this element is a parent.
     */
    public boolean isParent() {
        return isParent;
    }

    /**
     * Sets whether this element is a parent.
     *
     * @param parent - True if we want this element to be a parent.
     */
    public void setParent(boolean parent) {
        isParent = parent;
    }

    /**
     * Sets the title of the gui.
     *
     * @param title - The title of the gui.
     */
    public void setTitle(Text title) {
        this.title = title;
    }

    /**
     * Sets the symbol that should be drawn on the right side of the gui.
     *
     * @param symbol - The symbol to draw.
     */
    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }

    /**
     * Clears all children from this gui.
     */
    public void clearChildren() {
        children.clear();
    }

    /**
     * Gets the children of this gui.
     *
     * @return The children of this gui.
     */
    public ArrayList<Gui> getChildren() {
        return children;
    }

    /**
     * Adds an child element to this gui.
     *
     * @param child - The child element to add.
     */
    public void addElement(Gui child) {
        if (children.isEmpty()) {
            child.setPosition(new PointD(getX2() + 100, getY2() + 1));
            children.add(child);
            return;
        }
        // get last gui
        Gui lastButton = children.get(children.size() - 1);
        var lastY = lastButton.getY2();
        // set new gui position
        child.setPosition(new PointD(getX(), lastY + 2));
        children.add(child);
    }

    /**
     * Gets whether this gui is currently hidden.
     *
     * @return Whether this gui is currently hidden.
     */
    public boolean isHidden() {
        return hidden;
    }

    /**
     * Hides this gui.
     */
    public void hide() {
        hidden = true;
    }

    /**
     * Shows this gui.
     */
    public void show() {
        hidden = false;
    }

    /**
     * Sets the background color to the given color.
     *
     * @param color - The color to set the background to.
     */
    public void setBackground(Color color) {
        backgroundColor = color;
    }

    /**
     * Gets the x coordinate for the top left corner of the dropdown.
     *
     * @return The x coordinate for the top left corner of the dropdown.
     */
    public double getX() {
        return box.getTopLeft().x();
    }

    /**
     * Gets the y coordinate for the top left corner of the dropdown.
     *
     * @return The y coordinate for the top left corner of the dropdown.
     */
    public double getY() {
        return box.getTopLeft().y();
    }

    /**
     * Gets the x coordinate for the bottom right corner of the dropdown.
     *
     * @return The x coordinate for the bottom right corner of the dropdown.
     */
    public double getX2() {
        return box.getBottomRight().x();
    }

    /**
     * Gets the y coordinate for the bottom right corner of the dropdown.
     *
     * @return The y coordinate for the bottom right corner of the dropdown.
     */
    public double getY2() {
        return box.getBottomRight().y();
    }

    /**
     * Gets the width of the dropdown.
     *
     * @return The width of the dropdown.
     */
    public double getWidth() {
        return box.getWidth();
    }

    /**
     * Sets the width of the gui.
     *
     * @param width - The width of the gui.
     */
    public void setWidth(double width) {
        box = new BoxD(box.getTopLeft(), width, box.getHeight());
    }

    /**
     * Shrinks this gui by 5.5 pixels to fit a scrollbar.
     */
    public void shrinkForScrollbar() {
        if (!shrunkForScroll) setWidth(getWidth() - 5.5);
        shrunkForScroll = true;
    }

    /**
     * Gets the height of the dropdown.
     *
     * @return The height of the dropdown.
     */
    public double getHeight() {
        return box.getHeight();
    }

    /**
     * Renders the clickable ui
     *
     * @param matrixStack The matrix stack used to draw boxes on screen.
     * @param tr          The text render to use to draw text
     * @param mouseX      The x coordinate of the mouse.
     * @param mouseY      The y coordinate of the mouse.
     * @param delta       The change in time since the last render.
     */

    public void render(MatrixStack matrixStack, TextRenderer tr, int mouseX, int mouseY, float delta) {
        if (isHidden()) return;
        GuiUtil.drawBox(backgroundColor.getAsFloatArray(), (int) getX(), (int) getY(), (int) getX2(), (int) getY2() + 1, matrixStack);
        tr.draw(matrixStack, title, (int) getX() + 2, (int) getY() + 2, (GavUISettings.getColor("gui.color.foreground")).getAsInt());
        if (symbol != '\0')
            tr.draw(matrixStack, String.valueOf(symbol), (int) getX2() + symbolOffsetX, (int) getY() + symbolOffsetY, (GavUISettings.getColor("gui.color.foreground")).getAsInt());
        GuiUtil.drawOutline(Colors.WHITE.getAsFloatArray(), (int) getX(), (int) getY(), (int) getX2(), (int) getY2() + 1, matrixStack);
    }

    /**
     * Checks whether the mouse is clicked.
     *
     * @param mouseX - The x coordinate of the mouse.
     * @param mouseY - The y coordinate of the mouse.
     * @param button - The button that was clicked.
     * @return Whether the mouse was clicked.
     */
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        return false;
    }

    /**
     * Checks whether the mouse was dragged.
     *
     * @param mouseX - The x coordinate of the mouse.
     * @param mouseY - The y coordinate of the mouse.
     * @param button - The button that was dragged.
     * @param deltaX - The change in x coordinate.
     * @param deltaY - The change in y coordinate.
     * @return Whether the mouse was dragged.
     */
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        return false;
    }

    /**
     * Checks whether the mouse was scrolled
     *
     * @param mouseX - The x coordinate of the mouse.
     * @param mouseY - The y coordinate of the mouse.
     * @param amount - The amount of scroll.
     * @return Whether the mouse was scrolled.
     */
    public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
        return false;
    }

    /**
     * Whether the current window is being dragged.`
     *
     * @return True if the current window is being dragged.
     */
    public boolean isDragging() {
        return dragging;
    }

    /**
     * Sets whether the current window is being dragged.
     *
     * @param dragging - Whether the current window is being dragged.
     */
    public void setDragging(boolean dragging) {
        this.dragging = dragging;
    }

    /**
     * Resets the position to the default position.
     */
    public void resetPosition() {
        box = BoxD.copy(defaultPosition);
    }

    /**
     * Gets the current location of the top left corner of the gui.
     *
     * @return The current location of the top left corner of the gui.
     */
    public PointD getPosition() {
        return box.getTopLeft();
    }

    /**
     * Sets the top left corner of the gui element to the given point.
     *
     * @param position - The point to set the top left corner of the gui element to.
     */
    public void setPosition(PointD position) {
        box.setTopLeft(position);
    }

    /**
     * Sets the middle of the gui element to the given point.
     *
     * @param position - The point to set the middle of the gui element to.
     */
    public void setMidPoint(PointD position) {
        box.setMiddle(position);
    }

    /**
     * Gets whether the mouse coordinates are within the bounds of the gui.
     *
     * @param mouseX - The x coordinate of the mouse.
     * @param mouseY - The y coordinate of the mouse.
     * @return Whether the mouse coordinates are within the bounds of the gui.
     */
    public boolean mouseWithinGui(double mouseX, double mouseY) {
        return mouseX >= getX() && mouseX <= getX2() && mouseY >= getY() && mouseY <= getY2();
    }

    /**
     * Gets the background color of the gui.
     *
     * @return The background color of the gui.
     */
    public Color getBackgroundColor() {
        return backgroundColor;
    }

    /**
     * Whether this element has children.
     *
     * @return True if this element has children.
     */
    public boolean hasChildren() {
        return !children.isEmpty();
    }
}
