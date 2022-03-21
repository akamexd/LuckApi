package me.akamex.luckapi.util.itemstack;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.util.Collection;

import static me.akamex.luckapi.util.color.ColorUtils.color;

public class BookItemBuilder extends AbstractItemBuilder<BookItemBuilder, BookMeta> {

    BookItemBuilder(ItemStack pattern) {
        super(pattern);

        Material type = pattern.getType();
        if(type != Material.WRITTEN_BOOK) {
            throw new IllegalArgumentException("Pattern type is not supported!");
        }
    }

    public BookItemBuilder setTitle(String title) {
        meta.setTitle(color(title));
        return this;
    }

    public BookItemBuilder setAuthor(String author) {
        meta.setAuthor(author);
        return this;
    }

    public BookItemBuilder setPageGeneration(BookMeta.Generation generation) {
        meta.setGeneration(generation);
        return this;
    }

    public BookItemBuilder setPage(int page, String data) {
        meta.setPage(page, color(data));
        return this;
    }

    public BookItemBuilder setPages(Collection<String> data) {
        meta.setPages(color(data));
        return this;
    }

    public BookItemBuilder setPages(String... data) {
        meta.setPages(color(data));
        return this;
    }

}
