package usantatecla.draughts.models;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class CoordinateParametrizedTest {
    private final int row;
    private final int column;
    private Coordinate coordinate;
    private final String getInstanceOf;
    private final Coordinate expectedGetInstanceCoordinate;
    private final Direction expectedDirection;
    private final Coordinate coordinateInDirection;
    private final boolean expectedIsOnDiagonal;
    private final int expectedDiagonalDistance;
    private final Coordinate coordinateAtDistance;
    private final boolean expectedIsBlack;
    private final boolean expectedIsFirst;

    public CoordinateParametrizedTest(int row, int column, String getInstanceOf,
                                      Coordinate expectedGetInstanceCoordinate, Direction expectedDirection,
                                      Coordinate coordinateInDirection, boolean expectedIsOnDiagonal,
                                      int expectedDiagonalDistance, Coordinate coordinateAtDistance,
                                      boolean expectedIsBlack, boolean expectedIsFirst) {
        this.row = row;
        this.column = column;
        this.getInstanceOf = getInstanceOf;
        this.expectedGetInstanceCoordinate = expectedGetInstanceCoordinate;
        this.expectedDirection = expectedDirection;
        this.coordinateInDirection = coordinateInDirection;
        this.expectedIsOnDiagonal = expectedIsOnDiagonal;
        this.expectedDiagonalDistance = expectedDiagonalDistance;
        this.coordinateAtDistance = coordinateAtDistance;
        this.expectedIsBlack = expectedIsBlack;
        this.expectedIsFirst = expectedIsFirst;
    }

    @Before
    public void setUp() {
        this.coordinate = new Coordinate(this.row, this.column);
    }

    @Parameters(name = "(Test at row: {0} and column: {1})")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {1, 0, "32", coordinate(2, 1), Direction.NE, coordinate(3, 2), true,
                        2, coordinate(3, 2), true, false},
                {3, 2, "45", coordinate(3, 4), Direction.SW, coordinate(1, 0), true,
                        2, coordinate(1, 0), true, false},
                {1, 0, "10", null, null, coordinate(1, 0), false,
                        1, coordinate(2, 1), true, false},
                {2, 0, "99", null, null, coordinate(3, 0), false,
                        8, coordinate(10, 8), false, false},
        });
    }

    @Test
    public void testGetInstance() {
        Assert.assertEquals(this.expectedGetInstanceCoordinate, Coordinate.getInstance(this.getInstanceOf));
    }

    @Test
    public void testGetDirection() {
        Assert.assertEquals(this.expectedDirection, this.coordinate.getDirection(this.coordinateInDirection));
    }

    @Test
    public void testIsOnDiagonal() {
        Assert.assertEquals(this.expectedIsOnDiagonal, this.coordinate.isOnDiagonal(this.coordinateInDirection));
    }

    @Test
    public void testGetDiagonalDistance() {
        Assert.assertEquals(this.expectedDiagonalDistance, this.coordinate.getDiagonalDistance(this.coordinateAtDistance));
    }

    @Test
    public void testIsBlack() {
        Assert.assertEquals(this.expectedIsBlack, this.coordinate.isBlack());
    }

    @Test
    public void testIsFirst() {
        Assert.assertEquals(this.expectedIsFirst, this.coordinate.isFirst());
    }

    private static Coordinate coordinate(int row, int column) {
        return new Coordinate(row, column);
    }
}