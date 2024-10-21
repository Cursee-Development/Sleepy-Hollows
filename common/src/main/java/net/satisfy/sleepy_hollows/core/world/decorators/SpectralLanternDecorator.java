package net.satisfy.sleepy_hollows.core.world.decorators;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LanternBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.satisfy.sleepy_hollows.core.registry.FeatureTypeRegistry;
import net.satisfy.sleepy_hollows.core.registry.ObjectRegistry;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SpectralLanternDecorator extends TreeDecorator {

    public static final Codec<SpectralLanternDecorator> CODEC = Codec.unit(SpectralLanternDecorator::new);

    public SpectralLanternDecorator() {
    }

    @Override
    protected @NotNull TreeDecoratorType<?> type() {
        return FeatureTypeRegistry.SPECTRAL_LANTERN_DECORATOR.get();
    }

    @Override
    public void place(Context context) {
        RandomSource random = context.random();
        List<BlockPos> leavesPositions = context.leaves();

        for (BlockPos pos : leavesPositions) {
            BlockPos belowPos = pos.below();
            BlockPos aboveLanternPos = belowPos.above();

            if (context.isAir(belowPos)) {
                if (random.nextFloat() < 0.1) {
                    BlockState lantern = ObjectRegistry.SPECTRAL_LANTERN.get().defaultBlockState().setValue(LanternBlock.HANGING, true);
                    context.setBlock(belowPos, lantern);

                    if (context.isAir(aboveLanternPos)) {
                        BlockState chain = Blocks.CHAIN.defaultBlockState();
                        context.setBlock(aboveLanternPos, chain);
                    }
                }
            }
        }
    }
}
