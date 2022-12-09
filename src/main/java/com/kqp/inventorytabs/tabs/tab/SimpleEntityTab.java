package com.kqp.inventorytabs.tabs.tab;

import com.kqp.inventorytabs.init.InventoryTabsConfig;
import com.kqp.inventorytabs.mixin.accessor.ScreenAccessor;
import com.kqp.inventorytabs.tabs.provider.SimpleBlockTabProvider;
import com.kqp.inventorytabs.tabs.render.TabRenderInfo;
import com.kqp.inventorytabs.util.EntityUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;

public class SimpleEntityTab extends Tab {
    public final ResourceLocation entityId;
    public final Entity entity;

    public SimpleEntityTab(Entity entity) {
        super(entity.getPickResult() != null ? entity.getPickResult() : new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("barrier"))));
        this.entity = entity;
        this.entityId = EntityType.getKey(entity.getType());
    }

    @Override
    public void open() {
        AbstractClientPlayer player = Minecraft.getInstance().player;
        Minecraft.getInstance().gameMode.interact(player, entity, player.getUsedItemHand());
    }

    @Override
    public boolean shouldBeRemoved() {
        if (entity.isRemoved()) {
            return true;
        }
        if (InventoryTabsConfig.doSightChecksFlag.get()) {
            var player = Minecraft.getInstance().player;
            if (!EntityUtil.canInteract(player, entity, player.getReachDistance())) {
                return true;
            }
        }
        return entity.position().distanceTo(Minecraft.getInstance().player.position()) > SimpleBlockTabProvider.SEARCH_DISTANCE * SimpleBlockTabProvider.SEARCH_DISTANCE;
    }

    @Override
    public Component getHoverText() {
        return entity.getName();
    }

    @Override
    public void renderTabIcon(PoseStack poseStack, TabRenderInfo tabRenderInfo, AbstractContainerScreen<?> currentScreen) {
        super.renderTabIcon(poseStack, tabRenderInfo, currentScreen);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SimpleEntityTab tab = (SimpleEntityTab) o;
        if (!Objects.equals(entityId, tab.entityId)) {
            return false;
        }
        return entity.getUUID().equals(tab.entity.getUUID());
    }

    @Override
    public int hashCode() {
        return Objects.hash(entity.getUUID());
    }
}