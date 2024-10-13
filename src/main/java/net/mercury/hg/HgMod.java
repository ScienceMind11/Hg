package net.mercury.hg;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.mercury.hg.multiblock.MultiblockResourceReloadListener;
import net.mercury.hg.util.registrar.Registrar;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Registries;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HgMod implements ModInitializer {

    public static final String NAME = "Hg";
    public static final String ID = NAME.toLowerCase();
    public static final Logger LOGGER = LoggerFactory.getLogger(NAME);

    public static final Registrar REGISTRAR = new Registrar(ID);

    @Override
    public void onInitialize() {

        ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(new MultiblockResourceReloadListener(DynamicRegistryManager.of(Registries.REGISTRIES)));

        LOGGER.info("Loaded successfully");

    }

    public static Identifier id(String path) {
        return new Identifier(ID, path);
    }

}
