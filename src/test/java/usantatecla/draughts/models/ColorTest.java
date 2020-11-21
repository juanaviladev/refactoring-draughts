package usantatecla.draughts.models;

import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ColorTest {
    Color white = Color.WHITE;
    Color black = Color.BLACK;

    @Test
    public void testIsInitialRow() {
        Assert.assertTrue(black.isInitialRow(0));
        Assert.assertTrue(black.isInitialRow(2));
        Assert.assertFalse(black.isInitialRow(3));

        Assert.assertTrue(white.isInitialRow(5));
        Assert.assertTrue(white.isInitialRow(8));
    }

    @Test
    public void testGetInitialColorWhenCoordinateIsOnInitialRowsShouldReturnColor() {
        Assert.assertEquals(black, Color.getInitialColor(coordinate(0, 1)));
        Assert.assertEquals(white, Color.getInitialColor(coordinate(5, 0)));
    }

    @Test
    public void testGetInitialColorWhenCoordinateIsWhiteShouldReturnNull() {
        assertThat(Color.getInitialColor(coordinate(0, 0)), is(equalTo(Color.NULL)));
    }

    @Test
    public void testGetInitialColorWhenCoordinateDoesntHavePieceShouldReturnNull() {
        assertThat(Color.getInitialColor(coordinate(3, 3)), is(equalTo(Color.NULL)));
        assertThat(Color.getInitialColor(coordinate(4, 5)), is(equalTo(Color.NULL)));
    }

    private static Coordinate coordinate(int row, int column) {
        return new Coordinate(row, column);
    }
}