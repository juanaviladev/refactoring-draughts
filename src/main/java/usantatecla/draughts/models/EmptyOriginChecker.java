package usantatecla.draughts.models;

public class EmptyOriginChecker extends LegalMovementChecker {

    private Board board;

    public EmptyOriginChecker(Board board, LegalMovementChecker next) {
        super(next);
        this.board = board;
    }

    @Override
    public Error check(Move.Pair move) {
        if (board.isEmpty(move.getOrigin()))
            return Error.EMPTY_ORIGIN;
        else
            return next(move);
    }
}
