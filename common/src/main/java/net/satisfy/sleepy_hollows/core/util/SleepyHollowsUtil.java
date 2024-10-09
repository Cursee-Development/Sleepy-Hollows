package net.satisfy.sleepy_hollows.core.util;

import dev.architectury.platform.Platform;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class SleepyHollowsUtil {

    public static <T extends Block> RegistrySupplier<T> abstractBlockRegistration(DeferredRegister<Block> register, Registrar<Block> registrar, ResourceLocation path, Supplier<T> block) {
        return Platform.isForge() ? register.register(path.getPath(), block) : registrar.register(path, block);
    }

    public static <T extends Item> RegistrySupplier<T> abstractItemRegistration(DeferredRegister<Item> register, Registrar<Item> registrar, ResourceLocation path, Supplier<T> itemSupplier) {
        return Platform.isForge() ? register.register(path.getPath(), itemSupplier) : registrar.register(path, itemSupplier);
    }

    public static <T extends Block> RegistrySupplier<T> abstractBlockWithoutItemRegistration(DeferredRegister<Block> register, Registrar<Block> registrar, ResourceLocation path, Supplier<T> block) {
        return Platform.isForge() ? register.register(path.getPath(), block) : registrar.register(path, block);
    }

    public static <T extends Block> RegistrySupplier<T> abstractBlockItemRegistration(DeferredRegister<Block> registerB, Registrar<Block> registrarB, DeferredRegister<Item> registerI, Registrar<Item> registrarI, ResourceLocation name, Supplier<T> block) {
        RegistrySupplier<T> toReturn = abstractBlockRegistration(registerB, registrarB, name, block);
        abstractItemRegistration(registerI, registrarI, name, () -> new BlockItem(toReturn.get(), new Item.Properties()));
        return toReturn;
    }

    public static VoxelShape rotateShape(Direction from, Direction to, VoxelShape shape) {
        VoxelShape[] buffer = new VoxelShape[]{shape, Shapes.empty()};
        int times = (to.get2DDataValue() - from.get2DDataValue() + 4) % 4;

        for(int i = 0; i < times; ++i) {
            buffer[0].forAllBoxes((minX, minY, minZ, maxX, maxY, maxZ) -> {
                buffer[1] = Shapes.joinUnoptimized(buffer[1], Shapes.box(1.0 - maxZ, minY, minX, 1.0 - minZ, maxY, maxX), BooleanOp.OR);
            });
            buffer[0] = buffer[1];
            buffer[1] = Shapes.empty();
        }

        return buffer[0];
    }


    public enum CornerType implements StringRepresentable {
        NONE("none"),
        OUTER("outer"),
        INNER("inner");

        private final String name;

        CornerType(String name) {
            this.name = name;
        }

        @Override
        public @NotNull String getSerializedName() {
            return this.name;
        }
    }


}
