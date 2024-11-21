package com.nomiceu.nomilabs.gregtech.material.registry.register;

import static com.nomiceu.nomilabs.gregtech.material.registry.LabsMaterials.*;
import static com.nomiceu.nomilabs.util.LabsNames.makeLabsName;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.material.info.MaterialFlags.*;
import static gregtech.api.unification.material.info.MaterialIconSet.*;

import gregtech.api.fluids.FluidBuilder;
import gregtech.api.unification.material.Material;

public class LabsRareEarthLine {

    public static void initRareEarthLine() {
        RareEarthOxideConcentrate = new Material.Builder(118, makeLabsName("rare_earth_oxide_concentrate")) // Hardmode
                                                                                                            // Material
                .dust()
                .color(0x394c04).iconSet(FINE)
                .flags(DISABLE_DECOMPOSITION)
                .build();
        RoastedRareEarthOxideConcentrate = new Material.Builder(119,
                makeLabsName("roasted_rare_earth_oxide_concentrate")) // Hardmode Material
                        .dust()
                        .color(0x182100).iconSet(ROUGH)
                        .flags(DISABLE_DECOMPOSITION)
                        .build();
        LeachedRareEarthOxide = new Material.Builder(120, makeLabsName("leached_rare_earth_oxide")) // Hardmode
                                                                                                    // Material
                .dust()
                .color(0x4c5632).iconSet(DULL)
                .flags(DISABLE_DECOMPOSITION)
                .build();
        TrivalentRareEarths = new Material.Builder(121, makeLabsName("trivalent_rare_earths")) // Hardmode Material
                .dust()
                .color(0xa7ea8c).iconSet(METALLIC)
                .flags(DISABLE_DECOMPOSITION)
                .build();
        CeriumConcentrate = new Material.Builder(122, makeLabsName("cerium_concentrate")) // Hardmode Material
                .dust()
                .color(0xef654c).iconSet(DULL)
                .flags(DISABLE_DECOMPOSITION)
                .build();
        DissolvedCeriumConcentrate = new Material.Builder(123, makeLabsName("dissolved_cerium_concentrate")) // Hardmode
                                                                                                             // Material
                .liquid()
                .color(0xc1796c)
                .flags(DISABLE_DECOMPOSITION)
                .build();
        VaporousNitricAcid = new Material.Builder(124, makeLabsName("vaporous_nitric_acid"))
                .liquid(new FluidBuilder().temperature(356))
                .color(0xeaf293)
                .components(Hydrogen, 1, Nitrogen, 1, Oxygen, 3)
                .build();
        EuropiumFod = new Material.Builder(125, makeLabsName("europium_fod"))
                .dust()
                .color(0xf2ec4d).iconSet(FINE)
                .components(Carbon, 30, Hydrogen, 30, Europium, 1, Fluorine, 21, Oxygen, 6)
                .build();
        Hfod = new Material.Builder(126, makeLabsName("hfod"))
                .dust()
                .color(0x6ae86a).iconSet(FINE)
                .components(Carbon, 10, Hydrogen, 11, Fluorine, 7, Oxygen, 2)
                .build();
        Terpyridine = new Material.Builder(127, makeLabsName("terpy"))
                .dust()
                .color(0xdee0de).iconSet(FINE)
                .components(Carbon, 15, Hydrogen, 11, Nitrogen, 3)
                .build();
        EuropiumChelate = new Material.Builder(128, makeLabsName("eu_chelate"))
                .dust()
                .color(0x48a8ce).iconSet(FINE)
                .components(Carbon, 30, Hydrogen, 35, Europium, 1, Fluorine, 21, Oxygen, 7)
                .build();
        TerpyridineEuropiumFod = new Material.Builder(129, makeLabsName("terpy_eu_fod"))
                .dust()
                .color(0x61e8c8).iconSet(FINE)
                .components(Carbon, 45, Hydrogen, 44, Europium, 1, Fluorine, 21, Oxygen, 6, Nitrogen, 3)
                .build();
        CrystallizedTerpyridineEuropiumFod = new Material.Builder(130, makeLabsName("crystal_terpy_eu_fod"))
                .dust()
                .color(0x61e8c8).iconSet(ROUGH)
                .components(Carbon, 45, Hydrogen, 44, Europium, 1, Fluorine, 21, Oxygen, 6, Nitrogen, 3)
                .build();
        FilteredEuropiumFodCompound = new Material.Builder(131, makeLabsName("filtered_europium_fod"))
                .dust()
                .color(0xb2ae40).iconSet(ROUGH)
                .components(Carbon, 30, Hydrogen, 30, Europium, 1, Fluorine, 21, Oxygen, 6)
                .build();
        WashedEuropiumFodCompound = new Material.Builder(132, makeLabsName("washed_europium_fod"))
                .dust()
                .color(0xb2ae40).iconSet(FINE)
                .components(Carbon, 30, Hydrogen, 30, Europium, 1, Fluorine, 21, Oxygen, 6)
                .build();
        CrystallizedEuropiumFodCompound = new Material.Builder(133, makeLabsName("crystal_eu_fod"))
                .dust()
                .color(0xf2ec4d).iconSet(ROUGH)
                .components(Carbon, 30, Hydrogen, 30, Europium, 1, Fluorine, 21, Oxygen, 6)
                .build();
    }
}
