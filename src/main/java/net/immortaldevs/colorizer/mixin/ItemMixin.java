package net.immortaldevs.colorizer.mixin;

import net.immortaldevs.colorizer.ColorManager;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BarrelBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public class ItemMixin {
    @Inject(at = @At("HEAD"), method = "useOn(Lnet/minecraft/world/item/context/UseOnContext;)Lnet/minecraft/world/InteractionResult;")
    public void useOnBlock(UseOnContext context, CallbackInfoReturnable<InteractionResult> cir) {
        Item item = (Item) (Object) this;
        Level level = context.getLevel();
        BlockPos blockPos = context.getClickedPos();
        BlockEntity blockEntity = level.getBlockEntity(blockPos);

        if (blockEntity instanceof ChestBlockEntity) {
            if (item instanceof DyeItem dyeItem)
                ColorManager.updateColor(blockPos, dyeItem);
            else if (item == Items.PAPER)
                ColorManager.clearChestColor(blockPos, blockEntity.getBlockState());
            return;
        }

        BlockState blockState = context.getLevel().getBlockState(blockPos);
        if (blockState.getBlock() instanceof BarrelBlock) {
            blockState = blockState.setValue(BarrelBlock.OPEN, true);
            level.setBlock(blockPos, blockState, Block.UPDATE_NONE);

            if (item instanceof DyeItem dyeItem)
                ColorManager.updateColor(blockPos, dyeItem);
            else if (item == Items.PAPER)
                ColorManager.clearColor(blockPos);

            blockState = blockState.setValue(BarrelBlock.OPEN, false);
            level.setBlock(blockPos, blockState, Block.UPDATE_NONE);
        }
    }
}
