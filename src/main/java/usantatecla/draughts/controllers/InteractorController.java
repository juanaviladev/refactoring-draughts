package usantatecla.draughts.controllers;

import usantatecla.draughts.models.Coordinate;
import usantatecla.draughts.models.Game;
import usantatecla.draughts.models.Piece;
import usantatecla.draughts.models.State;
import usantatecla.draughts.views.View;

public abstract class InteractorController extends Controller {

	protected InteractorController(Game game, State state) {
		super(game, state);
	}

	public Piece getPiece(Coordinate coordinate) {
		return this.game.getPiece(coordinate);
	}

	public abstract void control();

	void write() {
		this.getView().writeNumbersLine(this.getDimension());
		for (int i = 0; i < this.getDimension(); i++)
			this.writePiecesRow(i);
		this.getView().writeNumbersLine(this.getDimension());
	}

	protected View getView() {
		return new View();
	}

	void writePiecesRow(final int row) {
		this.getView().write((row + 1) + "");
		for (int j = 0; j < this.getDimension(); j++) {
			Piece piece = this.getPiece(new Coordinate(row, j));
			if (piece == null)
				this.getView().write(" ");
			else
				this.getView().write(piece.getCode());
		}
		this.getView().writeln((row + 1) + "");
	}

}
