package usantatecla.draughts.models;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CoordinateAssertionErrorTest {
    private Coordinate coordinate;

    private final int row = 4;
    private final int column = 5;


    @Before
    public void beforeAll() {
        this.coordinate = coordinate(this.row, this.column);
    }

    @Test(expected = AssertionError.class)
    public void testGetInstanceWithNullThrowsError() {
        Coordinate.getInstance(null);
    }


    @Test(expected = AssertionError.class)
    public void testGetDirectionWithNullInputValue() {
        this.coordinate.getDirection(nullCoordinate());
    }


    @Test(expected = AssertionError.class)
    public void testIsOnDiagonalWithNullInput() {
        this.coordinate.isOnDiagonal(nullCoordinate());
    }


    @Test(expected = AssertionError.class)
    public void testGetDiagonalDistanceNoDistance() {
        Assert.assertEquals(0, this.coordinate.getDiagonalDistance(this.coordinate));
    }

    @Test(expected = AssertionError.class)
    public void getBetweenDiagonalCoordinatesIsNotOnDiagonal() {
        Coordinate coordinate = Direction.NE.getDistanceCoordinate(3);
        this.coordinate.getBetweenDiagonalCoordinates(coordinate);
    }

    @Test(expected = AssertionError.class)
    public void getBetweenCoordinatesNullParameter() {
        Assert.assertNull(this.coordinate.getBetweenDiagonalCoordinates(nullCoordinate()));
    }

    private static Coordinate coordinate(int row, int column) {
        return new Coordinate(row, column);
    }

    private static Coordinate nullCoordinate() {
        return null;
    }
}
