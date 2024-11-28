package net.mercury.hg.multiblock;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.pattern.BlockPattern;
import net.minecraft.block.pattern.BlockPatternBuilder;
import net.minecraft.block.pattern.CachedBlockPosition;
import net.minecraft.predicate.block.BlockStatePredicate;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryCodecs;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.util.Identifier;
import net.minecraft.util.dynamic.Codecs;
import net.minecraft.world.gen.structure.Structure;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class Pattern {

    public static BlockPattern parse(Pattern.Data data) {

        List<List<String>> pattern = data.pattern();
        Map<Character, RegistryEntryList<Block>> key = data.key();
        BlockPatternBuilder builder = BlockPatternBuilder.start();

        builder.where(' ', CachedBlockPosition.matchesBlockState(BlockStatePredicate.ANY));

        for(List<String> layer : pattern) {
            builder.aisle(layer.toArray(new String[]{}));
        }
        for(Character mapKey : key.keySet()) {

            boolean hasTagKey = key.get(mapKey).getTagKey().isPresent();

            if(hasTagKey) {
                builder.where(
                        mapKey,
                        blockPos -> blockPos.getBlockState().isIn(key.get(mapKey).getTagKey().get())
                );
            } else {
                builder.where(
                        mapKey,
                        blockPos -> blockPos.getBlockState().isIn(key.get(mapKey))
                );
            }
        }

        return builder.build();

    }

    public record Data(Identifier id, Character controller, Map<Character, RegistryEntryList<Block>> key, List<List<String>> pattern) {

        private static final Codec<Character> CONTROLLER_CODEC = Codec.STRING.comapFlatMap(Pattern.Data::validateKey, String::valueOf);

        private static final Codec<Character> KEY_ENTRY_CODEC = Codec.STRING.comapFlatMap(Pattern.Data::validateKey, String::valueOf);

        private static final Codec<List<List<String>>> PATTERN_CODEC = Codec.STRING.listOf().listOf().comapFlatMap(Pattern.Data::validatePattern, Function.identity());

        // Whole codec, incorporates KEY_ENTRY_CODEC and PATTERN_CODEC
        public static final MapCodec<Data> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                // Identifier
                Identifier.CODEC.fieldOf("id").forGetter(Data::id),
                // A list of blocks, a single block, or a block tag
                CONTROLLER_CODEC.fieldOf("controller").forGetter(Data::controller),
                // Map of key-value pairs for a character and a list of blocks/single block/block tag
                Codecs.strictUnboundedMap(KEY_ENTRY_CODEC, RegistryCodecs.entryList(RegistryKeys.BLOCK)).fieldOf("key").forGetter(Data::key),
                // List of lists of strings to form 3d pattern for multiblock (characters map to "key")
                PATTERN_CODEC.fieldOf("pattern").forGetter(Data::pattern)
        ).apply(instance, Pattern.Data::new));

        // Moving key validation outside of lambda in KEY_ENTRY_CODEC
        private static DataResult<Character> validateKey(String keyEntry) {
            if(keyEntry.length() != 1) {
                return DataResult.error(() -> "Invalid key entry: '%s' is an invalid symbol (must be 1 character only).".formatted(keyEntry));
            } else {
                return " ".equals(keyEntry) ? DataResult.error(() -> "Invalid key entry: ' ' is a reserved symbol.") : DataResult.success(keyEntry.charAt(0));
            }
        }

        // Moving pattern validation outside of lambda in PATTERN_CODEC
        private static DataResult<List<List<String>>> validatePattern(List<List<String>> pattern) {
            for(List<String> stringList : pattern) {
                if(stringList.size() > 16) {
                    return DataResult.error(() -> "Multiblock cannot be larger than 16 blocks");
                } else if(stringList.isEmpty()) {
                    return DataResult.error(() -> "Multiblock cannot be empty");
                }
            }
            return DataResult.success(pattern);
        }

    }

}
