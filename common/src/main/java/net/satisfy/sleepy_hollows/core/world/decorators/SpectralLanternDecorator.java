package net.satisfy.sleepy_hollows.core.world.decorators;

import java.util.ArrayList;
import com.mojang.serialization.MapCodec;
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
    public static final MapCodec<SpectralLanternDecorator> CODEC = MapCodec.unit(SpectralLanternDecorator::new);

    public SpectralLanternDecorator() {
    }

    @Override
    protected @NotNull TreeDecoratorType<?> type() {
        return FeatureTypeRegistry.SPECTRAL_LANTERN_DECORATOR.get();
    }

    @Override
    public void place(Context context) {
        RandomSource random = context.random();
        List<BlockPos> candidates = new ArrayList<>(context.leaves().stream().filter(pos -> context.isAir(pos.below()) && context.isAir(pos.below(2))).toList());
        int lanterns = random.nextInt(3);
        for (int i = 0; i < lanterns && !candidates.isEmpty(); i++) {
            BlockPos leaf = candidates.remove(random.nextInt(candidates.size()));
            context.setBlock(leaf.below(), Blocks.CHAIN.defaultBlockState());
            context.setBlock(leaf.below(2), ObjectRegistry.SPECTRAL_LANTERN.get().defaultBlockState().setValue(LanternBlock.HANGING, true));
            candidates.removeIf(pos -> pos.distManhattan(leaf) < 4);
        }
    }
}