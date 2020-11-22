package usantatecla.draughts.controllers;

import usantatecla.draughts.models.Coordinate;
import usantatecla.draughts.models.Game;
import usantatecla.draughts.models.Piece;
import usantatecla.draughts.models.State;
import usantatecla.draughts.views.View;

public class StartController extends InteractorController {

	private View view;

	public StartController(Game game, State state) {
        super(game, state);
        this.view = new View();
	}

	public void start() {
        this.state.next();
	}
    
    @Override
	public void accept(InteractorControllersVisitor controllersVisitor) {
		assert controllersVisitor != null;
		controllersVisitor.visit(this);
    }

    public void control() {
		this.view.writeln(View.TITTLE);
		this.write();
		this.start();
	}

	void write() {
		this.view.writeNumbersLine(this.getDimension());
		for (int i = 0; i < this.getDimension(); i++)
			this.writePiecesRow(i);
		this.view.writeNumbersLine(this.getDimension());
	}

	void writePiecesRow(final int row) {
		this.view.write((row + 1) + "");
		for (int j = 0; j < this.getDimension(); j++) {
			Piece piece = this.getPiece(new Coordinate(row, j));
			if (piece == null)
				this.view.write(" ");
			else
				this.view.write(piece.getCode());
		}
		this.view.writeln((row + 1) + "");
	}
}
