package com.gic.simulator.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Data
@Slf4j
@AllArgsConstructor
public class Vehicle {
    private int x;
    private int y;
    private String direction;
    private String name;

    public void movePosition(FieldMatrix fieldMatrix) {
        switch (direction) {
            case "N":
                move(fieldMatrix, x, y + 1);
                break;
            case "E":
                move(fieldMatrix, x + 1, y);
                break;
            case "S":
                move(fieldMatrix, x, y - 1);
                break;
            case "W":
                move(fieldMatrix, x - 1, y);
                break;
            default:
                log.warn("Unknown Direction");
        }
    }

    private void move(FieldMatrix fieldMatrix, int nextX, int nextY) {
        int width = fieldMatrix.getWidth();
        int height = fieldMatrix.getHeight();
        if(nextX >= width || nextX < 0 || nextY >= height || nextY < 0 ) {
            log.warn("Cannot move -> currentX: {}, currentY: {}", x, y);
            return;
        }
        x = nextX;
        y = nextY;
        log.debug("Moved -> currentX: {}, currentY: {}", x, y);
    }

    public void changeDirection(Map<String,String> rotationDirectionConfigMap, String command) {
        var directionKey = String.format("%s_%s", direction, command);
        var newDirection = rotationDirectionConfigMap.getOrDefault(directionKey, direction);
        log.debug("Direction changed -> command: {}, previousDirection: {}, newDirection, {}",
                command, direction, newDirection);
        direction = newDirection;
    }

    public String toString() {
        return String.format("%s %s %s", x, y, direction);
    }
}
