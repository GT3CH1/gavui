package com.peasenet.gavui.util;

import com.mojang.blaze3d.systems.RenderSystem;
import com.peasenet.gavui.math.BoxD;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Matrix4f;

public class GuiUtil {
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

    public static void drawOutline(float[] acColor, BoxD box, MatrixStack matrixStack) {
        drawOutline(acColor, (int) box.getTopLeft().x(), (int) box.getTopLeft().y(), (int) box.getBottomRight().x(), (int) box.getBottomRight().y(), matrixStack);
    }

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
