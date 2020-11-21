package usantatecla.draughts.models;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;


import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class DirectionParametrizedTest {
    private final Direction direction;
    private final Coordinate coordinate;
    private final boolean expectedIsOnDirection;
    private final int distance;
    private final Coordinate expectedDistancedCoordinate;

    @Parameters(name = "(Direction: {0}, Coordinate: {1}, Distance: {3}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {Direction.NE, new Coordinate(2, 2), true, 2, new Coordinate(2, 2)},
                {Direction.SW, new Coordinate(0, 0), false, 0, new Coordinate(0, 0)},
                {Direction.SE, new Coordinate(2, 2), false, 100, new Coordinate(-100, 100)}
        });
    }

    public DirectionParametrizedTest(Direction direction, Coordinate coordinate, boolean expectedIsOnDirection,
                                     int distance, Coordinate expectedDistancedCoordinate) {
        this.direction = direction;
        this.coordinate = coordinate;
        this.expectedIsOnDirection = expectedIsOnDirection;
        this.distance = distance;
        this.expectedDistancedCoordinate = expectedDistancedCoordinate;
    }

    @Test
    public void testIsOnDirection() {
        Assert.assertEquals(this.expectedIsOnDirection, this.direction.isOnDirection(this.coordinate));
    }

    @Test
    public void testGetDistanceCoordinate() {
        Assert.assertEquals(this.expectedDistancedCoordinate, this.direction.getDistanceCoordinate(this.distance));
    }
}