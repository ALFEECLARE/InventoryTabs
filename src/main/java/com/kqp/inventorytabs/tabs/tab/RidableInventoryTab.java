package com.kqp.inventorytabs.tabs.tab;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.ClientInput;
import net.minecraft.network.protocol.game.ServerboundPlayerCommandPacket;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.equine.AbstractHorse;
import net.minecraft.world.entity.player.Input;

public class RidableInventoryTab extends SimpleEntityTab {

    public RidableInventoryTab(Entity entity) {
        super(entity);
    }

    @Override
    public void open() {
        if (!entity.hasPassenger(Minecraft.getInstance().player)) {
        	ClientInput input = Minecraft.getInstance().player.input;
        	boolean backupShiftStatus = input.keyPresses.shift(); 
        	overwriteKeydownShift(input, true);
            super.open();
        	overwriteKeydownShift(input, backupShiftStatus);
            Minecraft.getInstance().getConnection().send(new ServerboundPlayerCommandPacket(Minecraft.getInstance().player, ServerboundPlayerCommandPacket.Action.OPEN_INVENTORY));
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
    
    private void overwriteKeydownShift(ClientInput input, boolean shiftKeyStatus) {
    	Input currentKeys = input.keyPresses; 
    	input.keyPresses = new Input(currentKeys.forward(), currentKeys.backward(), currentKeys.left(), currentKeys.right(), currentKeys.jump(), shiftKeyStatus, currentKeys.sprint());
    }
}
