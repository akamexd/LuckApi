package me.akamex.luckapi.title;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Title {

    String title, subtitle;
    int fadeIn = 10, stay = 20, fadeOut = 10;

    public Title(String title, String subtitle, int fadeIn, int stay, int fadeOut) {
        this.title = title;
        this.subtitle = subtitle;
        this.fadeIn = fadeIn;
        this.stay = stay;
        this.fadeOut = fadeOut;
    }

    public Title(String title, String subtitle) {
        this.title = title;
        this.subtitle = subtitle;
    }

    Title(Title title) {
        this(title.getTitle(), title.getSubtitle(), title.getFadeIn(), title.getStay(), title.getFadeOut());
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public int getFadeIn() {
        return fadeIn;
    }

    public int getFadeOut() {
        return fadeOut;
    }

    public int getStay() {
        return stay;
    }

    public void send(Player player) {
        player.sendTitle(title, subtitle, fadeIn, stay, fadeOut);
    }

    public void broadcast() {
        Bukkit.getOnlinePlayers().forEach(this::send);
    }

    public AdaptiveTitle toAdaptive() {
        return AdaptiveTitle.adapt(this);
    }
}
