package me.akamex.luckapi.provider.economy;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public abstract class PooledEconomyProvider implements EconomyProvider {

    protected final Cache<String, EconomicUser> cache;

    protected PooledEconomyProvider() {
        cache = CacheBuilder.newBuilder()
                .expireAfterWrite(3, TimeUnit.MINUTES)
                .expireAfterAccess(3, TimeUnit.MINUTES)
                .build();
    }

    protected abstract Callable<EconomicUser> getEconomicUser(String name);

    @Override
    public EconomicUser getUser(String name) {
        try {
            return cache.get(name, getEconomicUser(name));
        } catch (ExecutionException exception) {
            exception.printStackTrace();
            return new EmptyEconomicUser(name, this);
        }
    }
}
