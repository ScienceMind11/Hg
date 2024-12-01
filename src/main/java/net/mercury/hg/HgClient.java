package net.mercury.hg;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.client.player.ClientPlayerBlockBreakEvents;
import net.mercury.hg.client.model.json.BooleanModelOverride;
import net.mercury.hg.handler.FreezeframeHandler;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.util.Identifier;

public class HgClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {

//        ModelLoadingPlugin.register(pluginContext -> pluginContext.modifyModelOnLoad().register(SeparateTransformsModelLoader::load));

//        ClientTickEvents.END_CLIENT_TICK.register(FreezeframeHandler::tick);

    }

    private static void registerSeparateTransformsPredicates() {
        ModelPredicateProviderRegistry.register(new Identifier("is_hand_first"), new BooleanModelOverride((itemStack, clientWorld, livingEntity, integer) -> BooleanModelOverride.isRenderingInHandFirst(itemStack)));
        ModelPredicateProviderRegistry.register(new Identifier("is_hand_third"), new BooleanModelOverride((itemStack, clientWorld, livingEntity, integer) -> BooleanModelOverride.isRenderingInHandThird(itemStack)));
        ModelPredicateProviderRegistry.register(new Identifier("is_hand_any"), new BooleanModelOverride((itemStack, clientWorld, livingEntity, integer) -> BooleanModelOverride.isRenderingInHandAny(itemStack)));
        ModelPredicateProviderRegistry.register(new Identifier("is_in_hand_first"), new BooleanModelOverride((itemStack, clientWorld, livingEntity, integer) -> BooleanModelOverride.isRenderingInHandFirst(itemStack)));
        ModelPredicateProviderRegistry.register(new Identifier("is_in_hand_third"), new BooleanModelOverride((itemStack, clientWorld, livingEntity, integer) -> BooleanModelOverride.isRenderingInHandThird(itemStack)));
        ModelPredicateProviderRegistry.register(new Identifier("is_in_hand_any"), new BooleanModelOverride((itemStack, clientWorld, livingEntity, integer) -> BooleanModelOverride.isRenderingInHandAny(itemStack)));
        ModelPredicateProviderRegistry.register(new Identifier("is_gui"), new BooleanModelOverride((itemStack, clientWorld, livingEntity, integer) -> BooleanModelOverride.isRenderingInGUI(itemStack)));
        ModelPredicateProviderRegistry.register(new Identifier("is_inventory"), new BooleanModelOverride((itemStack, clientWorld, livingEntity, integer) -> BooleanModelOverride.isRenderingInGUI(itemStack)));
        ModelPredicateProviderRegistry.register(new Identifier("is_fixed"), new BooleanModelOverride((itemStack, clientWorld, livingEntity, integer) -> BooleanModelOverride.isRenderingInFixedPos(itemStack)));
        ModelPredicateProviderRegistry.register(new Identifier("is_in_gui"), new BooleanModelOverride((itemStack, clientWorld, livingEntity, integer) -> BooleanModelOverride.isRenderingInGUI(itemStack)));
        ModelPredicateProviderRegistry.register(new Identifier("is_in_inventory"), new BooleanModelOverride((itemStack, clientWorld, livingEntity, integer) -> BooleanModelOverride.isRenderingInGUI(itemStack)));
        ModelPredicateProviderRegistry.register(new Identifier("is_in_fixed"), new BooleanModelOverride((itemStack, clientWorld, livingEntity, integer) -> BooleanModelOverride.isRenderingInFixedPos(itemStack)));
        ModelPredicateProviderRegistry.register(new Identifier("is_in_item_frame"), new BooleanModelOverride((itemStack, clientWorld, livingEntity, integer) -> BooleanModelOverride.isRenderingInFixedPos(itemStack)));
        ModelPredicateProviderRegistry.register(new Identifier("is_dropped"), new BooleanModelOverride((itemStack, clientWorld, livingEntity, integer) -> BooleanModelOverride.isRenderingAsDropped(itemStack)));
        ModelPredicateProviderRegistry.register(new Identifier("is_ground"), new BooleanModelOverride((itemStack, clientWorld, livingEntity, integer) -> BooleanModelOverride.isRenderingAsDropped(itemStack)));
        ModelPredicateProviderRegistry.register(new Identifier("is_on_ground"), new BooleanModelOverride((itemStack, clientWorld, livingEntity, integer) -> BooleanModelOverride.isRenderingAsDropped(itemStack)));
    }

}
