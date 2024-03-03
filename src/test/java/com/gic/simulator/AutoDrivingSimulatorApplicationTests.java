package com.gic.simulator;

import com.gic.simulator.config.SimulatorConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AutoDrivingSimulatorApplicationTests {

    @Autowired
    private SimulatorConfig simulatorConfig;

    @Test
    void contextLoads() {
        Assertions.assertEquals(8, simulatorConfig.rotationDirectionConfigMap().size());
    }

}
