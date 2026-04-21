package net.immortaldevs.colorizer.mixin.sodium;

import net.caffeinemc.mods.sodium.client.world.LevelSlice;
import net.immortaldevs.colorizer.BlockColor;
import net.immortaldevs.colorizer.ColorManager;
import net.immortaldevs.colorizer.ColorizerMod;
import net.immortaldevs.colorizer.block.ColorizedBarrelBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.BarrelBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = LevelSlice.class)
public class LevelSliceMixin {
    @Inject(method = "getBlockState(III)Lnet/minecraft/world/level/block/state/BlockState;", at = @At(value = "RETURN"), cancellable = true)
    public void modifyBlockState(int blockX, int blockY, int blockZ, CallbackInfoReturnable<BlockState> cir) {
        BlockState state = cir.getReturnValue();
        if (state != null) {
            if (state.getBlock() == Blocks.BARREL) {
                BlockColor color = ColorManager.getColor(new BlockPos(blockX, blockY, blockZ));
                state = ColorizerMod.BARREL_BLOCK.defaultBlockState()
                        .setValue(ColorizedBarrelBlock.COLOR, color)
                        .setValue(BarrelBlock.FACING, state.getValue(BarrelBlock.FACING))
                        .setValue(BarrelBlock.OPEN, state.getValue(BarrelBlock.OPEN));
            }
            cir.setReturnValue(state);
        }
    }
}
