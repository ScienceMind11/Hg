package net.mercury.hg.mixin;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.mercury.hg.util.HashTool;
import net.minecraft.client.render.model.json.ModelOverride;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.zip.Adler32;
import java.util.zip.CRC32;

@Environment(EnvType.CLIENT)
@Mixin(targets = "net.minecraft.client.render.model.json.ModelOverride$Deserializer")
public class ModelOverrideDeserializerMixin {

    // Unfortunately, I need to do this to allow different types. Sorry!
    @Inject(method="deserializeMinPropertyValues", at=@At("HEAD"), cancellable = true)
    private void onDeserializeMinPropertyValues(JsonObject object, CallbackInfoReturnable<List<ModelOverride.Condition>> cir) {

        List<ModelOverride.Condition> conds = new ArrayList<>();

        JsonObject jsonObject = JsonHelper.getObject(object, "predicate");
        for (Map.Entry<String, JsonElement> stringJsonElementEntry : jsonObject.entrySet()) {
            if (JsonHelper.isBoolean(stringJsonElementEntry.getValue())) {
                boolean s = JsonHelper.asBoolean(stringJsonElementEntry.getValue(), stringJsonElementEntry.getKey());
                float v = s ? 0.07991f : 0;
                conds.add(new ModelOverride.Condition(new Identifier(stringJsonElementEntry.getKey()), v));
                continue;
            } else if (JsonHelper.isString(stringJsonElementEntry.getValue())) {
                float v = HashTool.getHash64(JsonHelper.asString(stringJsonElementEntry.getValue(), stringJsonElementEntry.getKey()));
                conds.add(new ModelOverride.Condition(new Identifier(stringJsonElementEntry.getKey()), v));
                continue;
            }
            conds.add(new ModelOverride.Condition(new Identifier(stringJsonElementEntry.getKey()), JsonHelper.asFloat(stringJsonElementEntry.getValue(), stringJsonElementEntry.getKey())));
        }
        cir.setReturnValue (conds);

    }

}
