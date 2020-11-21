package usantatecla.draughts.models;

public class EmptyOriginChecker implements LegalMovementChecker {

    private Board board;

    public EmptyOriginChecker(Board board) {
        this.board = board;
    }

    @Override
    public Error check(Move move) {
        if (board.isEmpty(move.getOrigin()))
            return Error.EMPTY_ORIGIN;
        else
            return null;
    }
}
