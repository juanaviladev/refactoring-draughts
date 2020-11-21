package usantatecla.draughts.models;

public class IsPlayerPieceChecker implements LegalMovementChecker {

    private Board board;
    private Turn turn;
    private LegalMovementChecker next;

    public IsPlayerPieceChecker(Board board, Turn turn, LegalMovementChecker next) {
        this.board = board;
        this.turn = turn;
        this.next = next;
    }

    public Error check(Move.Pair move) {
        if (turn.getOppositeColor() == board.getColor(move.getOrigin()))
            return Error.OPPOSITE_PIECE;
        else if(next != null)
            return next.check(move);
        else
            return null;
    }

}
