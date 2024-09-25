package com.kqp.inventorytabs.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.kqp.inventorytabs.init.InventoryTabsClient;
import com.kqp.inventorytabs.interf.TabManagerContainer;
import com.kqp.inventorytabs.tabs.TabManager;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.StonecutterScreen;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
@Mixin(StonecutterScreen.class)
public class StonecutterScreenTabAdder {
    @Inject(method = "renderBg", at = @At(value = "HEAD"))
    protected void drawBackgroundTabs(GuiGraphics gui, float delta, int mouseX, int mouseY,
                                      CallbackInfo callbackInfo) {
        if (InventoryTabsClient.shouldRenderTabs((StonecutterScreen)(Object)this)) {
            Minecraft client = Minecraft.getInstance();
            TabManager tabManager = ((TabManagerContainer) client).getTabManager();

            tabManager.tabRenderer.renderBackground(gui);
        }
    }
}
