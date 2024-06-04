package net.immortaldevs.colorizer.mixin;

import net.immortaldevs.colorizer.ColorManager;
import net.minecraft.block.BarrelBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.item.*;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public class ItemMixin {
    @Inject(at = @At("HEAD"), method = "useOnBlock(Lnet/minecraft/item/ItemUsageContext;)Lnet/minecraft/util/ActionResult;")
    public void useOnBlock(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir) {
        Item item = (Item) (Object) this;
        World world = context.getWorld();
        BlockPos blockPos = context.getBlockPos();
        BlockEntity blockEntity = world.getBlockEntity(blockPos);

        if (blockEntity instanceof ChestBlockEntity) {
            if (item instanceof DyeItem)
                ColorManager.updateColor(blockPos, (DyeItem) item);
            else if (item == Items.PAPER)
                ColorManager.clearChestColor(blockPos, blockEntity.getCachedState());
            return;
        }

        BlockState blockState = context.getWorld().getBlockState(blockPos);
        if (blockState.getBlock() instanceof BarrelBlock) {
            blockState = blockState.with(BarrelBlock.OPEN, true);
            world.setBlockState(blockPos, blockState);

            if (item instanceof DyeItem)
                ColorManager.updateColor(blockPos, (DyeItem) item);
            else if (item == Items.PAPER)
                ColorManager.clearColor(blockPos);

            blockState = blockState.with(BarrelBlock.OPEN, false);
            world.setBlockState(blockPos, blockState);
        }
    }
}
