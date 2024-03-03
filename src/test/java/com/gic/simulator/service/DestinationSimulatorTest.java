package com.gic.simulator.service;

import com.gic.simulator.dto.SimulatorCommand;
import com.gic.simulator.model.FieldMatrix;
import com.gic.simulator.model.Vehicle;
import com.gic.simulator.service.DestinationSimulator;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class DestinationSimulatorTest {

    @Autowired
    private DestinationSimulator destinationSimulator;

    @SneakyThrows
    @Test
    void testChangeDirection() {
        var fieldMatrix = new FieldMatrix(3, 3);
        var vehicle = new Vehicle(0, 0, "N", "A");
        var vehicleCommand = new SimulatorCommand.VehicleCommand(vehicle, "LLLLRRRRRT");
        Method executeNextCommand = ReflectionUtils.findMethod(DestinationSimulator.class, "executeNextCommand",
                SimulatorCommand.VehicleCommand.class, FieldMatrix.class);

        if (executeNextCommand == null) {
            throw new AssertionError(
                    "Method not found -> class: DestinationSimulator.class Method: executeNextCommand");
        }
        executeNextCommand.setAccessible(true);
        executeNextCommand.invoke(destinationSimulator, vehicleCommand, fieldMatrix);
        assertEquals("0 0 W", vehicle.toString(), "Turn Left from North");

        executeNextCommand.invoke(destinationSimulator, vehicleCommand, fieldMatrix);
        assertEquals("0 0 S", vehicle.toString(), "Turn Left from West");

        executeNextCommand.invoke(destinationSimulator, vehicleCommand, fieldMatrix);
        assertEquals("0 0 E", vehicle.toString(), "Turn Left from South");

        executeNextCommand.invoke(destinationSimulator, vehicleCommand, fieldMatrix);
        assertEquals("0 0 N", vehicle.toString(), "Turn Left from East");

        executeNextCommand.invoke(destinationSimulator, vehicleCommand, fieldMatrix);
        assertEquals("0 0 E", vehicle.toString(), "Turn Right from North");

        executeNextCommand.invoke(destinationSimulator, vehicleCommand, fieldMatrix);
        assertEquals("0 0 S", vehicle.toString(), "Turn Right from East");

        executeNextCommand.invoke(destinationSimulator, vehicleCommand, fieldMatrix);
        assertEquals("0 0 W", vehicle.toString(), "Turn Right from South");

        executeNextCommand.invoke(destinationSimulator, vehicleCommand, fieldMatrix);
        assertEquals("0 0 N", vehicle.toString(), "Turn Right from West");

        executeNextCommand.invoke(destinationSimulator, vehicleCommand, fieldMatrix);
        assertEquals("0 0 E", vehicle.toString(), "Turn Right from North");

        executeNextCommand.invoke(destinationSimulator, vehicleCommand, fieldMatrix);
        assertEquals("0 0 E", vehicle.toString(), "Invalid Direction");
    }

    @SneakyThrows
    @Test
    void testMoveForward() {
        var fieldMatrix = new FieldMatrix(3, 3);
        var vehicle = new Vehicle(0, 0, "N", "A");
        var vehicleCommand = new SimulatorCommand.VehicleCommand(vehicle, "FRFRFRFF");
        Method executeNextCommand = ReflectionUtils.findMethod(DestinationSimulator.class, "executeNextCommand",
                SimulatorCommand.VehicleCommand.class, FieldMatrix.class);

        if (executeNextCommand == null) {
            throw new AssertionError(
                    "Method not found -> class: DestinationSimulator.class Method: executeNextCommand");
        }
        executeNextCommand.setAccessible(true);
        executeNextCommand.invoke(destinationSimulator, vehicleCommand, fieldMatrix);
        assertEquals("0 1 N", vehicle.toString(), "Move Forward from North");

        executeNextCommand.invoke(destinationSimulator, vehicleCommand, fieldMatrix);
        assertEquals("0 1 E", vehicle.toString(), "Turn Right from North");

        executeNextCommand.invoke(destinationSimulator, vehicleCommand, fieldMatrix);
        assertEquals("1 1 E", vehicle.toString(), "Move Forward from North");

        executeNextCommand.invoke(destinationSimulator, vehicleCommand, fieldMatrix);
        assertEquals("1 1 S", vehicle.toString(), "Turn Right from East");

        executeNextCommand.invoke(destinationSimulator, vehicleCommand, fieldMatrix);
        assertEquals("1 0 S", vehicle.toString(), "Move Forward from East");

        executeNextCommand.invoke(destinationSimulator, vehicleCommand, fieldMatrix);
        assertEquals("1 0 W", vehicle.toString(), "Turn Right from South");

        executeNextCommand.invoke(destinationSimulator, vehicleCommand, fieldMatrix);
        assertEquals("0 0 W", vehicle.toString(), "Move Forward from South");

        executeNextCommand.invoke(destinationSimulator, vehicleCommand, fieldMatrix);
        assertEquals("0 0 W", vehicle.toString(), "Try to Move Forward from West Boundary");
    }

    @Test
    void testChangeDirectionAndMoveForward() {
        var fieldMatrix = new FieldMatrix(3, 3);
        var vehicle = new Vehicle(0, 0, "N", "A");
        var vehicleCommand = new SimulatorCommand.VehicleCommand(vehicle, "FFRFFFFRFLFRFRF");
        Method executeAllCommandsForSingleVehicle = ReflectionUtils.findMethod(DestinationSimulator.class,
                "executeAllCommandsForSingleVehicle", SimulatorCommand.VehicleCommand.class, FieldMatrix.class);

        if (executeAllCommandsForSingleVehicle == null) {
            throw new AssertionError(
                    "Method not found -> class: DestinationSimulator.class Method: executeAllCommandsForSingleVehicle");
        }
        executeAllCommandsForSingleVehicle.setAccessible(true);
        destinationSimulator.executeAllCommandsForSingleVehicle(vehicleCommand, fieldMatrix);
        assertEquals("1 0 W", vehicle.toString(), "Change Direction And Move Forward 1");
    }

    @Test
    void testChangeDirectionAndMoveForwardPart1() {
        var fieldMatrix = new FieldMatrix(10, 10);
        var vehicle = new Vehicle(1, 2, "N", "A");
        var vehicleCommand = new SimulatorCommand.VehicleCommand(vehicle, "FFRFFFRRLF");
        Method executeAllCommandsForSingleVehicle = ReflectionUtils.findMethod(DestinationSimulator.class,
                "executeAllCommandsForSingleVehicle", SimulatorCommand.VehicleCommand.class, FieldMatrix.class);

        if (executeAllCommandsForSingleVehicle == null) {
            throw new AssertionError(
                    "Method not found -> class: DestinationSimulator.class Method: executeAllCommandsForSingleVehicle");
        }
        executeAllCommandsForSingleVehicle.setAccessible(true);
        destinationSimulator.executeAllCommandsForSingleVehicle(vehicleCommand, fieldMatrix);
        assertEquals("4 3 S", vehicle.toString(), "Change Direction And Move Forward Part 1");
    }

}