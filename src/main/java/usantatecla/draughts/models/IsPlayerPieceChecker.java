package usantatecla.draughts.models;

public class IsPlayerPieceChecker {

    public Error check(Board board, Turn turn, int pair, Coordinate... coordinates) {
        if (turn.getOppositeColor() == board.getColor(coordinates[pair]))
            return Error.OPPOSITE_PIECE;
        else
            return null;
    }

}
