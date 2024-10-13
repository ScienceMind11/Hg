package net.mercury.hg;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.client.player.ClientPlayerBlockBreakEvents;
import net.mercury.hg.client.model.loader.SeparateTransformsModelLoader;
import net.mercury.hg.handler.FreezeframeHandler;

public class HgClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {

//        ModelLoadingPlugin.register(pluginContext -> pluginContext.modifyModelOnLoad().register(SeparateTransformsModelLoader::load));

        ClientTickEvents.END_CLIENT_TICK.register(FreezeframeHandler::tick);

    }

}
