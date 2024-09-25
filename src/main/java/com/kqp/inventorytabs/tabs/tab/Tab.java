package com.kqp.inventorytabs.tabs.tab;

import com.kqp.inventorytabs.mixin.accessor.ScreenAccessor;
import com.kqp.inventorytabs.tabs.render.TabRenderInfo;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

/**
 * Base interface for tabs.
 */
@OnlyIn(Dist.CLIENT)
public abstract class Tab {
    private final ItemStack renderItemStack;

    protected Tab(ItemStack renderItemStack) {
        this.renderItemStack = renderItemStack;
    }

    /**
     * Fires whenever the tab is clicked.
     */
    public abstract void open();

    /**
     * Returns true if the tab should stop being displayed. Should be synced up with
     * the provider that provides this tab.
     *
     * @return
     */
    public abstract boolean shouldBeRemoved();

    /**
     * Returns the text that's displayed when hovering over the tab.
     *
     * @return
     */
    public abstract Component getHoverText();

    /**
     * Called when the screen associated with the tab is closed.
     */
    public void onClose() {
    }

    /**
     * Returns the tab's priority when being displayed. The player's inventory is at
     * 100.
     *
     * @return
     */
    public int getPriority() {
        return 0;
    }

    /**
     * Renders the tab's icon
     *
     * @param poseStack      PoseStack
     * @param tabRenderInfo TabRenderInfo
     * @param currentScreen AbstractContainerScreen
     */
    @OnlyIn(Dist.CLIENT)
    public void renderTabIcon(GuiGraphics gui, TabRenderInfo tabRenderInfo, AbstractContainerScreen<?> currentScreen) {
        //ItemRenderer itemRenderer = ((ScreenAccessor) currentScreen).getItemRenderer();
        Font font = ((ScreenAccessor) currentScreen).getFont();
        // itemRenderer.blitOffset = 100.0F;
        // RenderSystem.enableRescaleNormal();
        gui.renderItem(renderItemStack, tabRenderInfo.itemX, tabRenderInfo.itemY);
    }
}
