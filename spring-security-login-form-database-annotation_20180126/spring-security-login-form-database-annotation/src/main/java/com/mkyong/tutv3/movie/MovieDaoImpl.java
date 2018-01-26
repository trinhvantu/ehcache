package com.mkyong.tutv3.movie;

import net.sf.ehcache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;


@Repository("movieDao")
public class MovieDaoImpl implements MovieDao{

	@Autowired
	CacheManager cacheManager;

	@Cacheable(value="movieFindCache", key="#name")
	public Movie findByDirector(String name) {
		System.out.println(cacheManager.getCache("movieFindCache"));

		System.out.println("findByDirector is running...");

		slowQuery(10000L);

		return new Movie(1,"Forrest Gump","Robert Zemeckis");
	}

	@Cacheable(value="movieFindCache2", key="#name")
	public Movie findByDirector2(String name) {
		System.out.println("findByDirector2 is running...");

		slowQuery(10000L);

		return new Movie(1,"Forrest Gump","Robert Zemeckis");
	}
	
	private void slowQuery(long seconds){
		try {
            Thread.sleep(seconds);
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
	}
	
}