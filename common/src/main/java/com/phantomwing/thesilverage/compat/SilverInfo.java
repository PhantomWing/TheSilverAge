package com.phantomwing.thesilverage.compat;

import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.client.ServerContentSettings;
import com.phantomwing.thesilverage.platform.CommonConfig;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * The info pages JEI and EMI show for what no recipe shows: who presses a Silver Pressure Plate and what its tarnish
 * does, the undead keeping away from silver light, and what a Silver Arrow does. A page about something the config
 * turns off is left out, as The Tin Age's recipe viewer plugins show theirs.
 *
 * @param name  the page's name, its text {@code thesilverage.info.<name>}
 * @param items the items it is shown for
 */
public record SilverInfo(String name, List<Item> items) {
    private static final String[] STAGES = {"", "exposed_", "weathered_", "oxidized_", "waxed_", "waxed_exposed_",
            "waxed_weathered_", "waxed_oxidized_"};

    public String key() {
        return TheSilverAge.MOD_ID + ".info." + name;
    }

    public static List<SilverInfo> all() {
        List<SilverInfo> pages = new ArrayList<>();
        pages.add(new SilverInfo("silver_pressure_plate", everyStage("silver_pressure_plate")));
        if (CommonConfig.undeadAvoidSilverLight()) {
            List<Item> lights = new ArrayList<>(List.of(item("silver_torch")));
            lights.addAll(everyStage("silver_lantern"));
            pages.add(new SilverInfo("silver_light", lights));
        }
        if (ServerContentSettings.arrows()) {
            pages.add(new SilverInfo("silver_arrow", List.of(item("silver_arrow"))));
        }
        return pages;
    }

    private static List<Item> everyStage(String family) {
        List<Item> items = new ArrayList<>();
        for (String stage : STAGES) {
            items.add(item(stage + family));
        }
        return items;
    }

    private static Item item(String id) {
        return BuiltInRegistries.ITEM.get(TheSilverAge.resourceLocation(id));
    }
}
