package com.nomiceu.nomilabs.gregtech.mixinhelper;

import net.minecraft.block.BlockLiquid;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.IFluidBlock;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.wrappers.BlockLiquidWrapper;
import net.minecraftforge.fluids.capability.wrappers.FluidBlockWrapper;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import gregtech.api.items.metaitem.stats.IItemBehaviour;
import gregtech.api.items.metaitem.stats.ItemFluidContainer;
import gregtech.api.util.GTTransferUtils;
import gregtech.api.util.GTUtility;

/**
 * Implements <a href="https://github.com/GregTechCEu/GregTech/pull/2660">GTCEu #2660</a>.
 */
public class BucketItemFluidContainer extends ItemFluidContainer implements IItemBehaviour {

    @Override
    public ActionResult<ItemStack> onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand,
                                             EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack stack = player.getHeldItem(hand);

        var result = rayTrace(world, player);
        if (result == null) return pass(stack);

        ItemStack cellStack = GTUtility.copy(1, stack);
        var cellHandler = FluidUtil.getFluidHandler(cellStack);
        if (cellHandler == null) return pass(stack);

        var cellFluid = cellHandler.drain(Integer.MAX_VALUE, false);
        var blockHandler = FluidUtil.getFluidHandler(world, result.getBlockPos(), result.sideHit);
        FluidStack soundFluid = cellFluid;
        boolean success, isFill;

        if (blockHandler == null) {
            if (cellFluid == null || !cellFluid.getFluid().canBePlacedInWorld())
                return pass(stack);

            blockHandler = createHandler(cellFluid, world, pos.offset(facing));
            success = GTTransferUtils.transferFluids(cellHandler, blockHandler) > 0;
            isFill = true;
        } else {
            soundFluid = blockHandler.drain(Integer.MAX_VALUE, false);
            success = GTTransferUtils.transferFluids(blockHandler, cellHandler) > 0;
            isFill = false;
        }

        if (success) {
            playSound(soundFluid, isFill, player);
            addToPlayerInventory(stack, cellHandler.getContainer(), player, hand);
            return success(stack);
        }

        return pass(stack);
    }

    // copied and adapted from Item.java
    @Nullable
    private static RayTraceResult rayTrace(World worldIn, EntityPlayer player) {
        Vec3d lookPos = player.getPositionVector()
                .add(0, player.getEyeHeight(), 0);

        Vec3d lookOffset = player.getLookVec()
                .scale(player.getEntityAttribute(EntityPlayer.REACH_DISTANCE).getAttributeValue());

        return worldIn.rayTraceBlocks(lookPos, lookPos.add(lookOffset),
                true, false, false);
    }

    @NotNull
    private IFluidHandler createHandler(FluidStack stack, World world, BlockPos pos) {
        var block = stack.getFluid().getBlock();
        if (block instanceof IFluidBlock fluidBlock) {
            return new FluidBlockWrapper(fluidBlock, world, pos);
        } else if (block instanceof BlockLiquid blockLiquid) {
            return new BlockLiquidWrapper(blockLiquid, world, pos);
        }
        throw new IllegalArgumentException("Block must be a liquid!");
    }

    private void addToPlayerInventory(ItemStack playerStack, ItemStack resultStack, EntityPlayer player,
                                      EnumHand hand) {
        if (playerStack.getCount() > resultStack.getCount()) {
            playerStack.shrink(resultStack.getCount());
            if (!player.inventory.addItemStackToInventory(resultStack) && !player.world.isRemote) {
                EntityItem dropItem = player.entityDropItem(resultStack, 0);
                if (dropItem != null) dropItem.setPickupDelay(0);
            }
        } else {
            player.setHeldItem(hand, resultStack);
        }
    }

    /**
     * Play the appropriate fluid interaction sound for the fluid. <br />
     * Must be called on server to work correctly
     **/
    private void playSound(FluidStack fluid, boolean fill, EntityPlayer player) {
        if (fluid == null || player.world.isRemote) return;
        SoundEvent soundEvent;
        if (fill) {
            soundEvent = fluid.getFluid().getFillSound(fluid);
        } else {
            soundEvent = fluid.getFluid().getEmptySound(fluid);
        }
        player.world.playSound(null, player.posX, player.posY + 0.5, player.posZ,
                soundEvent, SoundCategory.PLAYERS, 1.0F, 1.0F);
    }

    /* Util */
    private ActionResult<ItemStack> pass(ItemStack stack) {
        return ActionResult.newResult(EnumActionResult.PASS, stack);
    }

    private ActionResult<ItemStack> success(ItemStack stack) {
        return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
    }
}
