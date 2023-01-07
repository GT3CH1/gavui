/*
 * Copyright (c) 2022-2023. Gavin Pease and contributors.
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
import com.peasenet.gavui.color.Color;
import com.peasenet.gavui.math.BoxF;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import org.joml.Matrix4f;

/**
 * @author gt3ch1
 * @version 01/07/2023
 * A utility class for drawing gui elements.
 */
public class GuiUtil {
    /**
     * Draws a box around the given box, with an alpha of 1f.
     *
     * @param c           - The color to draw the box with.
     * @param box         - The box to draw.
     * @param matrixStack - The matrix stack to draw with.
     */
    public static void drawBox(Color c, BoxF box, MatrixStack matrixStack) {
        drawBox(c, box, matrixStack, 1f);
    }

    /**
     * @param c           - The color to draw the box with.
     * @param box         - The box to draw.
     * @param matrixStack - The matrix stack to draw with.
     * @param alpha       - The alpha value to draw with.
     */
    public static void drawBox(Color c, BoxF box, MatrixStack matrixStack, float alpha) {
        var acColor = c.getAsFloatArray();
        RenderSystem.setShader(GameRenderer::getPositionProgram);
        RenderSystem.enableBlend();
        RenderSystem.setShaderColor(acColor[0], acColor[1], acColor[2], alpha);

        var bufferBuilder = Tessellator.getInstance().getBuffer();
        bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION);
        var matrix = matrixStack.peek().getPositionMatrix();
        drawBox(box, matrix, bufferBuilder);
    }

    /**
     * Draws an outline of the given box with the given color, with an alpha of 1f.
     *
     * @param c           - The color to draw the outline with.
     * @param box         - The outline of a box to draw.
     * @param matrixStack - The matrix stack to draw with.
     */
    public static void drawOutline(Color c, BoxF box, MatrixStack matrixStack) {
        drawOutline(c, box, matrixStack, 1.0f);
    }

    /**
     * Draws an outline of the given box with the given color.
     *
     * @param c           - The color to draw the outline with.
     * @param box         - The outline of a box to draw.
     * @param matrixStack - The matrix stack to draw with.
     * @param alpha       - The alpha value to draw with.
     */
    public static void drawOutline(Color c, BoxF box, MatrixStack matrixStack, float alpha) {
        var acColor = c.getAsFloatArray();
        RenderSystem.setShader(GameRenderer::getPositionProgram);
        RenderSystem.enableBlend();
        RenderSystem.setShaderColor(acColor[0], acColor[1], acColor[2], alpha);

        var matrix = matrixStack.peek().getPositionMatrix();
        var bufferBuilder = Tessellator.getInstance().getBuffer();
        bufferBuilder.begin(VertexFormat.DrawMode.DEBUG_LINE_STRIP, VertexFormats.POSITION);
        drawBox(box, matrix, bufferBuilder);
    }

    /**
     * Draws a box around the given box.
     *
     * @param box           - The box to draw.
     * @param matrix        - The matrix to draw with.
     * @param bufferBuilder - The buffer builder to draw with.
     */
    private static void drawBox(BoxF box, Matrix4f matrix, BufferBuilder bufferBuilder) {
        var xt1 = box.getTopLeft().x();
        var yt1 = box.getTopLeft().y();
        var xt2 = box.getBottomRight().x();
        var yt2 = box.getBottomRight().y();
        bufferBuilder.vertex(matrix, xt1, yt1, 0).next();
        bufferBuilder.vertex(matrix, xt1, yt2, 0).next();
        bufferBuilder.vertex(matrix, xt2, yt2, 0).next();
        bufferBuilder.vertex(matrix, xt2, yt1, 0).next();
        bufferBuilder.vertex(matrix, xt1, yt1, 0).next();
        Tessellator.getInstance().draw();
    }
}
