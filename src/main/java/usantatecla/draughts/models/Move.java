package usantatecla.draughts.models;

public class Move {
    private final Coordinate origin;
    private final Coordinate target;

    public Move(Coordinate origin, Coordinate target) {
        assert origin != null;
        assert target != null;
        this.origin = origin;
        this.target = target;
    }

    public Coordinate getOrigin() {
        return origin;
    }

    public Coordinate getTarget() {
        return target;
    }
}
