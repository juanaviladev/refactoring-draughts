package usantatecla.draughts.models;

public class NotEmptyTargetChecker implements LegalMovementChecker {

    private Board board;

    public NotEmptyTargetChecker(Board board) {
        this.board = board;
    }

    public Error check(Move move) {
        if (!board.isEmpty(move.getTarget()))
            return Error.NOT_EMPTY_TARGET;
        else
            return null;
    }

}
