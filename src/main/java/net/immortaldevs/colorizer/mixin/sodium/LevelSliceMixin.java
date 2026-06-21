package net.immortaldevs.colorizer.mixin.sodium;

import net.caffeinemc.mods.sodium.client.world.LevelSlice;
import net.immortaldevs.colorizer.ColorManager;
import net.minecraft.core.BlockPos;
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
                state = ColorManager.getBarrelState(state, new BlockPos(blockX, blockY, blockZ));
            }
            cir.setReturnValue(state);
        }
    }
}
