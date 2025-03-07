//package com.example.RentalService.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//public class CorsConfig {
//    @Bean
//    public WebMvcConfigurer corsConfigurer() {
//        return new WebMvcConfigurer() {
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/**")
//                        .allowedOrigins("http://localhost:5173") // Explicitly allow frontend
//                        .allowedMethods("GET", "POST", "PUT", "DELETE")
//                       
//                        .allowedHeaders("*")
//                        .exposedHeaders("Authorization", "Set-Cookie","Access-Control-Allow-Origin")
//                        .allowCredentials(true);
//            }
//        };
//    }
//}
//
//
////@Configuration
////public class CorsConfig {
////
////    @Bean
////    public CorsFilter corsFilter() {
////        CorsConfiguration config = new CorsConfiguration();
////        config.setAllowedOrigins(List.of("http://localhost:5173")); // Frontend URL
////        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
////        config.setAllowedHeaders(List.of("Authorization", "Content-Type","Set-Cookie"));
////        config.setAllowCredentials(true);
////        config.addExposedHeader("Access-Control-Allow-Origin");
////
////
////        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
////        source.registerCorsConfiguration("/**", config);
////
////        return new CorsFilter(source);
////    }
////}