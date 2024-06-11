package com.asisge.consultifybackend.configuracion;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.cache.interceptor.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class ConfiguracionCache implements CachingConfigurer {

    static Caffeine<Object, Object> caffeineCacheBuilder() {
        return Caffeine.newBuilder()
                .expireAfterWrite(30, TimeUnit.MINUTES)
                .maximumSize(100);
    }

    @Override
    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.setCaffeine(caffeineCacheBuilder());
        cacheManager.setCacheNames(Arrays.asList("informeProyecto", "informeActividades"));
        return cacheManager;
    }

    @Override
    @Bean
    public CacheResolver cacheResolver() {
        return new UserAwareCacheResolver(cacheManager());
    }

    @Override
    @Bean
    public CacheErrorHandler errorHandler() {
        return new SimpleCacheErrorHandler();
    }

    @Bean
    public KeyGenerator userAwareKeyGenerator() {
        return (target, method, params) -> {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication != null ? authentication.getName() : "anonymous";
            return method.getName() + "-" + username + "-" + Arrays.toString(params);
        };
    }

    static class UserAwareCacheResolver extends SimpleCacheResolver {

        public UserAwareCacheResolver(CacheManager cacheManager) {
            super(cacheManager);
        }

        @Override
        public Collection<? extends Cache> resolveCaches(CacheOperationInvocationContext<?> context) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication != null ? authentication.getName() : "anonymous";

            String cacheName = context.getOperation().getCacheNames().iterator().next() + "-" + username;

            Cache cache = getCacheManager().getCache(cacheName);

            // Crear cache si no existe
            if (cache == null) {
                ((CaffeineCacheManager) getCacheManager()).registerCustomCache(
                        cacheName,
                        caffeineCacheBuilder().build());
                cache = getCacheManager().getCache(cacheName);
            }

            Collection<Cache> caches = new ArrayList<>();
            caches.add(cache);
            return caches;
        }
    }

}
