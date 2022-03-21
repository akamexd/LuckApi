package me.akamex.luckapi.util.function;

import me.akamex.luckapi.util.permission.PermissionNode;
import me.akamex.luckapi.util.permission.PermissionUtil;
import me.akamex.luckapi.util.player.PlayerUtils;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public final class Predicates {

    private Predicates() {
        throw new UnsupportedOperationException();
    }

    public static <T> Predicate<T> alwaysTrue() {
        return t -> true;
    }

    public static <T> Predicate<T> alwaysFalse() {
        return t -> false;
    }

    public static <T> Predicate<T> alwaysRandomly() {
        return t -> ThreadLocalRandom.current().nextBoolean();
    }

    public static <T, K> BiPredicate<T, K> alwaysTrueBi() {
        return (t, k) -> true;
    }

    public static <T, K> BiPredicate<T, K> alwaysFalseBi() {
        return (t, k) -> false;
    }

    public static <T, K> BiPredicate<T, K> alwaysRandomlyBi() {
        return (t, k) -> ThreadLocalRandom.current().nextBoolean();
    }

    public static Predicate<Player> permission(String permission) {
        return player -> PermissionUtil.hasPermission(player, permission);
    }

    public static Predicate<Player> permission(PermissionNode node) {
        return player -> PermissionUtil.hasPermission(player, node);
    }

    public static  Predicate<Player> minimalDistance(Location location, double distance) {
        return player -> PlayerUtils.getDistance(player, location) >= distance;
    }

    public static Predicate<Player> maximalDistance(Location location, double distance) {
        return player -> PlayerUtils.getDistance(player, location) <= distance;
    }

}
