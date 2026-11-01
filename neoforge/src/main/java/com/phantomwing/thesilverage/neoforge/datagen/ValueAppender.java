package com.phantomwing.thesilverage.neoforge.datagen;

import net.minecraft.data.tags.TagAppender;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagEntry;
import net.minecraft.tags.TagKey;

import java.util.function.Function;

/**
 * A {@link TagAppender} that also accepts registry <i>values</i>.
 *
 * <p>26.2 narrowed {@code TagAppender#add} to {@code ResourceKey}, which would otherwise mean
 * spelling {@code .add(block.builtInRegistryHolder().key())} at every entry of this mod's long tag
 * chains. The providers override {@code tag(...)} to return this instead, so the chains keep reading
 * as a list of blocks, items or entity types. Every method declared here returns this type so a
 * chain keeps the value-based overload; the interface's own {@code add(ResourceKey...)} and
 * {@code addAll} defaults still hand back a plain appender, so keep those out of a chain.</p>
 */
final class ValueAppender<T> implements TagAppender<T> {
    private final TagAppender<T> delegate;
    private final Function<T, ResourceKey<T>> keyOf;

    ValueAppender(TagAppender<T> delegate, Function<T, ResourceKey<T>> keyOf) {
        this.delegate = delegate;
        this.keyOf = keyOf;
    }

    /** Adds a value by resolving its registry key. */
    ValueAppender<T> add(T value) {
        delegate.add(keyOf.apply(value));
        return this;
    }

    @SafeVarargs
    final ValueAppender<T> add(T... values) {
        for (T value : values) {
            add(value);
        }
        return this;
    }

    @Override
    public ValueAppender<T> add(ResourceKey<T> key) {
        delegate.add(key);
        return this;
    }

    @Override
    public ValueAppender<T> addOptional(ResourceKey<T> key) {
        delegate.addOptional(key);
        return this;
    }

    @Override
    public ValueAppender<T> addTag(TagKey<T> tag) {
        delegate.addTag(tag);
        return this;
    }

    @Override
    public ValueAppender<T> addOptionalTag(TagKey<T> tag) {
        delegate.addOptionalTag(tag);
        return this;
    }

    // NeoForge's TagAppender extension; plain delegation, kept only so this stays a drop-in.
    @Override
    public ValueAppender<T> add(TagEntry entry) {
        delegate.add(entry);
        return this;
    }

    @Override
    public ValueAppender<T> replace(boolean replace) {
        delegate.replace(replace);
        return this;
    }

    @Override
    public ValueAppender<T> remove(ResourceKey<T> key) {
        delegate.remove(key);
        return this;
    }

    @Override
    public ValueAppender<T> remove(TagKey<T> tag) {
        delegate.remove(tag);
        return this;
    }
}
