package com.kqp.inventorytabs.init;

import java.util.Arrays;
import java.util.List;

import net.neoforged.neoforge.common.ModConfigSpec;

public class InventoryTabsConfig {

    public static ModConfigSpec.BooleanValue doSightChecksFlag;
    public static ModConfigSpec.BooleanValue rotatePlayer;
    public static ModConfigSpec.ConfigValue<List<? extends String>> excludeTab;
    public static ModConfigSpec.ConfigValue<List<? extends String>> includeTab;
    public static ModConfigSpec.BooleanValue renderTabs;
    public static ModConfigSpec.BooleanValue debugEnabled;

    public static void setupConfig(ModConfigSpec.Builder builder) {
        builder.push("Client");

        doSightChecksFlag = builder.define("doSightChecksFlag", () -> true);
        rotatePlayer = builder.define("rotatePlayer", () -> false);
        excludeTab = builder.defineList("excludeTab", Arrays.asList("tiered:reforging_station", "#techreborn:block_entities_without_inventories", "#inventorytabs:mod_compat_blacklist"), o -> true);
        includeTab = builder.defineList("includeTab", Arrays.asList(""), o -> true);
        renderTabs = builder.define("renderTabs", () -> true);
        debugEnabled = builder.define("debugEnabled", () -> false);
    }
}