package com.gic.simulator.dto;

import com.gic.simulator.model.FieldMatrix;
import com.gic.simulator.model.Vehicle;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

@Data
@Builder
public class SimulatorCommand {
    FieldMatrix fieldMatrix;
    List<VehicleCommand> vehicleList;


    @Slf4j
    public static class VehicleCommand {
        @Getter
        private final Vehicle vehicle;
        private final char[] command;
        @Getter
        private int runningCommandIndex;

        public VehicleCommand(Vehicle vehicle, String commands) {
            this.vehicle = vehicle;
            this.command = Optional.ofNullable(commands).orElse("").toCharArray();
        }

        public String getNextCommand() {
            if(!hasNext()) {
                log.error("No more commands");
                runningCommandIndex++;
                return "";
            }
            return String.valueOf(command[runningCommandIndex++]);
        }
        public boolean hasNext() {
            return command != null && runningCommandIndex < command.length;
        }
    }
}
