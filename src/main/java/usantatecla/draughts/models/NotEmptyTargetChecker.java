package usantatecla.draughts.models;

public class NotEmptyTargetChecker implements LegalMovementChecker {

    private Board board;
    private LegalMovementChecker next;

    public NotEmptyTargetChecker(Board board, LegalMovementChecker next) {
        this.board = board;
        this.next = next;
    }

    public Error check(Move.Pair move) {
        if (!board.isEmpty(move.getTarget()))
            return Error.NOT_EMPTY_TARGET;
        else if(next != null)
            return next.check(move);
        else
            return null;
    }

}
