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

package com.peasenet.gavui.util;

import com.mojang.blaze3d.systems.RenderSystem;
import com.peasenet.gavui.math.BoxF;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import org.joml.Matrix4f;


/**
 * @author gt3ch1
 * @version 7/13/2022
 * A utility class for drawing gui elements.
 */
public class GuiUtil {
    /**
     * @param acColor     - The color to draw the box with.
     * @param box         - The box to draw.
     * @param matrixStack - The matrix stack to draw with.
     */
    public static void drawBox(float[] acColor, BoxF box, MatrixStack matrixStack) {
        drawBox(acColor, box, matrixStack, GavUISettings.getFloat("gui.alpha"));
    }

    /**
     * @param acColor     - The color to draw the box with.
     * @param box         - The box to draw.
     * @param matrixStack - The matrix stack to draw with.
     */
    public static void drawBox(float[] acColor, BoxF box, MatrixStack matrixStack, float alpha) {
        float xt1 = (float) box.getTopLeft().x();
        float yt1 = (float) box.getTopLeft().y();
        float xt2 = (float) box.getBottomRight().x();
        float yt2 = (float) box.getBottomRight().y();
        RenderSystem.setShader(GameRenderer::getPositionProgram);
        RenderSystem.enableBlend();
        var matrix = matrixStack.peek().getPositionMatrix();
        var bufferBuilder = Tessellator.getInstance().getBuffer();
        RenderSystem.setShaderColor(acColor[0], acColor[1], acColor[2], alpha);
        bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION);
        bufferBuilder.vertex(matrix, xt1, yt1, 0).next();
        bufferBuilder.vertex(matrix, xt1, yt2, 0).next();
        bufferBuilder.vertex(matrix, xt2, yt2, 0).next();
        bufferBuilder.vertex(matrix, xt2, yt1, 0).next();
        bufferBuilder.vertex(matrix, xt1, yt1, 0).next();
        Tessellator.getInstance().draw();
    }

    /**
     * Draws a box with the given coordinates.
     *
     * @param xt1           - The x coordinate of the top left corner.
     * @param yt1           - The y coordinate of the top left corner.
     * @param xt2           - The x coordinate of the bottom right corner.
     * @param yt2           - The y coordinate of the bottom right corner.
     * @param matrix        - The matrix to draw with.
     * @param bufferBuilder - The buffer to draw with.
     */
    public static void drawBox(int xt1, int yt1, int xt2, int yt2, Matrix4f matrix, BufferBuilder bufferBuilder) {


    }

    /**
     * Draws a box on screen.
     *
     * @param acColor     The color of the box as a 4 point float array.
     * @param xt1         The x coordinate of the top left corner of the box.
     * @param yt1         The y coordinate of the top left corner of the box.
     * @param xt2         The x coordinate of the bottom right corner of the box.
     * @param yt2         The y coordinate of the bottom right corner of the box.
     * @param matrixStack The matrix stack used to draw boxes on screen.
     */
    public static void drawBox(float[] acColor, int xt1, int yt1, int xt2, int yt2, MatrixStack matrixStack) {
        RenderSystem.setShader(GameRenderer::getPositionProgram);
        RenderSystem.enableBlend();
        var matrix = matrixStack.peek().getPositionMatrix();
        var bufferBuilder = Tessellator.getInstance().getBuffer();
        RenderSystem.setShaderColor(acColor[0], acColor[1], acColor[2], GavUISettings.getFloat("gui.alpha"));
        bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION);
        drawBox(xt1, yt1, xt2, yt2, matrix, bufferBuilder);
        Tessellator.getInstance().draw();
    }

    /**
     * Draws an outline of the given box with the given color.
     *
     * @param acColor     - The color to draw the outline with.
     * @param box         - The outline of a box to draw.
     * @param matrixStack - The matrix stack to draw with.
     */
    public static void drawOutline(float[] acColor, BoxF box, MatrixStack matrixStack) {
        RenderSystem.setShader(GameRenderer::getPositionProgram);
        RenderSystem.enableBlend();
        var matrix = matrixStack.peek().getPositionMatrix();
        var bufferBuilder = Tessellator.getInstance().getBuffer();
        RenderSystem.setShaderColor(acColor[0], acColor[1], acColor[2], 1.0F);
        bufferBuilder.begin(VertexFormat.DrawMode.DEBUG_LINE_STRIP, VertexFormats.POSITION);

        var xt1 = (float) box.getTopLeft().x();
        var yt1 = (float) box.getTopLeft().y();
        var xt2 = (float) box.getBottomRight().x();
        var yt2 = (float) box.getBottomRight().y();
        bufferBuilder.vertex(matrix, xt1, yt1, 0).next();
        bufferBuilder.vertex(matrix, xt1, yt2, 0).next();
        bufferBuilder.vertex(matrix, xt2, yt2, 0).next();
        bufferBuilder.vertex(matrix, xt2, yt1, 0).next();
        bufferBuilder.vertex(matrix, xt1, yt1, 0).next();
        Tessellator.getInstance().draw();

    }


    /**
     * Tessellator.getInstance().draw();                                                       * Draws an outline of the given coordinates with the given color.
     *
     * @param acColor     - The color to draw the outline with.
     * @param xt1         - The x coordinate of the top left corner of the box.
     * @param yt1         - The y coordinate of the top left corner of the box.
     * @param xt2         - The x coordinate of the bottom right corner of the box.
     * @param yt2         - The y coordinate of the bottom right corner of the box.
     * @param matrixStack - The matrix stack to draw with.
     */
    public static void drawOutline(float[] acColor, int xt1, int yt1, int xt2, int yt2, MatrixStack matrixStack) {


    }
}
