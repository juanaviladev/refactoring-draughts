package usantatecla.draughts.models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class GameMovementTest {

	Game game;
	
	private static Game game(String... rows) {
		return new GameBuilder().rows(rows).build();
	}
	
	@Test
	public void testGivenBlockedPawnThenReturnTrue() {
		this.game = game(
			"        ",
			"        ",
			" n   n  ",
			"  n n   ",
			"   b    ",
			"        ",
			"        ",
			"        ");
		assertTrue(this.game.isBlocked());
	}
	
	@Test
	public void testGivenUnblockedPawnThenReturnFalse() {
		this.game = game(
				"        ",
				"        ",
				" n      ",
				"  n n   ",
				"   b    ",
				"  n n   ",
				" n   n  ",
				"        ");
		assertFalse(this.game.isBlocked());
	}
	
	@Test
	public void testGivenBlockedDraughtThenReturnTrue() {
		this.game = game(
				"        ",
				"  n     ",
				" n      ",
				"B       ",
				" n      ",
				"  n     ",
				"        ",
				"        ");
		assertTrue(this.game.isBlocked());
	}
	
	@Test
	public void testGivenUnblockedDraughtThenReturnFalse() {
		this.game = game(
				"        ",
				"        ",
				"     n  ",
				"      n ",
				"       B",
				"      n ",
				"        ",
				"        ");
		assertFalse(this.game.isBlocked());
	}
	
	@Test
	public void testWhenPairMoveAndMoveVerticalThenError() {
		this.game = game(
				"        ",
				"      n ",
				"        ",
				"      n ",
				"        ",
				"      n ",
				"     b  ",
				"        ");
		Error error = move(coordinate(6, 5), coordinate(4, 7), coordinate(3, 7));
		assertEquals(Error.NOT_DIAGONAL, error);
	}
	
	@Test
	public void testWhenPairMoveAndErrorThenRestorePiecesCorrectly() {
		this.game = game(
				"       n",
				"      n ",
				"        ",
				"      n ",
				"        ",
				"      N ",
				"     b  ",
				"        ");
		Error error = move(coordinate(6, 5), coordinate(4, 7), coordinate(2, 5), coordinate(0, 7));
		assertEquals(Error.NOT_EMPTY_TARGET, error);
		assertTrue(piece(coordinate(3, 6)) instanceof Pawn);
		assertTrue(piece(coordinate(5, 6)) instanceof Draught);
	}
	
	@Test
	public void testWhenMoreThanTwoMovementsWithoutEatingInEachMovementThenError() {
		this.game = game(
				"       n",
				"      n ",
				"        ",
				"      n ",
				"        ",
				"      N ",
				"     b  ",
				"        ");
		Error error = move(coordinate(6, 5), coordinate(4, 7), coordinate(2, 5), coordinate(1, 4));
		assertEquals(Error.TOO_MUCH_JUMPS, error);
	}
	
	@Test
	public void testWhenMoreThanTwoMovementsWithoutEatingThenError() {
		this.game = game(
				"       n",
				"      n ",
				"        ",
				"      n ",
				"        ",
				"      N ",
				"     b  ",
				"        ");
		Error error = move(coordinate(6, 5), coordinate(5, 4), coordinate(4, 3));
		assertEquals(Error.TOO_MUCH_JUMPS, error);
	}
	
	@Test
	public void testWhenEatingMultiplePiecesReachingLimitThenConvertIntoDraught() {
		this.game = game(
				"        ",
				"  n     ",
				"        ",
				"    n   ",
				"        ",
				"      N ",
				"       b",
				"        ");
		Error error = move(coordinate(6, 7), coordinate(4, 5), coordinate(2, 3), coordinate(0, 1));
		assertNull(error);
		assertTrue(piece(coordinate(0, 1)) instanceof Draught);
		assertNull(piece(coordinate(1, 2)));
	}
	
	@Test
	public void testWhenMovingBackWhitesThenError() {
		this.game = game(
				"        ",
				"        ",
				"        ",
				"        ",
				"        ",
				"    n N ",
				"       b",
				"        ");
		Error error = move(coordinate(6, 7), coordinate(4, 5), coordinate(6, 3));
		assertEquals(Error.NOT_ADVANCED, error);
	}
	
	@Test
	public void testWhenMovingBackBlacksThenError() {
		this.game = game(
				"        ",
				"        ",
				"        ",
				"        ",
				"        ",
				"    n   ",
				"       b",
				"        ");
		this.game.cancel();
		Error error = move(coordinate(5, 4), coordinate(4, 3));
		assertEquals(Error.NOT_ADVANCED, error);
	}
	
	@Test
	public void testWhenMovingOppositePieceThenError() {
		this.game = game(
				"        ",
				"        ",
				"        ",
				"        ",
				"        ",
				"        ",
				"       n",
				"        ");
		Error error = move(coordinate(6, 7), coordinate(5, 6));
		assertEquals(Error.OPPOSITE_PIECE, error);
	}
	
	@Test
	public void testWhenSelectingEmptyOriginThenReturnError() {
		this.game = game(
				"        ",
				"        ",
				"        ",
				"        ",
				"        ",
				"        ",
				"       n",
				"        ");
		Error error = move(coordinate(3, 0), coordinate(2, 1));
		assertEquals(Error.EMPTY_ORIGIN, error);
	}
	
	@Test
	public void testWhenMovingDoubleThenError() {
		this.game = game(
				"        ",
				"        ",
				"        ",
				"        ",
				"        ",
				"        ",
				"       b",
				"        ");
		Error error = move(coordinate(6, 7), coordinate(4, 5));
		assertEquals(Error.WITHOUT_EATING, error);
	}
	
	@Test
	public void testWhenMovingTripleThenError() {
		this.game = game(
				"        ",
				"        ",
				"        ",
				"        ",
				"        ",
				"        ",
				"       b",
				"        ");
		Error error = move(coordinate(6, 7), coordinate(3, 4));
		assertEquals(Error.TOO_MUCH_ADVANCED, error);
	}
	
	@Test
	public void testWhenEatingColleagueThenError() {
		this.game = game(
				"        ",
				"        ",
				"        ",
				"        ",
				"        ",
				"      b ",
				"       b",
				"        ");
		Error error = move(coordinate(6, 7), coordinate(4, 5));
		assertEquals(Error.COLLEAGUE_EATING, error);
	}
	
	@Test
	public void testWhenPawnEatingTwoThenError() {
		this.game = game(
				"        ",
				"        ",
				"        ",
				"        ",
				"     n  ",
				"      n ",
				"       b",
				"        ");
		Error error = move(coordinate(6, 7), coordinate(3, 4));
		assertEquals(Error.TOO_MUCH_ADVANCED, error);
	}
	
	@Test
	public void testWhenDraughtEatingTwoThenError() {
		this.game = game(
				"        ",
				"        ",
				"        ",
				"        ",
				"     n  ",
				"      n ",
				"       B",
				"        ");
		Error error = move(coordinate(6, 7), coordinate(3, 4));
		assertEquals(Error.TOO_MUCH_EATINGS, error);
	}
	
	@Test
	public void testWhenMovingDraughtVerticalThenError() {
		this.game = game(
				"        ",
				"        ",
				"        ",
				"        ",
				"     n  ",
				"      n ",
				"       B",
				"        ");
		Error error = move(coordinate(6, 7), coordinate(5, 7));
		assertEquals(Error.NOT_DIAGONAL, error);
	}
	
	@Test
	public void testWhenMovingPawnHorizontalThenError() {
		this.game = game(
				"        ",
				"        ",
				"        ",
				"        ",
				"     n  ",
				"      n ",
				"       b",
				"        ");
		Error error = move(coordinate(6, 7), coordinate(6, 6));
		assertEquals(Error.NOT_DIAGONAL, error);
	}
	
	@Test
	public void testWhenMovingPawnToSamePositionThenError() {
		this.game = game(
				"        ",
				"        ",
				"        ",
				"        ",
				"     n  ",
				"      n ",
				"       b",
				"        ");
		Error error = move(coordinate(6, 7), coordinate(6, 7));
		assertEquals(Error.NOT_EMPTY_TARGET, error);
	}
	
	private Coordinate coordinate(int row, int column) {
		return new Coordinate(row, column);
	}
	
	private Piece piece(Coordinate coordinate) {
		return this.game.getPiece(coordinate);
	}
	
	private Error move(Coordinate... coordinates) {
		return this.game.move(coordinates);
	}
}
