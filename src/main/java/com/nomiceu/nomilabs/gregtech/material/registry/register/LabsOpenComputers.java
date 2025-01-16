package com.nomiceu.nomilabs.gregtech.material.registry.register;

import static com.nomiceu.nomilabs.gregtech.material.registry.LabsMaterials.*;
import static com.nomiceu.nomilabs.util.LabsNames.makeLabsName;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.material.info.MaterialFlags.*;

import gregtech.api.fluids.FluidBuilder;
import gregtech.api.unification.material.Material;

public class LabsOpenComputers {

    public static void initOpenComputers() {
        // PLA
        PolylacticAcid = new Material.Builder(125, makeLabsName("polylactic_acid"))
                .liquid(new FluidBuilder().temperature(430)).ingot()
                .color(0xf0fff4)
                .components(Carbon, 3, Hydrogen, 4, Oxygen, 2)
                .flags(GENERATE_FINE_WIRE, DISABLE_DECOMPOSITION, NO_UNIFICATION)
                .build();
        CarbonatedBiomassBroth = new Material.Builder(126, makeLabsName("carbonated_biomass_broth"))
                .liquid(new FluidBuilder().temperature(298))
                .color(0x3b8a33)
                .flags(DISABLE_DECOMPOSITION)
                .build();
        FermentedBiomassBroth = new Material.Builder(127, makeLabsName("fermented_biomass_broth"))
                .liquid(new FluidBuilder().temperature(298))
                .color(0x1f471a)
                .flags(DISABLE_DECOMPOSITION)
                .build();
        LacticAcid = new Material.Builder(128, makeLabsName("lactic_acid"))
                .liquid(new FluidBuilder().temperature(395))
                .color(0xbccf74)
                .components(Carbon, 3, Hydrogen, 6, Oxygen, 3)
                .build();
        CrudeLacticAcid = new Material.Builder(129, makeLabsName("crude_lactic_acid"))
                .liquid(new FluidBuilder().temperature(298))
                .color(0x6f7a46)
                .components(LacticAcid, 2, Water, 1)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        // Nylon
        Nylon = new Material.Builder(130, makeLabsName("nylon"))
                .liquid(new FluidBuilder().temperature(725)).ingot()
                .color(0xf6ffd4)
                .components(Carbon, 12, Hydrogen, 22, Nitrogen, 2, Oxygen, 2)
                .flags(GENERATE_FINE_WIRE, DISABLE_DECOMPOSITION, NO_UNIFICATION)
                .build();

        // PEEK
        Peek = new Material.Builder(131, makeLabsName("peek"))
                .liquid(new FluidBuilder().temperature(616)).ingot()
                .color(0xa79786)
                .components(Carbon, 19, Hydrogen, 12, Oxygen, 3)
                .flags(GENERATE_FINE_WIRE, DISABLE_DECOMPOSITION, NO_UNIFICATION)
                .build();

        // Grog
        Grog = new Material.Builder(132, makeLabsName("grog"))
                .liquid(new FluidBuilder().temperature(666))
                .color(0x42ff5c)
                .components(RareEarth, 1)
                .flags(DISABLE_DECOMPOSITION)
                .build();
    }
}
