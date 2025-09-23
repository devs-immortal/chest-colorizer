package net.immortaldevs.colorizer.block;

import net.immortaldevs.colorizer.BlockColor;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.math.Direction;

import static net.minecraft.block.BarrelBlock.FACING;
import static net.minecraft.block.BarrelBlock.OPEN;

public class ColorizedBarrelBlock extends Block {
    public static final EnumProperty<BlockColor> COLOR = EnumProperty.of("color", BlockColor.class);

    public ColorizedBarrelBlock(AbstractBlock.Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH).with(OPEN, false).with(COLOR, BlockColor.DEFAULT));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, OPEN, COLOR);
    }
}
