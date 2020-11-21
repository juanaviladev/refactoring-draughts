package usantatecla.draughts.models;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;

public class Move implements Iterable<Move.Pair> {

    private List<Pair> pairs;

    public Move(Coordinate... coordinates) {
        this.pairs = new ArrayList<>();
        for(int i = 0; i < coordinates.length - 1; i++) {
            pairs.add(new Pair(coordinates[i], coordinates[i + 1]));
        }
    }

    Pair get(int i) {
        return pairs.get(i);
    }

    @Override
    public Iterator<Pair> iterator() {
        return pairs.iterator();
    }

    @Override
    public Spliterator<Pair> spliterator() {
        return pairs.spliterator();
    }

    static class Pair {
        Coordinate origin;
        Coordinate target;

        public Pair(Coordinate origin, Coordinate target) {
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
}