package usantatecla.draughts.models;

public class NotEmptyTargetChecker {

    public Error check(Board board, Turn turn, int pair, Coordinate... coordinates) {
        if (!board.isEmpty(coordinates[pair + 1]))
            return Error.NOT_EMPTY_TARGET;
        else
            return null;
    }

}
