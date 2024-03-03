package com.gic.simulator.service;

import com.gic.simulator.dto.SimulatorCommand;
import com.gic.simulator.model.FieldMatrix;
import com.gic.simulator.model.Vehicle;
import com.gic.simulator.service.CollisionSimulator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class CollisionSimulatorTest {

    @Autowired
    private CollisionSimulator collisionSimulator;


    @Test
    void testFindCollisionsPart2() {
        var fieldMatrix = new FieldMatrix(10, 10);
        var vehicleA = new Vehicle(1, 2, "N", "A");
        var vehicleCommandA = new SimulatorCommand.VehicleCommand(vehicleA, "FFRFFFFRRL");

        var vehicleB = new Vehicle(7, 8, "W", "B");
        var vehicleCommandB = new SimulatorCommand.VehicleCommand(vehicleB, "FFLFFFFFFF");

        var simulatorCommand = SimulatorCommand.builder()
                .fieldMatrix(fieldMatrix)
                .vehicleList(List.of(vehicleCommandA, vehicleCommandB))
                .build();
        List<String> simulatorCollisions = collisionSimulator.findCollisions(simulatorCommand);
        Assertions.assertEquals("A B".concat("\n").concat("5 4").concat("\n").concat("7"),
                simulatorCollisions.get(0), "Collision found -> step: 7, vehicle: A,B, position: 5 4");

    }

    @Test
    void testNoCollisions() {
        var fieldMatrix = new FieldMatrix(10, 10);
        var vehicleA = new Vehicle(0, 0, "N", "A");
        var vehicleCommandA = new SimulatorCommand.VehicleCommand(vehicleA, "FFFFFFFF");

        var vehicleB = new Vehicle(5, 0, "N", "B");
        var vehicleCommandB = new SimulatorCommand.VehicleCommand(vehicleB, "FFFFFFFF");

        var vehicleC = new Vehicle(8, 0, "N", "C");
        var vehicleCommandC = new SimulatorCommand.VehicleCommand(vehicleC, "FFFFFFFF");

        var simulatorCommand = SimulatorCommand.builder()
                .fieldMatrix(fieldMatrix)
                .vehicleList(List.of(vehicleCommandA, vehicleCommandB, vehicleCommandC))
                .build();
        List<String> simulatorCollisions = collisionSimulator.findCollisions(simulatorCommand);
        Assertions.assertEquals("no collision",
                simulatorCollisions.get(0), "No collision");

    }

    @Test
    void testNoCollisions1() {
        var fieldMatrix = new FieldMatrix(10, 10);
        var vehicleA = new Vehicle(0, 0, "N", "A");
        var vehicleCommandA = new SimulatorCommand.VehicleCommand(vehicleA, "FFFF");

        var vehicleB = new Vehicle(5, 0, "N", "B");
        var vehicleCommandB = new SimulatorCommand.VehicleCommand(vehicleB, "FFFFLFFFFF");

        var vehicleC = new Vehicle(8, 0, "N", "C");
        var vehicleCommandC = new SimulatorCommand.VehicleCommand(vehicleC, "FFFFFFFF");

        var simulatorCommand = SimulatorCommand.builder()
                .fieldMatrix(fieldMatrix)
                .vehicleList(List.of(vehicleCommandA, vehicleCommandB))
                .build();
        List<String> simulatorCollisions = collisionSimulator.findCollisions(simulatorCommand);
        Assertions.assertEquals("A B".concat("\n").concat("0 4").concat("\n").concat("10"),
                simulatorCollisions.get(0), "Collision found -> step: 10, vehicle: A,B, position: 0 4");

    }
}