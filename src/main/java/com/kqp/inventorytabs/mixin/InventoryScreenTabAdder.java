package com.kqp.inventorytabs.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.kqp.inventorytabs.init.InventoryTabsClient;
import com.kqp.inventorytabs.tabs.TabManager;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractRecipeBookScreen;

@Mixin(AbstractRecipeBookScreen.class)
public class InventoryScreenTabAdder {

    //AbstractRecipeBookScreenのextractRenderStateがsuperのextractRenderStateを呼ばなくなったので切り出し
    @Inject(method = "extractRenderState", at = @At("TAIL"))
    protected void drawForegroundTabs(GuiGraphicsExtractor gui, int mouseX, int mouseY, float delta,
                                      CallbackInfo callbackInfo) {
        if (InventoryTabsClient.shouldRenderTabs((Screen)((Object)this))) {
            TabManager tabManager = TabManager.getInstance();

            tabManager.tabRenderer.renderForeground(gui, mouseX, mouseY);
            tabManager.tabRenderer.renderHoverTooltips(gui, mouseX, mouseY);
        }
    }
}
