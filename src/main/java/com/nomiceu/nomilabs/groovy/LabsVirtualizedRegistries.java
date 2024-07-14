package com.nomiceu.nomilabs.groovy;

import static com.nomiceu.nomilabs.groovy.CompositionBuilder.CompositionSpecification;

import java.util.*;

import net.minecraft.item.ItemStack;

import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.Nullable;

import com.cleanroommc.groovyscript.registry.AbstractReloadableStorage;
import com.cleanroommc.groovyscript.registry.VirtualizedRegistry;
import com.nomiceu.nomilabs.util.ItemMeta;

import gregtech.api.recipes.ingredients.nbtmatch.NBTCondition;
import gregtech.api.recipes.ingredients.nbtmatch.NBTMatcher;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.stack.ItemMaterialInfo;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;

@SuppressWarnings("unused")
public class LabsVirtualizedRegistries {

    public static final ReplaceRecyclingManager REPLACE_RECYCLING_MANAGER = new ReplaceRecyclingManager();
    public static final ReplaceCompositionManager REPLACE_DECOMP_MANAGER = new ReplaceCompositionManager();

    public static class ReplaceCompositionManager extends VirtualizedRegistry<CompositionSpecification> {

        public final Deque<CompositionSpecification> needReloading = new ArrayDeque<>();

        @Override
        public void onReload() {
            restoreFromBackup().forEach(ChangeComposition::restore);
        }

        @Override
        public void afterScriptLoad() {
            ChangeComposition.reloadCompositionRecipes();
            needReloading.clear();
        }

        public void changeMaterialDecomp(CompositionSpecification spec) {
            addBackup(spec);
            // Add Items to the 'back' of the array deque, so that it's a LIFO structure
            needReloading.addFirst(spec);
        }
    }

    public static class ReplaceRecyclingManager extends VirtualizedRegistry<Pair<ItemMeta, ItemMaterialInfo>> {

        public final Map<ItemMeta, ItemMaterialInfo> needReloading = new Object2ObjectOpenHashMap<>();

        // Separate Accessible NBT Conditions and Actually Added Ones, so that accessible is only available DURING
        // RELOADING
        @Nullable
        public Map<ItemMeta, Pair<NBTMatcher, NBTCondition>> nbtConditions = null;
        private final Map<ItemMeta, Pair<NBTMatcher, NBTCondition>> addedNbtConditions = new Object2ObjectOpenHashMap<>();

        @Override
        public void onReload() {
            restoreFromBackup().forEach((pair) -> {
                OreDictUnifier.registerOre(pair.getLeft().toStack(), pair.getRight());
                needReloading.put(pair.getLeft(), pair.getRight());
            });
            addedNbtConditions.clear();
        }

        @Override
        public void afterScriptLoad() {
            // Load actual map into accessible one
            nbtConditions = addedNbtConditions;

            RecyclingHelper.reloadRecyclingRecipes();

            needReloading.clear();
            nbtConditions = null;
        }

        @Override
        protected AbstractReloadableStorage<Pair<ItemMeta, ItemMaterialInfo>> createRecipeStorage() {
            return new AbstractReloadableStorage<>() {

                @Override
                protected boolean compareRecipe(Pair<ItemMeta, ItemMaterialInfo> a,
                                                Pair<ItemMeta, ItemMaterialInfo> b) {
                    return a.getKey().equals(b.getKey());
                }
            };
        }

        public void registerOre(ItemStack stack, ItemMaterialInfo info) {
            var in = new ItemMeta(stack);
            addBackup(Pair.of(in, OreDictUnifier.getMaterialInfo(stack)));
            needReloading.put(in, info);
            OreDictUnifier.registerOre(stack, info);
        }

        public void registerNBTHandling(ItemStack stack, NBTMatcher matcher, NBTCondition condition) {
            addedNbtConditions.put(new ItemMeta(stack), Pair.of(matcher, condition));
        }
    }
}
