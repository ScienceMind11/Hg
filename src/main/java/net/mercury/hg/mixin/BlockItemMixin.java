package net.mercury.hg.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.mercury.hg.event.PlaceBlockCallback;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockItem.class)
public abstract class BlockItemMixin {

    @Shadow public abstract ActionResult place(ItemPlacementContext context);

    @Inject(method = "place(Lnet/minecraft/item/ItemPlacementContext;)Lnet/minecraft/util/ActionResult;", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemPlacementContext;getBlockPos()Lnet/minecraft/util/math/BlockPos;"), cancellable = true)
    private void hg$onPlaceCallback(ItemPlacementContext context, CallbackInfoReturnable<ActionResult> cir, @Local(ordinal = 0) BlockState placementState) {
        ActionResult result = PlaceBlockCallback.EVENT.invoker().place(context, placementState);
        if(result == ActionResult.FAIL) cir.cancel();
    }

}
