package net.immortaldevs.colorizer.mixin;

import net.immortaldevs.colorizer.ColorManager;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BarrelBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Block.class)
public class BlockMixin {
    @Inject(at = @At("HEAD"), method = "destroy(Lnet/minecraft/world/level/LevelAccessor;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)V")
    public void onBroken(LevelAccessor level, BlockPos pos, BlockState state, CallbackInfo ci) {
        final Block block = (Block) (Object) this;
        if (block instanceof ChestBlock) ColorManager.clearChestColor(pos, state);
        if (block instanceof BarrelBlock) ColorManager.clearColor(pos);
    }
}
