package me.akamex.luckapi.bossbar;

import me.akamex.luckapi.placeholder.Placeholder;
import me.akamex.luckapi.placeholder.ValuedPlaceholder;
import me.akamex.luckapi.util.color.ColorUtils;

public class AdaptiveBossbarSession extends BossbarSession {

    private AdaptiveBossbarSession(BossbarSession bossbar) {
        super(bossbar);
    }

    public static AdaptiveBossbarSession adapt(BossbarSession session) {
        return new AdaptiveBossbarSession(session);
    }

    public AdaptiveBossbarSession placeholder(String holder, Object to) {
        bukkitBossBar.setTitle(bossbar.getText().replaceAll(holder, ColorUtils.color(to.toString())));
        return this;
    }

    public <T> AdaptiveBossbarSession placeholder(ValuedPlaceholder<T> placeholder) {
        return placeholder(placeholder.getHolder(), placeholder.getApplier().apply(placeholder.getValue()));
    }

    public <T> AdaptiveBossbarSession placeholder(Placeholder<T> placeholder, T t) {
        return placeholder(placeholder.getHolder(), placeholder.getApplier().apply(t));
    }

}
