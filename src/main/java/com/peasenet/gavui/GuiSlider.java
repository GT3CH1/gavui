/*
 * Copyright (c) 2022-2022. Gavin Pease and contributors.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do so, subject to the
 * following conditions:
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
import com.peasenet.gavui.util.callbacks.GuiCallback;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

public class GuiSlider extends Gui {

    public void setCallback(GuiCallback callback) {
        this.callback = callback;
    }

    GuiCallback callback;

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    float value;

    /**
     * Creates a new GUI menu.
     *
     * @param topLeft - The top left corner of the gui.
     * @param width   - The width of the gui.
     * @param height  - The height of the gui.
     * @param title   - The title of the gui.
     */
    public GuiSlider(PointD topLeft, int width, int height, Text title) {
        super(topLeft, width, height, title);
    }

    @Override
    public void render(MatrixStack matrixStack, TextRenderer tr, int mouseX, int mouseY, float delta) {
        super.render(matrixStack, tr, mouseX, mouseY, delta);
        if (!isHidden())
            drawTickMark(matrixStack);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        if (button == 0 && mouseWithinGui(mouseX, mouseY) && !isHidden()) {
            setValue(mouseX);
            return true;
        }
        return false;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0 && mouseWithinGui(mouseX, mouseY) && !isHidden()) {
            setValue(mouseX);
            return true;
        }
        return false;
    }

    private void setValue(double mouseX) {
        value = (float) ((mouseX - getX()) / (getWidth() - 2));
        value = Math.max(0, Math.min(1, value));
        if (callback != null)
            callback.callback();
    }

    private void drawTickMark(MatrixStack stack) {
        var box = new BoxD(new PointD(((getX()) + ((getWidth() - 2) * value)), getY() - 0.25), 1, getHeight() + 0.75);
        GuiUtil.drawBox(Colors.WHITE.getAsFloatArray(), box, stack, 0.75f);
    }
}
