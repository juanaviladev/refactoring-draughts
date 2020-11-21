package usantatecla.draughts.models;

public class IsPlayerPieceChecker implements LegalMovementChecker {

    private Board board;
    private Turn turn;

    public IsPlayerPieceChecker(Board board, Turn turn) {
        this.board = board;
        this.turn = turn;
    }

    public Error check(Move.Pair move) {
        if (turn.getOppositeColor() == board.getColor(move.getOrigin()))
            return Error.OPPOSITE_PIECE;
        else
            return null;
    }

}
