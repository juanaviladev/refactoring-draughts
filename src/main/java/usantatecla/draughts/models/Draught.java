package usantatecla.draughts.models;

public class Draught extends Piece {

  public Draught(Color color) {
    super(color);
  }

  @Override
  Error isCorrectDiagonalMovement(int amountBetweenDiagonalPieces, Move move) {
    if (amountBetweenDiagonalPieces > 1)
      return Error.TOO_MUCH_EATINGS;
    return null;
  }

  @Override
  public String getCode(){
		return super.getCode().toUpperCase();
  }

	@Override
	public Piece copy() {
		return new Draught(this.getColor());
	}

}