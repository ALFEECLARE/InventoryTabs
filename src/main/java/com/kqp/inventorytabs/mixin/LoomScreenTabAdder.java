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
import net.minecraft.client.gui.screens.inventory.LoomScreen;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
@Mixin(LoomScreen.class)
public class LoomScreenTabAdder {
    @Inject(method = "renderBg", at = @At(value = "HEAD"))
    protected void drawBackgroundTabs(GuiGraphics gui, float delta, int mouseX, int mouseY,
                                      CallbackInfo callbackInfo) {
        if (InventoryTabsClient.shouldRenderTabs((LoomScreen)(Object)this)) {
            Minecraft client = Minecraft.getInstance();
            TabManager tabManager = ((TabManagerContainer) client).getTabManager();

            tabManager.tabRenderer.renderBackground(gui);
        }
    }
}
