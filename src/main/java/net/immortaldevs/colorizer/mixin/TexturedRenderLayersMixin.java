package net.immortaldevs.colorizer.mixin;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.EnderChestBlockEntity;
import net.minecraft.block.entity.TrappedChestBlockEntity;
import net.minecraft.block.enums.ChestType;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.immortaldevs.colorizer.ColorManager.getColorizedTextureId;
import static net.minecraft.client.render.TexturedRenderLayers.*;

@Mixin(TexturedRenderLayers.class)
public class TexturedRenderLayersMixin {
    @Inject(at = @At("HEAD"), cancellable = true, method = "getChestTextureId(Lnet/minecraft/block/entity/BlockEntity;Lnet/minecraft/block/enums/ChestType;Z)Lnet/minecraft/client/util/SpriteIdentifier;")
    private static void getChestTexture(BlockEntity blockEntity, ChestType type, boolean christmas, CallbackInfoReturnable<SpriteIdentifier> cir) {
        if (blockEntity instanceof EnderChestBlockEntity) {
            cir.setReturnValue(ENDER_CHEST);
        } else if (blockEntity instanceof TrappedChestBlockEntity) {
            cir.setReturnValue(christmas ? getChestTextureId(type, CHRISTMAS_CHEST, CHRISTMAS_CHEST_LEFT, CHRISTMAS_CHEST_RIGHT) : getChestTextureId(type, TRAPPED_CHEST, TRAPPED_CHEST_LEFT, TRAPPED_CHEST_RIGHT));
        } else {
            World world = blockEntity.getWorld();
            if (world != null) {
                SpriteIdentifier identifier = getColorizedTextureId(blockEntity, type);
                if (identifier != null)
                    cir.setReturnValue(getColorizedTextureId(blockEntity, type));
                else
                    cir.setReturnValue(christmas ? getChestTextureId(type, CHRISTMAS_CHEST, CHRISTMAS_CHEST_LEFT, CHRISTMAS_CHEST_RIGHT) : getChestTextureId(type, CHEST, CHEST_LEFT, CHEST_RIGHT));
            } else {
                cir.setReturnValue(christmas ? getChestTextureId(type, CHRISTMAS_CHEST, CHRISTMAS_CHEST_LEFT, CHRISTMAS_CHEST_RIGHT) : getChestTextureId(type, CHEST, CHEST_LEFT, CHEST_RIGHT));
            }
        }
    }
}
