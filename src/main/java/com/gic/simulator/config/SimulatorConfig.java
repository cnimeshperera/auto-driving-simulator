package com.gic.simulator.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class SimulatorConfig {

    @Bean
    public Map<String, String> rotationDirectionConfigMap() {
        var rotationMap = new HashMap<String, String>();
        rotationMap.put("N_L", "W");
        rotationMap.put("N_R", "E");
        rotationMap.put("E_L", "N");
        rotationMap.put("E_R", "S");
        rotationMap.put("S_L", "E");
        rotationMap.put("S_R", "W");
        rotationMap.put("W_L", "S");
        rotationMap.put("W_R", "N");
        return rotationMap;
    }
}
