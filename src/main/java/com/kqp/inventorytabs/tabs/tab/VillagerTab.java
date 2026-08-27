package com.kqp.inventorytabs.tabs.tab;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.villager.Villager;
import net.minecraft.world.entity.npc.villager.VillagerProfession;

public class VillagerTab extends SimpleEntityTab {

    public VillagerTab(Entity entity) {
        super(entity);
    }

    @Override
    public void open() {
        super.open();
    }

    @Override
    public boolean shouldBeRemoved() {
        if (entity instanceof Villager villager) {
            if (villager.getVillagerData().profession().getKey().equals(VillagerProfession.NITWIT) || villager.getVillagerData().profession().getKey().equals(VillagerProfession.NONE)) {
                return true;
            }
        }

        return super.shouldBeRemoved();
    }
}
