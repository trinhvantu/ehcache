package com.mkyong.tutv3.test;
 
import com.mkyong.tutv3.movie.MovieDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class App {
	
	private static final Logger log = LoggerFactory.getLogger(App.class);
	
	public static void main(String[] args) {
     
		ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
	    MovieDao obj = (MovieDao) context.getBean("movieDao");
	    
	    log.debug("Result : {}", obj.findByDirector("dummy"));
	    log.debug("Result : {}", obj.findByDirector("dummy"));
	    log.debug("Result : {}", obj.findByDirector("dummy"));


		log.debug("Result : {}", obj.findByDirector2("dummy"));
		log.debug("Result : {}", obj.findByDirector2("dummy"));
		log.debug("Result : {}", obj.findByDirector2("dummy"));
	    //shut down the Spring context so that Ehcache got chance to shut down as well
	    ((ConfigurableApplicationContext)context).close();
	    
	}
}