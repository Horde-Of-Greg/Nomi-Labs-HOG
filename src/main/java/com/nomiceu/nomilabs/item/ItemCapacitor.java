package com.nomiceu.nomilabs.item;

import com.enderio.core.client.handlers.SpecialTooltipHandler;
import com.nomiceu.nomilabs.LabsValues;
import crazypants.enderio.api.capacitor.CapabilityCapacitorData;
import crazypants.enderio.api.capacitor.ICapacitorData;
import crazypants.enderio.api.capacitor.ICapacitorKey;
import crazypants.enderio.base.lang.Lang;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ItemCapacitor extends Item {
    public final ICapacitorData data;
    public ItemCapacitor(ResourceLocation rl, CreativeTabs tab, ICapacitorData data) {
        setCreativeTab(tab);
        setRegistryName(rl);
        setMaxStackSize(64);
        this.data = data;
    }

    @Override
    public void addInformation(@NotNull ItemStack stack, @Nullable World worldIn, @NotNull List<String> tooltip, @NotNull ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(I18n.format("tooltip.nomilabs.capacitors.description")); // Not using default here. This one adds `EnderIO`, making it clearer
        // Add default info
        if (SpecialTooltipHandler.showAdvancedTooltips())
            SpecialTooltipHandler.addDetailedTooltipFromResources(tooltip, Lang.MACHINE_UPGRADE.getKey());
        else
            SpecialTooltipHandler.addShowDetailsTooltip(tooltip);
    }

    @Override
    @Nullable
    public ICapabilityProvider initCapabilities(@NotNull ItemStack stack, @Nullable NBTTagCompound nbt) {
        return new CapacitorCapabilityProvider(data);
    }

    public static class CapacitorCapabilityProvider implements ICapabilityProvider {
        private final ICapacitorData data;
        public CapacitorCapabilityProvider(ICapacitorData data) {
            this.data = data;
        }

        @Override
        public boolean hasCapability(@NotNull Capability<?> capability, @Nullable EnumFacing facing) {
            return capability == CapabilityCapacitorData.getCapNN();
        }

        @Nullable
        @Override
        public <T> T getCapability(@NotNull Capability<T> capability, @Nullable EnumFacing facing) {
            if (capability == CapabilityCapacitorData.getCapNN())
                return CapabilityCapacitorData.getCapNN().cast(data);
            return null;
        }
    }

    public enum LabsCapacitorData implements ICapacitorData {
        COMPRESSED("compressed_octadic", 4),
        DOUBLE_COMPRESSED("double_compressed_octadic", 5);

        private final String name;
        private final float level;

        LabsCapacitorData(String name, float level) {
            this.name = name;
            this.level = level;
        }


        @Override
        public float getUnscaledValue(@NotNull ICapacitorKey iCapacitorKey) {
            return level;
        }

        @NotNull
        @Override
        public String getUnlocalizedName() {
            return name;
        }

        @NotNull
        @Override
        public String getLocalizedName() {
            return I18n.format(LabsValues.LABS_MODID + "." + name);
        }
    }
}
