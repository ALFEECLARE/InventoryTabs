package com.kqp.inventorytabs.interf;

import com.kqp.inventorytabs.tabs.TabManager;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

/**
 * Interface for holding the tab manager. Gets injected into
 * {@link net.minecraft.client.Minecraft}.
 */
@OnlyIn(Dist.CLIENT)
public interface TabManagerContainer {
    TabManager getTabManager();
}
