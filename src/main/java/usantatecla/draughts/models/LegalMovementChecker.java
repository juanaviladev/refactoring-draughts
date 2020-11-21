package usantatecla.draughts.models;

public interface LegalMovementChecker {
    Error check(Move.Pair move);
}
