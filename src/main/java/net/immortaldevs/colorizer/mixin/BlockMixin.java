package net.immortaldevs.colorizer.mixin;

import net.immortaldevs.colorizer.ColorManager;
import net.minecraft.block.BarrelBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Block.class)
public class BlockMixin {
    @Inject(at = @At("HEAD"), method = "onBroken(Lnet/minecraft/world/WorldAccess;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;)V")
    public void onBroken(WorldAccess worldAccess, BlockPos pos, BlockState state, CallbackInfo info) {
        if (worldAccess instanceof World world) {
            var block = (Block) (Object) this;
            var dimension = world.getDimensionEntry();

            if (block instanceof ChestBlock) ColorManager.clearColor(dimension, pos, state);
            if (block instanceof BarrelBlock) ColorManager.clearColor(dimension, pos);
        }
    }
}
