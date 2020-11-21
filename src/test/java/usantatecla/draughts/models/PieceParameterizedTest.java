package usantatecla.draughts.models;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class PieceParameterizedTest {

	Piece piece;
	
	Coordinate coordinateToTestLimit;
	boolean expectedIsLimit;
	
	Coordinate coordinateOriginAdvance;
	Coordinate coordinateTargetAdvance;
	boolean expectedIsAdvancing;
	
	List<Piece> betweenDiagonalPieces;
	int pair;
	Coordinate[] coordinatesCorrectMovement;
	Error expectedIsCorrectMovement;
	
	public PieceParameterizedTest(Piece piece, Coordinate coordinateToTestLimit, boolean expectedIsLimit,
			Coordinate coordinateOriginAdvance, Coordinate coordinateTargetAdvance, boolean expectedIsAdvancing,
			List<Piece> betweenDiagonalPieces, int pair, Coordinate[] coordinatesCorrectMovement,
			Error expectedIsCorrectMovement) {
		this.piece = piece;
		this.coordinateToTestLimit = coordinateToTestLimit;
		this.expectedIsLimit = expectedIsLimit;
		this.coordinateOriginAdvance = coordinateOriginAdvance;
		this.coordinateTargetAdvance = coordinateTargetAdvance;
		this.expectedIsAdvancing = expectedIsAdvancing;
		this.betweenDiagonalPieces = betweenDiagonalPieces;
		this.pair = pair;
		this.coordinatesCorrectMovement = coordinatesCorrectMovement;
		this.expectedIsCorrectMovement = expectedIsCorrectMovement;
	}
	
	@Parameters
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] {
				{ pawn(white()),
					coordinate(0, 0), true,
					coordinate(5, 4), coordinate(4, 5), true,
					Arrays.asList(pawn(black())), 0, new Coordinate[] { coordinate(5, 4), coordinate(3, 6) }, null },
				{ pawn(black()),
					coordinate(7, 3), true,
					coordinate(5, 4), coordinate(4, 5), false,
					Arrays.asList(pawn(white())), 0, new Coordinate[] { coordinate(3, 6), coordinate(4, 6) }, Error.NOT_DIAGONAL },
				{ pawn(black()),
					coordinate(0, 7), false,
					coordinate(3, 4), coordinate(4, 5), true,
					Arrays.asList(pawn(black())), 0, new Coordinate[] { coordinate(3, 6), coordinate(5, 4) }, Error.COLLEAGUE_EATING },
				{ pawn(white()),
					coordinate(1, 7), false,
					coordinate(0, 0), coordinate(7, 7), false,
					Arrays.asList(), 0, new Coordinate[] { coordinate(7, 7), coordinate(0, 0) }, Error.TOO_MUCH_ADVANCED },
				{ pawn(white()),
					coordinate(6, 4), false,
					coordinate(7, 0), coordinate(0, 7), true,
					Arrays.asList(), 0, new Coordinate[] { coordinate(7, 0), coordinate(5, 2) }, Error.WITHOUT_EATING },
				{ draught(white()),
					coordinate(5, 0), false,
					coordinate(7, 7), coordinate(0, 0), true,
					Arrays.asList(pawn(black()), pawn(black())), 0, new Coordinate[] { coordinate(3, 6), coordinate(5, 4) }, Error.TOO_MUCH_EATINGS }
			});
	}
	
	@Test
	public void testIsLimit() {
		assertEquals(expectedIsLimit, this.piece.isLimit(coordinateToTestLimit));
	}
	
	@Test
	public void testIsAdvanced() {
		assertEquals(expectedIsAdvancing, this.piece.isAdvanced(coordinateOriginAdvance, coordinateTargetAdvance));
	}
	
	@Test
	public void testIsCorrectMovement() {
		assertEquals(expectedIsCorrectMovement, this.piece.isCorrectMovement(betweenDiagonalPieces, new Move.Pair(coordinatesCorrectMovement[pair], coordinatesCorrectMovement[pair+1])));
	}
	
	private static Draught draught(Color color) {
		return new Draught(color);
	}
	
	private static Pawn pawn(Color color) {
		return new Pawn(color);
	}
	
	private static Coordinate coordinate(int row, int column) {
		return new Coordinate(row, column);
	}
	
	private static Color white() {
		return Color.WHITE;
	}
	
	private static Color black() {
		return Color.BLACK;
	}
}
