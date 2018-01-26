package com.mkyong.web.controller;

import com.mkyong.config.AppConfig;
import com.mkyong.tutv3.movie.Movie;
import com.mkyong.tutv3.movie.MovieDao;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class MainController {

    @Autowired
    CacheManager cacheManager;

    @Autowired
    MovieDao movieDao;

    @Autowired
    ApplicationContext context;


    @RequestMapping(value = {"/", "/welcome**"}, method = RequestMethod.GET)
    public ModelAndView defaultPage(HttpServletRequest request, HttpServletResponse response) {

//		request.getUserPrincipal().getName();
//		UserDetailsService userDetailsService = new JdbcDaoImpl();

//		System.out.println(obj.loadUserByUsername(request.getUserPrincipal().getName()));

        ModelAndView model = new ModelAndView();
        model.addObject("title", "Spring Security Login Form - Database Authentication");
        model.addObject("message", "This is default page!");
        model.setViewName("hello");
        return model;

    }

    @RequestMapping(value = "/admin**", method = RequestMethod.GET)
    public ModelAndView adminPage() {

        ModelAndView model = new ModelAndView();
        model.addObject("title", "Spring Security Login Form - Database Authentication");
        model.addObject("message", "This page is for ROLE_ADMIN only!");
        model.setViewName("admin");

        return model;

    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(@RequestParam(value = "error", required = false) String error,
                              @RequestParam(value = "logout", required = false) String logout, HttpServletRequest request, HttpServletResponse response) {


        System.out.println(movieDao.findByDirector("dummy").toString());
        System.out.println(movieDao.findByDirector("abc").toString());
        System.out.println(movieDao.findByDirector("123").toString());
        System.out.println(movieDao.findByDirector("dummy").toString());
        System.out.println(movieDao.findByDirector("abc").toString());
        System.out.println(movieDao.findByDirector("123").toString());

        // Test update cache
        Cache cache = cacheManager.getCache("movieFindCache");
        cache.put(new Element("dummy", new Movie(1,"TuTV3","TuTV3")));
        // Xem cache đã được update hay chưa
        System.out.println(movieDao.findByDirector("dummy").toString());

        System.out.println(movieDao.findByDirector2("dummy").toString());
        System.out.println(movieDao.findByDirector2("abc").toString());
        System.out.println(movieDao.findByDirector2("123").toString());
        System.out.println(movieDao.findByDirector2("dummy").toString());
        System.out.println(movieDao.findByDirector2("abc").toString());
        System.out.println(movieDao.findByDirector2("123").toString());

        // Cache shutdown
        //((ConfigurableApplicationContext)context).close();

        System.out.println(request);
        System.out.println(response);
        testCache("abc");
        testCache("cef");
        testCache("efg");
        testCache("abc");
        testCache("mnp");
        testCache("npq");
        testCache("123");

        ModelAndView model = new ModelAndView();
        if (error != null) {
            model.addObject("error", "Invalid username and password!");
        }

        if (logout != null) {
            model.addObject("msg", "You've been logged out successfully.");
        }
        model.setViewName("login");

        return model;

    }

    //for 403 access denied page
    @RequestMapping(value = "/403", method = RequestMethod.GET)
    public ModelAndView accesssDenied() {

        ModelAndView model = new ModelAndView();

        //check if user is login
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            UserDetails userDetail = (UserDetails) auth.getPrincipal();
            System.out.println(userDetail);

            model.addObject("username", userDetail.getUsername());

        }

        model.setViewName("403");
        return model;

    }

    /*@Cacheable(value = "users", key = "#name")*/
    public String testCache(String name) {
        System.out.println("Using cache");
        return name;
    }

}