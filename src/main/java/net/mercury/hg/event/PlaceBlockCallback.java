package net.mercury.hg.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.util.ActionResult;

public interface PlaceBlockCallback {

    Event<PlaceBlockCallback> EVENT = EventFactory.createArrayBacked(PlaceBlockCallback.class,
            (listeners) -> (context, placementState) -> {
                for(PlaceBlockCallback listener : listeners) {
                    ActionResult result = listener.place(context, placementState);

                    if(result != ActionResult.PASS) {
                        return result;
                    }

                }
                return ActionResult.PASS;
            }
    );

    ActionResult place(ItemPlacementContext context, BlockState placementState);

}
