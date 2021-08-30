package com.leyou.upload.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class LeyouCorsConfiguration {
    @Bean
    public CorsFilter crosFilter() {

        // 初始化 cors 配置对象
        CorsConfiguration configuration = new CorsConfiguration();
        // 允许跨域的域名，如果要携带 cookie 不能写 *， * 代表所有域名都可以跨域访问
        configuration.addAllowedOrigin("http://manage.leyou.com");
        // 允许携带 cookie
        configuration.setAllowCredentials(true);
        configuration.addAllowedMethod("*"); // 代表所有的请求方法：GET、POST、PUT、Delete...
        configuration.addAllowedHeader("*"); // 允许携带任何头信息


        // 初始化 cors 配置源对象
        UrlBasedCorsConfigurationSource corsConfigurationSource =  new UrlBasedCorsConfigurationSource();
        corsConfigurationSource.registerCorsConfiguration("/**",configuration);

        // 返回 corsFilter 实例；参数：cors 配置源对象
        return new CorsFilter(corsConfigurationSource);
    }
}
