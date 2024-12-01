package net.mercury.hg.client.model.json;

import com.mojang.datafixers.util.Function4;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.ClampedModelPredicateProvider;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class BooleanModelOverride implements ClampedModelPredicateProvider {

    public static ModelTransformationMode currentModelTransform = ModelTransformationMode.NONE;
    public static int offset = 1;
    int thisOffset = 0;

    public final Function4<ItemStack, ClientWorld, LivingEntity, Integer, Boolean> predicate;

    /**
     * This value corresponds to a value of {@code true} in a model override.
     */
    public static final float MODEL_OVERRIDE_TRUE = 0.07991f;

    public BooleanModelOverride(Function4<ItemStack, ClientWorld, LivingEntity, Integer, Boolean> predicate) {
        this.predicate = predicate;
        this.thisOffset = offset++;
    }

    public float execute(ItemStack s, ClientWorld w, LivingEntity e, int seed) {
        if (predicate == null) {return 0;}
        return predicate.apply(s, w, e, seed) ? MODEL_OVERRIDE_TRUE * thisOffset : 0f;
    }

    public static boolean isRenderingInGUI(ItemStack stack) {
        return currentModelTransform == ModelTransformationMode.GUI && !isRenderingInHandAny(stack);
    }

    public static boolean isRenderingInHandFirst(ItemStack stack) {
        return currentModelTransform.isFirstPerson();
    }

    public static boolean isRenderingInHandThird(ItemStack stack) {
        return currentModelTransform == ModelTransformationMode.THIRD_PERSON_LEFT_HAND || currentModelTransform == ModelTransformationMode.THIRD_PERSON_RIGHT_HAND;
    }

    public static boolean isRenderingInHandAny(ItemStack stack) {
        return isRenderingInHandFirst(stack) || isRenderingInHandThird(stack);
    }

    public static boolean isRenderingInFixedPos(ItemStack stack) {
        return currentModelTransform == ModelTransformationMode.FIXED;
    }

    public static boolean isRenderingAsDropped(ItemStack stack) {
        return currentModelTransform == ModelTransformationMode.GROUND;
    }

    @Override
    public float unclampedCall(ItemStack stack, @Nullable ClientWorld world, @Nullable LivingEntity entity, int seed) {
        return execute(stack, world, entity, seed);
    }

}
