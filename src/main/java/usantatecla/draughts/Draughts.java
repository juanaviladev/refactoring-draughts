package usantatecla.draughts;

import usantatecla.draughts.controllers.*;
import usantatecla.draughts.models.Game;
import usantatecla.draughts.models.State;
import usantatecla.draughts.models.StateValue;
import usantatecla.draughts.views.View;

import java.util.HashMap;
import java.util.Map;

class Draughts {
    
    private Logic logic;
    private Game game;
    private State state;
    private Map<StateValue, InteractorController> controllers;

    private Draughts(){
        this.logic = new Logic();
        this.game = new Game();
        this.state = new State();
        this.controllers = new HashMap<StateValue, InteractorController>();
        this.controllers.put(StateValue.INITIAL, new StartController(this.game, this.state));
        this.controllers.put(StateValue.IN_GAME, new PlayController(this.game, this.state));
        this.controllers.put(StateValue.FINAL, new ResumeController(this.game, this.state));
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