package com.kqp.inventorytabs.init;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.kqp.inventorytabs.api.TabProviderRegistry;

import net.minecraft.resources.Identifier;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.AddServerReloadListenersEvent;

@Mod(InventoryTabs.ID)
public class InventoryTabs {
    public static final String ID = "inventorytabs";

    public static boolean isBigInvLoaded;
    public static boolean isPlayerExLoaded;
    public static boolean isLevelzLoaded;
    private static final Logger log = LogManager.getLogger(ID);

    public static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(ID, path);
    }

    public InventoryTabs(IEventBus modEventBus, ModContainer modContainer) {
        var spec = new ModConfigSpec.Builder();
        InventoryTabsConfig.setupConfig(spec);
        modContainer.registerConfig(ModConfig.Type.CLIENT, spec.build());

        isBigInvLoaded = ModList.get().isLoaded("biginv");
        isPlayerExLoaded = ModList.get().isLoaded("playerex");
        isLevelzLoaded = ModList.get().isLoaded("levelz");
        
        

        NeoForge.EVENT_BUS.addListener(this::playerJoin);
        NeoForge.EVENT_BUS.addListener(this::datapackReload);

        if (FMLLoader.getCurrent().getDist().isClient()) {
            InventoryTabsClient.init(modEventBus, modContainer);
        }
    }

    public void onInitialize() {
//        ClientLoginConnectionEvents.INIT.register((handler, client) -> TabProviderRegistry.init("load"));
//        ServerLifecycleEvents.END_DATA_PACK_RELOAD.register((server, resourceManager, success) -> TabProviderRegistry.init("reload"));
    }

    private void playerJoin(ClientPlayerNetworkEvent.LoggingIn event) {
        TabProviderRegistry.init("load");
    }

    private void datapackReload(AddServerReloadListenersEvent event) {
//        TabProviderRegistry.init("reload"); //TODO: after datapacks are loaded
    }

    public static void log(String message) {
    	if (log == null)
    		return;
    	log.info("[{}] {}", log.getName(), message);
      }
}
