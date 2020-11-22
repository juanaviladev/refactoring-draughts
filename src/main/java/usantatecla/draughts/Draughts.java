package usantatecla.draughts;

import usantatecla.draughts.controllers.*;
import usantatecla.draughts.models.Game;
import usantatecla.draughts.models.State;
import usantatecla.draughts.models.StateValue;
import usantatecla.draughts.views.View;

import java.util.HashMap;
import java.util.Map;

class Draughts {

    private State state;
    private Map<StateValue, InteractorController> controllers;

    private Draughts(){
        Game game = new Game();
        this.state = new State();
        this.controllers = new HashMap<>();

        this.controllers.put(StateValue.INITIAL, new StartController(game, this.state));
        this.controllers.put(StateValue.IN_GAME, new PlayController(game, this.state));
        this.controllers.put(StateValue.FINAL, new ResumeController(game, this.state));
        this.controllers.put(StateValue.EXIT, null);
    }

    public InteractorController getController() {
        return this.controllers.get(this.state.getValueState());
    }

    private void play() {
        InteractorController controller;
		do {
			controller = this.getController();
			if (controller != null)
				controller.control();
		} while (controller != null); 
    }

    public static void main(String[] args){
        new Draughts().play();
    }
    
}