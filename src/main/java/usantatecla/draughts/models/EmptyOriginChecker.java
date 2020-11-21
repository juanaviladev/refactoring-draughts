package usantatecla.draughts.models;

public class EmptyOriginChecker {

    public Error check(Board board, int pair, Coordinate... coordinates) {
        if (board.isEmpty(coordinates[pair]))
            return Error.EMPTY_ORIGIN;
        else
            return null;
    }

}
