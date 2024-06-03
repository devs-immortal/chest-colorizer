package net.immortaldevs.colorizer.mixin;

import net.immortaldevs.colorizer.ChestColor;
import net.immortaldevs.colorizer.ColorizerMod;
import net.minecraft.block.BarrelBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BarrelBlock.class)
public class BarrelBlockMixin {
    @Inject(at = @At("TAIL"), method = "Lnet/minecraft/block/BarrelBlock;<init>(Lnet/minecraft/block/AbstractBlock$Settings;)V")
    public void init(CallbackInfo info) {
        BarrelBlock thisBlock = (BarrelBlock) (Object) this;
        BlockState defaultState = thisBlock.getDefaultState();
        thisBlock.setDefaultState(defaultState.with(ColorizerMod.COLOR, ChestColor.DEFAULT));
    }

    @Inject(at = @At("TAIL"), method = "Lnet/minecraft/block/BarrelBlock;appendProperties(Lnet/minecraft/state/StateManager$Builder;)V")
    public void appendProperties(StateManager.Builder<Block, BlockState> builder, CallbackInfo info) {
        builder.add(ColorizerMod.COLOR);
    }
}