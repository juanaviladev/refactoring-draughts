package usantatecla.draughts.models;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

public class Game {

    private Board board;
    private Turn turn;

    Game(Board board) {
        this.turn = new Turn();
        this.board = board;
    }

    public Game() {
        this(new Board());
        this.reset();
    }

    public void reset() {
        for (int i = 0; i < Coordinate.getDimension(); i++)
            for (int j = 0; j < Coordinate.getDimension(); j++) {
                Coordinate coordinate = new Coordinate(i, j);
                Color color = Color.getInitialColor(coordinate);
                Piece piece = null;
                if (!color.isNull())
                    piece = new Pawn(color);
                this.board.put(coordinate, piece);
            }
        if (this.turn.getColor() != Color.WHITE)
            this.turn.change();
    }

    public Error move(Coordinate... coordinates) {
        Error error = null;
        List<Coordinate> removedCoordinates = new ArrayList<Coordinate>();
        List<Piece> removedPieces = new ArrayList<Piece>();
        Move move = new Move(coordinates);
        Iterator<Move.Pair> pairs = move.iterator();
        Stack<Move.Pair> moved = new Stack<>();
        while (pairs.hasNext() && error == null) {
            Move.Pair pair = pairs.next();
            error = this.isCorrectPairMove(pair);
            if (error == null) {
                this.pairMove(removedCoordinates, removedPieces, pair);
                moved.push(pair);
            }
        }
        error = this.isCorrectGlobalMove(error, removedCoordinates, coordinates);
        if (error == null)
            this.turn.change();
        else
            this.unMoves(removedCoordinates, removedPieces, moved);
        return error;
    }

    private Error isCorrectPairMove(Move.Pair move) {
        LegalMovementChecker checker = getLegalMovementCheckers();
        Error result = checker.check(move);
        if (result != null) return result;
        List<Piece> betweenDiagonalPieces =
                this.board.getBetweenDiagonalPieces(move.getOrigin(), move.getTarget());
        return this.board.getPiece(move.getOrigin()).isCorrectMovement(betweenDiagonalPieces, move);
    }

    private LegalMovementChecker getLegalMovementCheckers() {
        return new EmptyOriginChecker(this.board, new IsPlayerPieceChecker(this.board, this.turn, new NotEmptyTargetChecker(this.board, null)));
    }

    private void pairMove(List<Coordinate> removedCoordinates, List<Piece> removedPieces, Move.Pair pair) {
        Coordinate forRemoving = this.getBetweenDiagonalPiece(pair);
        if (forRemoving != null) {
            Piece forRemovingPiece = this.getPiece(forRemoving).copy();
            removedCoordinates.add(0, forRemoving);
            removedPieces.add(0, forRemovingPiece);
            this.board.remove(forRemoving);
        }
        this.board.move(pair.getOrigin(), pair.getTarget());
        if (this.board.getPiece(pair.getTarget()).isLimit(pair.getTarget())) {
            Color color = this.board.getColor(pair.getTarget());
            this.board.remove(pair.getTarget());
            this.board.put(pair.getTarget(), new Draught(color));
        }
    }

    private Coordinate getBetweenDiagonalPiece(Move.Pair pair) {
        assert pair.getOrigin().isOnDiagonal(pair.getTarget());
        List<Coordinate> betweenCoordinates = pair.getOrigin().getBetweenDiagonalCoordinates(pair.getTarget());
        if (betweenCoordinates.isEmpty())
            return null;
        for (Coordinate coordinate : betweenCoordinates) {
            if (this.getPiece(coordinate) != null)
                return coordinate;
        }
        return null;
    }

    private Error isCorrectGlobalMove(Error error, List<Coordinate> removedCoordinates, Coordinate... coordinates) {
        if (error != null)
            return error;
        if (coordinates.length > 2 && coordinates.length > removedCoordinates.size() + 1)
            return Error.TOO_MUCH_JUMPS;
        return null;
    }

    private void unMoves(List<Coordinate> removedCoordinates, List<Piece> removedPieces, Stack<Move.Pair> pairs) {
        while (!pairs.empty()) {
            Move.Pair pair = pairs.pop();
            this.board.move(pair.getTarget(), pair.getOrigin());
        }
        for (int i = 0; i < removedCoordinates.size(); i++) {
            Coordinate removedCoordinate = removedCoordinates.get(i);
            Piece removedPiece = removedPieces.get(i);
            this.board.put(removedCoordinate, removedPiece);
        }
    }

    public boolean isBlocked() {
        for (Coordinate coordinate : this.getCoordinatesWithActualColor())
            if (!this.isBlocked(coordinate))
                return false;
        return true;
    }

    private List<Coordinate> getCoordinatesWithActualColor() {
        List<Coordinate> coordinates = new ArrayList<Coordinate>();
        for (int i = 0; i < this.getDimension(); i++) {
            for (int j = 0; j < this.getDimension(); j++) {
                Coordinate coordinate = new Coordinate(i, j);
                Piece piece = this.getPiece(coordinate);
                if (piece != null && piece.getColor() == this.getTurnColor())
                    coordinates.add(coordinate);
            }
        }
        return coordinates;
    }

    private boolean isBlocked(Coordinate coordinate) {
        for (int i = 1; i <= 2; i++)
            for (Coordinate target : coordinate.getDiagonalCoordinates(i))
                if (this.isCorrectPairMove(new Move.Pair(coordinate, target)) == null)
                    return false;
        return true;
    }

    public void cancel() {
        for (Coordinate coordinate : this.getCoordinatesWithActualColor())
            this.board.remove(coordinate);
        this.turn.change();
    }

    public Color getColor(Coordinate coordinate) {
        assert coordinate != null;
        return this.board.getColor(coordinate);
    }

    public Color getTurnColor() {
        return this.turn.getColor();
    }

    private Color getOppositeTurnColor() {
        return this.turn.getOppositeColor();
    }

    public Piece getPiece(Coordinate coordinate) {
        assert coordinate != null;
        return this.board.getPiece(coordinate);
    }

    public int getDimension() {
        return Coordinate.getDimension();
    }

    @Override
    public String toString() {
        return this.board + "\n" + this.turn;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((board == null) ? 0 : board.hashCode());
        result = prime * result + ((turn == null) ? 0 : turn.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Game other = (Game) obj;
        if (board == null) {
            if (other.board != null)
                return false;
        } else if (!board.equals(other.board))
            return false;
        if (turn == null) {
            if (other.turn != null)
                return false;
        } else if (!turn.equals(other.turn))
            return false;
        return true;
    }

}