package net.immortaldevs.colorizer.network;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.dimension.DimensionType;

import static net.immortaldevs.colorizer.ColorizerMod.MOD_ID;

public record ClearColorPayload(RegistryEntry<DimensionType> dimension, BlockPos pos, boolean isChest) implements CustomPayload {
    public static final Identifier COLORIZE_PAYLOAD_ID = Identifier.of(MOD_ID, "clear_color");
    public static final CustomPayload.Id<ClearColorPayload> ID = new CustomPayload.Id<>(COLORIZE_PAYLOAD_ID);
    public static final PacketCodec<RegistryByteBuf, ClearColorPayload> CODEC = PacketCodec.tuple(
            DimensionType.PACKET_CODEC, ClearColorPayload::dimension,
            BlockPos.PACKET_CODEC, ClearColorPayload::pos,
            PacketCodecs.BOOLEAN, ClearColorPayload::isChest,
            ClearColorPayload::new
    );

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
