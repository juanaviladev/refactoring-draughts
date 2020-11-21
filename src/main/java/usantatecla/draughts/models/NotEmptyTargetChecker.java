package usantatecla.draughts.models;

public class NotEmptyTargetChecker implements LegalMovementChecker {

    private Board board;

    public NotEmptyTargetChecker(Board board) {
        this.board = board;
    }

    public Error check(int pair, Coordinate... coordinates) {
        if (!board.isEmpty(coordinates[pair + 1]))
            return Error.NOT_EMPTY_TARGET;
        else
            return null;
    }

}
