package com.mkyong.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

public class AdaptableUserCache implements UserCache, InitializingBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdaptableUserCache.class);

    private Cache cache;

    public AdaptableUserCache(Cache cache) {
        this.cache = cache;
    }

    public void afterPropertiesSet() throws Exception {
//        Assert.notNull(cache, "cache mandatory");
    }

    @Override
    public UserDetails getUserFromCache(String username) {
        ValueWrapper element = cache.get(username.toLowerCase());

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Cache hit: " + (element != null) + "; username: " + username.toLowerCase());
        }

        if (element == null) {
            return null;
        } else {
            return (UserDetails) element.get();
        }
    }

    @Override
    public void putUserInCache(UserDetails user) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Cache put: " + user.getUsername().toLowerCase());
        }

        cache.put(user.getUsername().toLowerCase(), user);
    }

    @Override
    public void removeUserFromCache(String username) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Cache remove: " + username.toLowerCase());
        }
        cache.evict(username.toLowerCase());
    }
}