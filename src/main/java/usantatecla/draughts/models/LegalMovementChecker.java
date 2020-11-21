package usantatecla.draughts.models;

public abstract class LegalMovementChecker {

    private LegalMovementChecker next;

    LegalMovementChecker(LegalMovementChecker next) {
        this.next = next;
    }

    abstract Error check(Move.Pair move);

    protected Error next(Move.Pair move) {
        if(next == null) return null;

        return next.check(move);
    }
}
