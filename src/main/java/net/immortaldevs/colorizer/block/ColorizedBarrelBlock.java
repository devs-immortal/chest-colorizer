package net.immortaldevs.colorizer.block;

import net.immortaldevs.colorizer.BlockColor;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;

import static net.minecraft.world.level.block.BarrelBlock.FACING;
import static net.minecraft.world.level.block.BarrelBlock.OPEN;


public class ColorizedBarrelBlock extends Block {
    public static final EnumProperty<BlockColor> COLOR = EnumProperty.create("color", BlockColor.class);

    public ColorizedBarrelBlock(BlockBehaviour.Properties settings) {
        super(settings);
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH).setValue(OPEN, false).setValue(COLOR, BlockColor.DEFAULT));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, OPEN, COLOR);
    }
}
