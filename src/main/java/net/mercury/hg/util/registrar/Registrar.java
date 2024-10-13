package net.mercury.hg.util.registrar;

import net.minecraft.block.Block;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class Registrar {

    private final String modId;

    private Item currentItem;
    private Block currentBlock;
    private StatusEffect currentEffect;

    public Registrar(String modId) {
        this.modId = modId;
    }

    protected Identifier id(String path) {
        return new Identifier(modId, path);
    }

    protected <T> T register(Registry<T> registry, String name, T object) {
        return Registry.register(registry, id(name), object);
    }

    public ItemRegistrar item(String name, Item item) {
        return new ItemRegistrar(name, item);
    }

    public class ItemRegistrar {

        private String name;
        private Item item;

        private ItemRegistrar(String name, Item item) {
            this.name = name;
            this.item = item;
        }

        public Item build() {
            return item;
        }

        public Item buildAndRegister() {
            return register(Registries.ITEM, name, build());
        }

    }

}
