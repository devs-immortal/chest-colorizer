package net.immortaldevs.colorizer.mixin.sodium;

import me.jellysquid.mods.sodium.client.world.WorldSlice;
import net.immortaldevs.colorizer.BlockColor;
import net.immortaldevs.colorizer.ColorManager;
import net.immortaldevs.colorizer.ColorizerMod;
import net.immortaldevs.colorizer.block.ColorizedBarrelBlock;
import net.minecraft.block.BarrelBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = WorldSlice.class, remap = false)
public class WorldSliceMixin {

    @Inject(method = "getBlockState(III)Lnet/minecraft/block/BlockState;", at = @At(value = "RETURN"),cancellable = true)
    public void modifyBlockState(int x, int y, int z, CallbackInfoReturnable<BlockState> cir) {
        BlockState state = cir.getReturnValue();
        if (state != null) {
            if (state.getBlock() == Blocks.BARREL) {
                BlockColor color = ColorManager.getColor(new BlockPos(x,y,z));
                state = ColorizerMod.BARREL_BLOCK.getDefaultState()
                        .with(ColorizedBarrelBlock.COLOR, color)
                        .with(BarrelBlock.FACING, state.get(BarrelBlock.FACING))
                        .with(BarrelBlock.OPEN, state.get(BarrelBlock.OPEN));
            }
            cir.setReturnValue(state);
        }
    }
}
