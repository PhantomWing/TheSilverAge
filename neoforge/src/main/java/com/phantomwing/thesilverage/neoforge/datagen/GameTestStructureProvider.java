package com.phantomwing.thesilverage.neoforge.datagen;

import com.google.common.hash.Hashing;
import com.phantomwing.thesilverage.TheSilverAge;
import net.minecraft.SharedConstants;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtIo;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;

/**
 * Generates the shared GameTest arena structure as a structure-template NBT at
 * {@code data/thesilverage/structure/silver_test_arena.nbt}.
 *
 * <p>1.21.5+ GameTests require an explicit structure to run in and dropped the old
 * auto-empty-template helper, so we emit a minimal 7x5x7 box with a stone floor at y=0 and
 * air above. The NBT is built by hand (size / palette / blocks / DataVersion) rather than via
 * {@code StructureTemplate}, which would need a live world. All {@link SilverGameTests}
 * instances place their blocks/entities on this floor.
 */
public class GameTestStructureProvider implements DataProvider {
    private static final String STRUCTURE_PATH = "structure/silver_test_arena.nbt";
    private static final int SIZE_X = 7;
    private static final int SIZE_Y = 5;
    private static final int SIZE_Z = 7;

    private final PackOutput output;

    public GameTestStructureProvider(PackOutput output) {
        this.output = output;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cachedOutput) {
        return CompletableFuture.runAsync(() -> {
            try {
                byte[] data = serialize(buildArena());
                Path path = output.getOutputFolder(PackOutput.Target.DATA_PACK)
                        .resolve(TheSilverAge.MOD_ID)
                        .resolve(STRUCTURE_PATH);
                cachedOutput.writeIfNeeded(path, data, Hashing.sha1().hashBytes(data));
            } catch (IOException e) {
                throw new RuntimeException("Failed to write GameTest arena structure", e);
            }
        });
    }

    private static CompoundTag buildArena() {
        CompoundTag root = new CompoundTag();
        root.putInt("DataVersion", SharedConstants.getCurrentVersion().dataVersion().version());

        ListTag size = new ListTag();
        size.add(IntTag.valueOf(SIZE_X));
        size.add(IntTag.valueOf(SIZE_Y));
        size.add(IntTag.valueOf(SIZE_Z));
        root.put("size", size);

        // Palette: index 0 = stone (floor), index 1 = air (everything above).
        ListTag palette = new ListTag();
        palette.add(named("minecraft:stone"));
        palette.add(named("minecraft:air"));
        root.put("palette", palette);

        ListTag blocks = new ListTag();
        for (int x = 0; x < SIZE_X; x++) {
            for (int y = 0; y < SIZE_Y; y++) {
                for (int z = 0; z < SIZE_Z; z++) {
                    CompoundTag block = new CompoundTag();
                    ListTag pos = new ListTag();
                    pos.add(IntTag.valueOf(x));
                    pos.add(IntTag.valueOf(y));
                    pos.add(IntTag.valueOf(z));
                    block.put("pos", pos);
                    block.putInt("state", y == 0 ? 0 : 1);
                    blocks.add(block);
                }
            }
        }
        root.put("blocks", blocks);
        root.put("entities", new ListTag());
        return root;
    }

    private static CompoundTag named(String blockId) {
        CompoundTag tag = new CompoundTag();
        tag.putString("Name", blockId);
        return tag;
    }

    private static byte[] serialize(CompoundTag tag) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        NbtIo.writeCompressed(tag, out);
        return out.toByteArray();
    }

    @Override
    public String getName() {
        return "The Silver Age GameTest Structures";
    }
}
