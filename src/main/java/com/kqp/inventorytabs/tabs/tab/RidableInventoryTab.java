package com.kqp.inventorytabs.tabs.tab;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.horse.AbstractHorse;

public class RidableInventoryTab extends SimpleEntityTab {

    public RidableInventoryTab(Entity entity) {
        super(entity);
    }

    @Override
    public void open() {
        if (!entity.hasPassenger(Minecraft.getInstance().player)) {
            Minecraft.getInstance().player.input.shiftKeyDown = true;
            super.open();
            Minecraft.getInstance().player.input.shiftKeyDown = false;
        } else {
            super.open();
        }
    }

    @Override
    public boolean shouldBeRemoved() {
        if (entity instanceof AbstractHorse horse) {
            if (!horse.isTamed()) {
                return true;
            }
        }
        return super.shouldBeRemoved();
    }
}
