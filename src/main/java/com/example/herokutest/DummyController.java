package com.example.herokutest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.List;
import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping("/api")
public class DummyController {

    @Autowired
    private ApplicationContext context;
    @GetMapping("/ananas")
    public String dummy(){
        return "Dies Das Ananas";
    }

    @GetMapping("/hunt")
    public String getRandomRickAndMortyCharHuntingYou(){
        int totalChars = ramApi();
        var ran = new Random();
        int randomCharIndex = ran.nextInt(totalChars);
        RamCharacter character = getRamCharacter(randomCharIndex);
        return getHuntingString(character);
    }

    private String getHuntingString(RamCharacter character) {
        StringBuilder str = new StringBuilder();
        str.append(character.getName());
        str.append(" is ");
        str.append(character.getStatus());
        if(!character.getStatus().equalsIgnoreCase("dead")){
            str.append(" and hunting you.");
        }else{
            str.append(" and no danger.");
        }
        return str.toString();
    }

    private RamCharacter getRamCharacter(int id){
        RestTemplate template = new RestTemplate();
        ResponseEntity<RamCharacter> response =  template.getForEntity("https://rickandmortyapi.com/api/character/" + id, RamCharacter.class);
        return response.getBody();
    }

    private int ramApi(){
        RestTemplate template = new RestTemplate();
        ResponseEntity<RamInfo> response =  template.getForEntity("https://rickandmortyapi.com/api/character", RamInfo.class);
        return response.getBody().getInfo().getCount();
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
