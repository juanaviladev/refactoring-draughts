package usantatecla.draughts.models;

public class NotEmptyTargetChecker extends LegalMovementChecker {

    private Board board;

    public NotEmptyTargetChecker(Board board, LegalMovementChecker next) {
        super(next);
        this.board = board;
    }

    public Error check(Move.Pair move) {
        if (!board.isEmpty(move.getTarget()))
            return Error.NOT_EMPTY_TARGET;
        else
            return next(move);
    }

}
