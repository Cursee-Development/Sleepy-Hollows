package net.satisfy.sleepy_hollows.client;

import dev.architectury.registry.client.level.entity.EntityModelLayerRegistry;
import dev.architectury.registry.client.level.entity.EntityRendererRegistry;
import dev.architectury.registry.client.rendering.BlockEntityRendererRegistry;
import dev.architectury.registry.client.rendering.RenderTypeRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.biome.Biome;
import net.satisfy.sleepy_hollows.client.model.armor.HauntboundBootsModel;
import net.satisfy.sleepy_hollows.client.model.armor.HauntboundChestplateModel;
import net.satisfy.sleepy_hollows.client.model.armor.HauntboundHelmetModel;
import net.satisfy.sleepy_hollows.client.model.armor.HauntboundLeggingsModel;
import net.satisfy.sleepy_hollows.client.model.entity.FleeingPumpkinHeadModel;
import net.satisfy.sleepy_hollows.client.model.entity.HorsemanModel;
import net.satisfy.sleepy_hollows.client.model.entity.SpectralHorseModel;
import net.satisfy.sleepy_hollows.client.renderer.*;
import net.satisfy.sleepy_hollows.client.util.PlayerSanityProvider;
import net.satisfy.sleepy_hollows.client.util.SanityManager;
import net.satisfy.sleepy_hollows.core.network.SleepyHollowsNetwork;
import net.satisfy.sleepy_hollows.core.network.message.SanityPacketMessage;
import net.satisfy.sleepy_hollows.core.registry.EntityTypeRegistry;
import net.satisfy.sleepy_hollows.core.util.IEntityDataSaver;
import net.satisfy.sleepy_hollows.core.util.SleepyHollowsUtil;

import static net.satisfy.sleepy_hollows.core.registry.ObjectRegistry.*;


@Environment(EnvType.CLIENT)
public class SleepyHollowsClient {
    public static void initClient() {
        RenderTypeRegistry.register(RenderType.cutout(),
                GRAVE_LILY.get(), POTTED_GRAVE_LILY.get(), DREAMSHADE.get(), POTTED_DREAMSHADE.get(), TALL_DREAMSHADE.get(),
                HOLLOW_SAPLING.get(), POTTED_HOLLOW_SAPLING.get(), HOLLOW_TRAPDOOR.get(), HOLLOW_DOOR.get(), MOONVEIL_GRASS.get(),
                HOLLOW_WINDOW.get(), TALL_MOONVEIL_GRASS.get(), SHADOWBLOOM.get(), POTTED_SHADOWBLOOM.get(), DUSKBERRY_BUSH.get(),
                SPECTRAL_LANTERN.get(), WROUGHT_IRON_FENCE.get()
        );

        BlockEntityRendererRegistry.register(EntityTypeRegistry.DISPLAY_BLOCK_ENTITY.get(), context -> new PedestalBlockRenderer());
        BlockEntityRendererRegistry.register(EntityTypeRegistry.COFFIN_BLOCK_ENTITY.get(), CoffinRenderer::new);
        BlockEntityRendererRegistry.register(EntityTypeRegistry.COMPLETIONIST_BANNER_ENTITY.get(), CompletionistBannerRenderer::new);

        // MANUAL ???
        // register a receiver for a Server-to-Client (S2C) packet, to handle the packet when the client acquires it
        // NetworkManager.registerReceiver(NetworkManager.Side.S2C, SleepyHollowsNetwork.Packets.SANITY_PACKET, SleepyHollowsNetwork.Packets::receiverForClient);
    }

    public static void PreinitClient() {
        EntityModelLayerRegistry.register(CoffinRenderer.LAYER_LOCATION, CoffinRenderer::getTexturedModelData);
        EntityModelLayerRegistry.register(SpectralHorseModel.LAYER_LOCATION, SpectralHorseModel::getTexturedModelData);
        EntityModelLayerRegistry.register(FleeingPumpkinHeadModel.LAYER_LOCATION, FleeingPumpkinHeadModel::getTexturedModelData);
        EntityModelLayerRegistry.register(HorsemanModel.LAYER_LOCATION, HorsemanModel::getTexturedModelData);
        EntityModelLayerRegistry.register(CompletionistBannerRenderer.LAYER_LOCATION, CompletionistBannerRenderer::createBodyLayer);
        EntityModelLayerRegistry.register(HauntboundHelmetModel.LAYER_LOCATION, HauntboundHelmetModel::createBodyLayer);
        EntityModelLayerRegistry.register(HauntboundChestplateModel.LAYER_LOCATION, HauntboundChestplateModel::createBodyLayer);
        EntityModelLayerRegistry.register(HauntboundLeggingsModel.LAYER_LOCATION, HauntboundLeggingsModel::createBodyLayer);
        EntityModelLayerRegistry.register(HauntboundBootsModel.LAYER_LOCATION, HauntboundBootsModel::createBodyLayer);
        EntityRendererRegistry.register(EntityTypeRegistry.SPECTRAL_HORSE, SpectralHorseRenderer::new);
        EntityRendererRegistry.register(EntityTypeRegistry.INFECTED_ZOMBIE, InfectedZombieRenderer::new);
        EntityRendererRegistry.register(EntityTypeRegistry.FLEEING_PUMPKIN_HEAD, FleeingPumpkinHeadRenderer::new);
        EntityRendererRegistry.register(EntityTypeRegistry.HORSEMAN, HorsemanRenderer::new);
    }

    // randomly send a packet to the server
    public static void onClientTick(Minecraft instance) {

        if (instance.level == null) return;
        if (instance.player == null) return;

        if (instance.level.random.nextInt(1, 1000) == 1) {

            if (SanityManager.hasSanityImmunity(instance.player)) return;

            Holder<Biome> biomeHolder = instance.level.getBiome(instance.player.getOnPos());

            if (SleepyHollowsUtil.unwrappedBiome(biomeHolder).contains("sleepy_hollow")) {

                IEntityDataSaver dataPlayer = (IEntityDataSaver) instance.player;
                // PlayerSanityProvider.decreaseSanity(dataPlayer, 20);
                instance.player.sendSystemMessage(Component.literal("your client sanity: " + String.valueOf(PlayerSanityProvider.getSanity(dataPlayer))));
                // MANUAL ???
//                // create a new buffer to store encoded data
//                FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
//
//                // create new message to store raw information
//                SanityPacketMessage sanityPacketMessage = new SanityPacketMessage(true);
//
//                // encode our message onto the buffer
//                sanityPacketMessage.encode(buf);
//
//                NetworkManager.sendToServer(SleepyHollowsNetwork.Packets.SANITY_PACKET, buf);

                SleepyHollowsNetwork.SANITY_CHANNEL.sendToServer(new SanityPacketMessage(true));
            } else {

                IEntityDataSaver dataPlayer = (IEntityDataSaver) instance.player;
                // PlayerSanityProvider.increaseSanity(dataPlayer, 5);
                instance.player.sendSystemMessage(Component.literal("your client sanity: " + String.valueOf(PlayerSanityProvider.getSanity(dataPlayer))));
            }
        }
    }
}

