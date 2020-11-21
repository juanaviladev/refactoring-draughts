package usantatecla.draughts.models;

public interface LegalMovementChecker {
    Error check(Board board, Turn turn, int pair, Coordinate... coordinates);
}
