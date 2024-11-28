package net.mercury.hg.registry;

import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.fabricmc.fabric.api.event.registry.RegistryAttribute;
import net.mercury.hg.HgMod;
import net.minecraft.block.Block;
import net.minecraft.block.pattern.BlockPattern;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntryList;

import java.util.Map;

public class HgRegistries {

    private static final RegistryKey<Registry<Map.Entry<RegistryEntryList<Block>, BlockPattern>>> MULTIBLOCK_KEY = RegistryKey.ofRegistry(HgMod.id("multiblock"));
    public static Registry<Map.Entry<RegistryEntryList<Block>, BlockPattern>> MULTIBLOCK;

    public static void register() {

        MULTIBLOCK = FabricRegistryBuilder.createSimple(MULTIBLOCK_KEY)
                .attribute(RegistryAttribute.SYNCED)
                .buildAndRegister();

    }

}
