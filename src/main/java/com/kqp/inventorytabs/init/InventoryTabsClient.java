package com.kqp.inventorytabs.init;

import java.util.concurrent.CompletableFuture;

import com.kqp.inventorytabs.interf.TabManagerContainer;
import com.mojang.blaze3d.platform.InputConstants;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.client.event.RegisterClientReloadListenersEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.level.LevelEvent;

public class InventoryTabsClient {
    public static final KeyMapping NEXT_TAB_KEY_BIND = new KeyMapping(
            "inventorytabs.key.next_tab", InputConstants.Type.KEYSYM, InputConstants.KEY_TAB, "key.categories.inventory");

    public static final KeyMapping DISABLE_TABS_KEY_BIND = new KeyMapping(
            "inventorytabs.key.disable_tabs", InputConstants.Type.KEYSYM, InputConstants.UNKNOWN.getValue(), "key.categories.inventory");

    public static boolean serverDoSightCheckFlag = true;

    static void init(IEventBus modEventBus, ModContainer modContainer) {
        // Handle state of tab managerInventoryTabsClient
        NeoForge.EVENT_BUS.addListener(InventoryTabsClient::onWorldLoad);
        NeoForge.EVENT_BUS.addListener(InventoryTabsClient::onKeyPressed);
        modEventBus.addListener(InventoryTabsClient::onRegisterKeyMappings);
        modEventBus.addListener(InventoryTabsClient::onReloadAssets);
    }

    private static void onReloadAssets(RegisterClientReloadListenersEvent event) {
        event.registerReloadListener((pPreparationBarrier, pResourceManager, pPreparationsProfiler, pReloadProfiler, pBackgroundExecutor, pGameExecutor) -> {
            return CompletableFuture.runAsync(InventoryTabsClient::reloadTabs, pGameExecutor).thenCompose(pPreparationBarrier::wait);
        });
    }

    private static void onWorldLoad(LevelEvent.Load event) {
        if (event.getLevel().isClientSide()) {
            reloadTabs();
        }
    }

    private static void reloadTabs() {
        Minecraft client = Minecraft.getInstance();
        if (client.level != null) {
            if (client.screen != null) {
                TabManagerContainer tabManagerContainer = (TabManagerContainer) client;

                tabManagerContainer.getTabManager().update();
            }
        }
    }

    private static void onRegisterKeyMappings(RegisterKeyMappingsEvent event) {
        event.register(NEXT_TAB_KEY_BIND);
        event.register(DISABLE_TABS_KEY_BIND);
    }

    private static void onKeyPressed(InputEvent.Key event) {
        if (DISABLE_TABS_KEY_BIND.matches(event.getKey(), event.getScanCode())) {
            InventoryTabsConfig.renderTabs.set(DISABLE_TABS_KEY_BIND.consumeClick() != InventoryTabsConfig.renderTabs.get());
        }
    }
    
    public static boolean screenSupported(Screen screen) {
        return (screen instanceof AbstractContainerScreen<?>) && !(screen instanceof CreativeModeInventoryScreen);
    }

    public static <T extends Screen> boolean shouldRenderTabs(T screen) {
        return screenSupported(screen) && InventoryTabsConfig.renderTabs.get();
    }
}
