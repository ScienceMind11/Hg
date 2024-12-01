package net.mercury.hg.multiblock;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonStreamParser;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.mercury.hg.HgMod;
import net.mercury.hg.event.PlaceBlockCallback;
import net.mercury.hg.registry.HgRegistries;
import net.minecraft.block.pattern.BlockPattern;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryOps;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.world.World;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

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

                Pattern.Data data = result.resultOrPartial(HgMod.LOGGER::error).get();
                List<List<String>> pattern = data.pattern();

                boolean foundController = false;
                for(List<String> stringList : pattern) {
                    for(String string : stringList) {
                        for(char c : string.toCharArray()) {

                            if(c == data.controller() && foundController) {
                                throw new IllegalArgumentException("Controller block cannot occur more than once");
                            } else if(c == data.controller()) {
                                foundController = true;
                            }

                        }
                    }
                }

                BlockPattern blockPattern = Pattern.parse(data);

                Registry.register(
                        HgRegistries.MULTIBLOCK,
                        data.id(),
                        Map.entry(
                                data.key().get(data.controller()),
                                blockPattern
                        )
                );

//                PlaceBlockCallback.EVENT.register((context, placementState) -> {
//
//                    if(placementState.isIn(data.key().get(data.controller()))) {
//                        blockPattern.searchAround(context.getWorld(), context.getBlockPos());
//                    }
//
//                });

            } catch(Exception except) {
                HgMod.LOGGER.error("Error occurred loading multiblock {} from resource, error: {}", id, except);
            }
        }

    }

}
