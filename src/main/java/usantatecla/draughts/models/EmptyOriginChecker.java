package usantatecla.draughts.models;

public class EmptyOriginChecker implements LegalMovementChecker {

    private Board board;
    private LegalMovementChecker next;

    public EmptyOriginChecker(Board board, LegalMovementChecker next) {
        this.board = board;
        this.next = next;
    }

    @Override
    public Error check(Move.Pair move) {
        if (board.isEmpty(move.getOrigin()))
            return Error.EMPTY_ORIGIN;
        else if(next != null)
            return next.check(move);
        else
            return null;
    }
}
