package net.mercury.hg.client.model;

import com.google.common.collect.ImmutableMap;
import net.minecraft.block.BlockState;
import net.minecraft.client.model.Model;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.model.*;
import net.minecraft.client.render.model.json.JsonUnbakedModel;
import net.minecraft.client.render.model.json.ModelOverrideList;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

public class SeparateTransformsModel implements UnbakedModel {

    @Override
    public Collection<Identifier> getModelDependencies() {
        return null;
    }

    @Override
    public void setParents(Function<Identifier, UnbakedModel> modelLoader) {

    }

    @Nullable
    @Override
    public BakedModel bake(Baker baker, Function<SpriteIdentifier, Sprite> textureGetter, ModelBakeSettings rotationContainer, Identifier modelId) {
        return null;
    }

    public static class Baked implements BakedModel {

        private final boolean useAmbientOcclusion;
        private final boolean hasDepth;
        private final boolean isSideLit;
        private final Sprite particle;
        private final ModelOverrideList overrides;

        private final BakedModel baseModel;
        private final ImmutableMap<ModelTransformationMode, BakedModel> perspectives;

        public Baked(boolean useAmbientOcclusion, boolean hasDepth, boolean isSideLit, Sprite particle, ModelOverrideList overrides, BakedModel baseModel, ImmutableMap<ModelTransformationMode, BakedModel> perspectives) {
            this.useAmbientOcclusion = useAmbientOcclusion;
            this.hasDepth = hasDepth;
            this.isSideLit = isSideLit;
            this.particle = particle;
            this.overrides = overrides;
            this.baseModel = baseModel;
            this.perspectives = perspectives;
        }

        @Override
        public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction face, Random random) {
            return baseModel.getQuads(state, face, random);
        }

        @Override
        public boolean useAmbientOcclusion() {
            return useAmbientOcclusion;
        }

        @Override
        public boolean hasDepth() {
            return hasDepth;
        }

        @Override
        public boolean isSideLit() {
            return isSideLit;
        }

        @Override
        public boolean isBuiltin() {
            return false;
        }

        @Override
        public Sprite getParticleSprite() {
            return particle;
        }

        @Override
        public ModelTransformation getTransformation() {
            return ModelTransformation.NONE;
        }

        @Override
        public ModelOverrideList getOverrides() {
            return overrides;
        }

    }

}
