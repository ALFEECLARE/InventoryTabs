package com.kqp.inventorytabs.util;

import java.nio.DoubleBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;

import com.mojang.blaze3d.platform.InputConstants;

import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

/**
 * Utility class for manipulating the client's mouse position.
 */
@OnlyIn(Dist.CLIENT)
public class MouseUtil {
    private static double mouseX = -1D, mouseY = -1D;

    public static void push() {
        mouseX = getMouseX();
        mouseY = getMouseY();
    }

    public static void tryPop() {
        if (mouseX != -1D && mouseY != -1D) {
            InputConstants.grabOrReleaseMouse(Minecraft.getInstance().getWindow(), 212993, mouseX,
                    mouseY);

            mouseX = -1D;
            mouseY = -1D;
        }
    }

    public static double getMouseX() {
        DoubleBuffer mouseBufX = BufferUtils.createDoubleBuffer(1);
        DoubleBuffer mouseBufY = BufferUtils.createDoubleBuffer(1);
        GLFW.glfwGetCursorPos(Minecraft.getInstance().getWindow().handle(), mouseBufX, mouseBufY);

        return mouseBufX.get(0);
    }

    public static double getMouseY() {
        DoubleBuffer mouseBufX = BufferUtils.createDoubleBuffer(1);
        DoubleBuffer mouseBufY = BufferUtils.createDoubleBuffer(1);
        GLFW.glfwGetCursorPos(Minecraft.getInstance().getWindow().handle(), mouseBufX, mouseBufY);

        return mouseBufY.get(0);
    }
}
