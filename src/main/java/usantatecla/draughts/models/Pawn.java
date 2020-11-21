package usantatecla.draughts.models;

public class Pawn extends Piece {

    private static char[] CHARACTERS = {'b', 'n'};
    private static final int MAX_DISTANCE = 2;

    public Pawn(Color color) {
        super(color);
    }

    @Override
    Error isCorrectDiagonalMovement(int amountBetweenDiagonalPieces, Move.Pair move) {
		if (!this.isAdvanced(move.getOrigin(), move.getTarget()))
			return Error.NOT_ADVANCED;
		int distance = move.getOrigin().getDiagonalDistance(move.getTarget());
		if (distance > Pawn.MAX_DISTANCE) 
			return Error.TOO_MUCH_ADVANCED;
		if (distance == Pawn.MAX_DISTANCE && amountBetweenDiagonalPieces != 1) 
			return Error.WITHOUT_EATING;
		return null;
    }
    
    protected char[] getCodes() {
		return Pawn.CHARACTERS;
	}

	@Override
	public Piece copy() {
		return new Pawn(this.getColor());
	}
    
}