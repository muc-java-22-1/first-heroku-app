package com.example.herokutest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class DummyController {

    @Autowired
    private ApplicationContext context;
    @GetMapping("/ananas")
    public String dummy(){
        return "Dies Das Ananas";
    }

    @GetMapping
    public List<String> getMappings(){
        return getMappingsFromContext();
    }

    public List<String> getMappingsFromContext() {
        RequestMappingHandlerMapping requestMappingHandlerMapping = context
                .getBean("requestMappingHandlerMapping", RequestMappingHandlerMapping.class);
        Map<RequestMappingInfo, HandlerMethod> mappings = requestMappingHandlerMapping.getHandlerMethods();
        return mappings.entrySet().stream().filter(e->e.getValue().toString().contains("com.example.herokutest")).map(e -> e.getKey().toString()).toList();
    }

}
