package com.gic.simulator.service;

import com.gic.simulator.dto.SimulatorCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CollisionSimulator {

    private final DestinationSimulator destinationSimulator;
    public List<String> findCollisions(SimulatorCommand simulatorCommand) {
        var vehicleList = simulatorCommand.getVehicleList();
        boolean hasMoreCommands = true;
        AtomicBoolean collisionFound = new AtomicBoolean(false);
        Map<String, List<SimulatorCommand.VehicleCommand>> positionMap = null;
        while (hasMoreCommands && !collisionFound.get()) {
            positionMap = new HashMap<>();
            hasMoreCommands = destinationSimulator.executeNextCommandForAllVehicles(simulatorCommand);
            Map<String, List<SimulatorCommand.VehicleCommand>> finalPositionMap = positionMap;
            vehicleList.forEach(vehicleCommand -> checkForCollisions(vehicleCommand, finalPositionMap, collisionFound));
        }
        if(collisionFound.get()) {
            return Optional.ofNullable(positionMap).orElse(Collections.emptyMap())
                    .entrySet().stream().filter(entry -> entry.getValue().size() > 1)
                    .map(this::generateCollisionOutputMessage)
                    .toList();
        }
        return List.of("no collision");
    }

    private String generateCollisionOutputMessage(Map.Entry<String, List<SimulatorCommand.VehicleCommand>> entry) {
        return entry.getValue().stream()
                .map(v -> v.getVehicle().getName()).collect(Collectors.joining(" "))
                .concat("\n")
                .concat(entry.getKey())
                .concat("\n")
                .concat(Integer.toString(entry.getValue().get(0).getRunningCommandIndex()));
    }

    private void checkForCollisions(SimulatorCommand.VehicleCommand vehicleCommand,
                                    Map<String, List<SimulatorCommand.VehicleCommand>> finalPositionMap,
                                    AtomicBoolean collisionFound) {
        var vehicle = vehicleCommand.getVehicle();
        String positionKey = String.format("%s %s", vehicle.getX(), vehicle.getY());
        if(finalPositionMap.containsKey(positionKey)) {
            collisionFound.set(true);
            finalPositionMap.get(positionKey).add(vehicleCommand);
        } else {
            var vehicleCommands = new ArrayList<SimulatorCommand.VehicleCommand>();
            vehicleCommands.add(vehicleCommand);
            finalPositionMap.put(positionKey, vehicleCommands);
        }
    }

}
