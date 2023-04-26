package net.immortaldevs.colorizer.mixin;

import net.immortaldevs.colorizer.ColorizedChest;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.item.*;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public class ItemMixin {
    @Inject(at = @At("HEAD"), method = "useOnBlock(Lnet/minecraft/item/ItemUsageContext;)Lnet/minecraft/util/ActionResult;")
    public void useOnBlock(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir) {
        BlockEntity blockEntity = context.getWorld().getBlockEntity(context.getBlockPos());
        if (blockEntity instanceof ChestBlockEntity) {
            if ((Object) this instanceof DyeItem)
                ColorizedChest.updateChestColor(context.getBlockPos(), (DyeItem) (Object) this);
            else if ((Object) this == Items.PAPER)
                ColorizedChest.clearChestColor(context.getBlockPos());
        }
    }
}
