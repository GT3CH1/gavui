package com.peasenet.gavui.util;

import com.mojang.blaze3d.systems.RenderSystem;
import com.peasenet.gavui.math.BoxD;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Matrix4f;


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
    public static void drawBox(float[] acColor, BoxD box, MatrixStack matrixStack) {
        int xt1 = (int) box.getTopLeft().x();
        int yt1 = (int) box.getTopLeft().y();
        int xt2 = (int) box.getBottomRight().x();
        int yt2 = (int) box.getBottomRight().y();
        RenderSystem.setShader(GameRenderer::getPositionShader);
        RenderSystem.enableBlend();
        var matrix = matrixStack.peek().getPositionMatrix();
        var bufferBuilder = Tessellator.getInstance().getBuffer();
        RenderSystem.setShaderColor(acColor[0], acColor[1], acColor[2], 0.5f);
        bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION);
        drawBox(xt1, yt1, xt2, yt2, matrix, bufferBuilder);
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
        bufferBuilder.vertex(matrix, xt1, yt1, 0).next();
        bufferBuilder.vertex(matrix, xt1, yt2, 0).next();
        bufferBuilder.vertex(matrix, xt2, yt2, 0).next();
        bufferBuilder.vertex(matrix, xt2, yt1, 0).next();
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
        RenderSystem.setShader(GameRenderer::getPositionShader);
        RenderSystem.enableBlend();
        var matrix = matrixStack.peek().getPositionMatrix();
        var bufferBuilder = Tessellator.getInstance().getBuffer();
        RenderSystem.setShaderColor(acColor[0], acColor[1], acColor[2], 0.5f);
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
    public static void drawOutline(float[] acColor, BoxD box, MatrixStack matrixStack) {
        drawOutline(acColor, (int) box.getTopLeft().x(), (int) box.getTopLeft().y(), (int) box.getBottomRight().x(), (int) box.getBottomRight().y(), matrixStack);
    }

    /**
     * Draws an outline of the given coordinates with the given color.
     *
     * @param acColor     - The color to draw the outline with.
     * @param xt1         - The x coordinate of the top left corner of the box.
     * @param yt1         - The y coordinate of the top left corner of the box.
     * @param xt2         - The x coordinate of the bottom right corner of the box.
     * @param yt2         - The y coordinate of the bottom right corner of the box.
     * @param matrixStack - The matrix stack to draw with.
     */
    public static void drawOutline(float[] acColor, int xt1, int yt1, int xt2, int yt2, MatrixStack matrixStack) {
        RenderSystem.setShader(GameRenderer::getPositionShader);
        RenderSystem.enableBlend();
        var matrix = matrixStack.peek().getPositionMatrix();
        var bufferBuilder = Tessellator.getInstance().getBuffer();
        RenderSystem.setShaderColor(acColor[0], acColor[1], acColor[2], 1.0F);
        bufferBuilder.begin(VertexFormat.DrawMode.DEBUG_LINE_STRIP, VertexFormats.POSITION);
        drawBox(xt1, yt1, xt2, yt2, matrix, bufferBuilder);
        bufferBuilder.vertex(matrix, xt1, yt1, 0).next();
        Tessellator.getInstance().draw();
    }
}
