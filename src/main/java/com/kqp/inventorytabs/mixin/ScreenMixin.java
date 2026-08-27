package com.kqp.inventorytabs.mixin;

import javax.annotation.Nullable;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.kqp.inventorytabs.init.InventoryTabsClient;
import com.kqp.inventorytabs.tabs.TabManager;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;

@Mixin(Screen.class)
public class ScreenMixin {

    @Shadow @Nullable protected Minecraft minecraft;

    @Inject(method = "resize", at = @At("HEAD"))
    private void inventorytabs_resize(int pWidth, int pHeight, CallbackInfo ci) {
        if (minecraft != null && minecraft.level != null && ((Object)this) instanceof AbstractContainerScreen<?> screen && TabManager.getInstance().getCurrentScreen() == screen) {
            var tabManager = TabManager.getInstance();
            tabManager.isResized = true;
        }
    }


    //@Inject(method = "extractBackground", at = @At("HEAD"))
    @Inject(method = "extractBackground", at = @At(value = "INVOKE", target="Lnet/minecraft/client/gui/Gui;extractDeferredSubtitles()V"))
    protected void drawBackgroundTabs(GuiGraphicsExtractor gui, int mouseX, int mouseY, float delta,
            CallbackInfo callbackInfo) {
        if (InventoryTabsClient.shouldRenderTabs((Screen)((Object)this))) {
            //if (!screenDoesDumbBlock()) {
                TabManager tabManager = TabManager.getInstance();

                tabManager.tabRenderer.renderBackground(gui);
            //}
        }
    }
}
