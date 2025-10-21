package net.immortaldevs.colorizer.network;

import net.immortaldevs.colorizer.BlockColor;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

import static net.immortaldevs.colorizer.ColorizerMod.MOD_ID;

public record UpdateColorPayload(BlockPos pos, BlockColor color) implements CustomPayload {
    public static final Identifier COLORIZE_PAYLOAD_ID = Identifier.of(MOD_ID, "update_color");
    public static final CustomPayload.Id<UpdateColorPayload> ID = new CustomPayload.Id<>(COLORIZE_PAYLOAD_ID);
    public static final PacketCodec<RegistryByteBuf, UpdateColorPayload> CODEC = PacketCodec.tuple(
            BlockPos.PACKET_CODEC, UpdateColorPayload::pos, BlockColor.PACKET_CODEC, UpdateColorPayload::color, UpdateColorPayload::new
    );

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
