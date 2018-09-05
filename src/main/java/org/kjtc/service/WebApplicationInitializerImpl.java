package org.kjtc.service;

import org.apache.commons.lang.StringUtils;
import org.kjtc.Application;
import org.kjtc.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration.Dynamic;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

@Configuration
public class WebApplicationInitializerImpl extends WebMvcConfigurerAdapter implements WebApplicationInitializer {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public void onStartup(ServletContext container) throws ServletException {
        WebApplicationContext context = getContext();

        Dynamic registration = container.addServlet("dispatcher", new DispatcherServlet(context));
        registration.setLoadOnStartup(1);
        registration.addMapping("/*");
    }

    private WebApplicationContext getContext() {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.setConfigLocation(Application.class.getName());
        return context;
    }

    @Bean
    SecurityInterceptor  getSecurityInterceptor(){
        return new SecurityInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration addInterceptor = registry.addInterceptor(getSecurityInterceptor());

        addInterceptor.excludePathPatterns("/login**");

        addInterceptor.addPathPatterns("/**");
        super.addInterceptors(registry);
    }

    private class SecurityInterceptor extends HandlerInterceptorAdapter {

        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

            String uuid = (String)request.getSession().getAttribute("UUID");
            if (!StringUtils.isEmpty(uuid) && redisTemplate.hasKey(uuid)) {
                redisTemplate.opsForValue().set(uuid, (User)redisTemplate.opsForValue().get(uuid), 1, TimeUnit.HOURS);
            } else {
                response.sendRedirect("/New/login");
            }
            return super.preHandle(request, response, handler);
        }
    }
}
