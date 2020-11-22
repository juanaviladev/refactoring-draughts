package usantatecla.draughts.controllers;

import usantatecla.draughts.models.Game;
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
			this.view.writePiecesRow(i, this);
		this.view.writeNumbersLine(this.getDimension());
	}
}
