package usantatecla.draughts.controllers;

import usantatecla.draughts.models.Game;
import usantatecla.draughts.models.State;
import usantatecla.draughts.views.View;

public class StartController extends InteractorController {

	public StartController(Game game, State state) {
        super(game, state);
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
		this.getView().writeln(View.TITTLE);
		this.write();
		this.start();
	}
}
