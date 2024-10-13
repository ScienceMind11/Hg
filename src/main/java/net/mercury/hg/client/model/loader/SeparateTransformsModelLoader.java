package net.mercury.hg.client.model.loader;

import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelModifier;
import net.minecraft.client.render.model.UnbakedModel;
import net.minecraft.client.render.model.json.JsonUnbakedModel;

public class SeparateTransformsModelLoader {

    public static UnbakedModel load(UnbakedModel model, ModelModifier.OnLoad.Context context) {

        if(!(model instanceof JsonUnbakedModel json)) return model;



        return null;

    }

}
