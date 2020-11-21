package usantatecla.draughts.models;

public interface LegalMovementChecker {
    Error check(int pair, Coordinate... coordinates);
}
