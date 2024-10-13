package net.mercury.hg.multiblock;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonStreamParser;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.mercury.hg.HgMod;
import net.minecraft.registry.RegistryOps;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.world.World;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class MultiblockResourceReloadListener implements SimpleSynchronousResourceReloadListener {

    private RegistryWrapper.WrapperLookup wrapperLookup;

    public MultiblockResourceReloadListener(RegistryWrapper.WrapperLookup wrapperLookup) {
        this.wrapperLookup = wrapperLookup;
    }

    @Override
    public Identifier getFabricId() {
        return HgMod.id("multiblock");
    }

    @Override
    public void reload(ResourceManager manager) {

        // Clear previous multiblock info here

        for(Identifier id : manager.findResources("multiblocks", path -> path.getPath().endsWith(".json")).keySet()) {

            try(InputStream stream = manager.getResource(id).get().getInputStream()) {

                JsonObject object = JsonHelper.deserialize(new InputStreamReader(stream, StandardCharsets.UTF_8));
                DataResult<Pattern.Data> result = Pattern.Data.CODEC.codec().parse(
                        RegistryOps.of(
                                JsonOps.INSTANCE,
                                wrapperLookup
                        ),
                        object
                );



            } catch(Exception except) {
                HgMod.LOGGER.error("Error occurred loading multiblock {} from resource, error: {}", id, except);
                except.printStackTrace();
            }
        }

    }

}
