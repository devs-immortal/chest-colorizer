package net.immortaldevs.colorizer;

import net.minecraft.block.*;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.math.Direction;

import static net.minecraft.block.BarrelBlock.FACING;
import static net.minecraft.block.BarrelBlock.OPEN;

public class ColorizedBarrelBlock extends Block {
    public static final EnumProperty<ChestColor> COLOR = EnumProperty.of("color", ChestColor.class);

    public ColorizedBarrelBlock(AbstractBlock.Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH).with(OPEN, false).with(COLOR, ChestColor.DEFAULT));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, OPEN, COLOR);
    }
}
