package com.gic.simulator.service;

import com.gic.simulator.dto.SimulatorCommand;
import com.gic.simulator.model.FieldMatrix;
import com.gic.simulator.model.Vehicle;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
@RequiredArgsConstructor
@Slf4j
public class DestinationSimulator {

    private final Map<String, String> rotationDirectionConfigMap;

    public boolean executeNextCommandForAllVehicles(SimulatorCommand simulatorCommand) {
        AtomicBoolean hasNext = new AtomicBoolean(false);
        var fieldMatrix = simulatorCommand.getFieldMatrix();
        List<SimulatorCommand.VehicleCommand> vehicleList = simulatorCommand.getVehicleList();
        vehicleList.forEach(vehicleCommand -> {
            executeNextCommand(vehicleCommand, fieldMatrix);
            if (vehicleCommand.hasNext()) hasNext.set(true);
        });
        return hasNext.get();
    }

    public void executeAllCommandsForSingleVehicle(SimulatorCommand.VehicleCommand vehicleCommand, FieldMatrix fieldMatrix) {
        if (vehicleCommand.hasNext()) {
            executeNextCommand(vehicleCommand, fieldMatrix);
            executeAllCommandsForSingleVehicle(vehicleCommand, fieldMatrix);
        }
    }

    private void executeNextCommand(SimulatorCommand.VehicleCommand vehicleCommand, FieldMatrix fieldMatrix) {
        var vehicle = vehicleCommand.getVehicle();
        var nextCommand = vehicleCommand.getNextCommand();
        log.debug("Field width: {}, height: {}, startX: {}, startY: {}, startDirection: {}, nextCommand: {}",
                fieldMatrix.getWidth(), fieldMatrix.getHeight(), vehicle.getX(),
                vehicle.getY(), vehicle.getDirection(), nextCommand);
        executeSingleCommand(fieldMatrix, vehicle, nextCommand);
    }

    private void executeSingleCommand(FieldMatrix fieldMatrix, Vehicle vehicle, String command) {
        if ("F".equals(command)) {
            log.debug("Move forward -> command: {}", command);
            vehicle.movePosition(fieldMatrix);
        } else {
            log.debug("Change direction -> command: {}", command);
            vehicle.changeDirection(rotationDirectionConfigMap, command);
        }
    }
}
