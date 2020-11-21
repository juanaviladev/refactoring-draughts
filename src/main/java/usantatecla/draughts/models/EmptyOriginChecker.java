package usantatecla.draughts.models;

public class EmptyOriginChecker implements LegalMovementChecker {

    private Board board;

    public EmptyOriginChecker(Board board) {
        this.board = board;
    }

    @Override
    public Error check(int pair, Coordinate... coordinates) {
        if (board.isEmpty(coordinates[pair]))
            return Error.EMPTY_ORIGIN;
        else
            return null;
    }
}
