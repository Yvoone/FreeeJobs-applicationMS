package com.freeejobs.jobApplication;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
@EnableWebMvc
public class WebConfig implements Filter,WebMvcConfigurer {


	private static Logger LOGGER = LogManager.getLogger(WebConfig.class);

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**");
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) {
      HttpServletResponse response = (HttpServletResponse) res;
      HttpServletRequest request = (HttpServletRequest) req;
      System.out.println("WebConfig; "+request.getRequestURI());
      response.setHeader("Access-Control-Allow-Origin", "https://freeejobs-web.herokuapp.com");
      response.setHeader("Access-Control-Allow-Methods", "POST, PATCH, PUT, GET, OPTIONS, DELETE");
      response.setHeader("Access-Control-Allow-Headers", "Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With,observe, x-xsrf-token");
      response.setHeader("Access-Control-Max-Age", "3600");
      response.setHeader("Access-Control-Allow-Credentials", "true");
      response.setHeader("Access-Control-Expose-Headers", "Authorization");
      response.addHeader("Access-Control-Expose-Headers", "responseType");
      response.addHeader("Access-Control-Expose-Headers", "observe");
      response.addHeader("Access-Control-Allow-Headers", "x-xsrf-token");
      System.out.println("Request Method: "+request.getMethod());
      if (!(request.getMethod().equalsIgnoreCase("OPTIONS"))) {
          try {
              chain.doFilter(req, res);
          } catch(Exception e) {
        	  LOGGER.error(e.getMessage(), e);
          }
      } else {
          System.out.println("Pre-flight");
          response.setHeader("Access-Control-Allow-Origin", "https://freeejobs-web.herokuapp.com");
          response.setHeader("Access-Control-Allow-Methods", "POST,GET,DELETE,PUT,OPTION");
          response.setHeader("Access-Control-Max-Age", "3600");
          response.setHeader("Access-Control-Allow-Headers", "Access-Control-Expose-Headers"+"Authorization, content-type," +
          "USERID"+"ROLE"+
                  "access-control-request-headers,access-control-request-method,accept,origin,authorization,x-requested-with,responseType,observe, x-xsrf-token");
          response.setStatus(HttpServletResponse.SC_OK);
      }

    }

}
