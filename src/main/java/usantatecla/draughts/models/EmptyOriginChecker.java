package usantatecla.draughts.models;

public class EmptyOriginChecker implements LegalMovementChecker {

    @Override
    public Error check(Board board, Turn turn, int pair, Coordinate... coordinates) {
        if (board.isEmpty(coordinates[pair]))
            return Error.EMPTY_ORIGIN;
        else
            return null;
    }
}
