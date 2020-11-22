package usantatecla.draughts.controllers;

import usantatecla.draughts.models.Error;
import usantatecla.draughts.models.*;
import usantatecla.draughts.views.View;

import java.util.ArrayList;
import java.util.List;

public class PlayController extends InteractorController {

    private CancelController cancelController;
    private MoveController moveController;
    private String string;

    public PlayController(Game game, State state) {
        super(game, state);
        this.cancelController = new CancelController(game, state);
        this.moveController = new MoveController(game, state);
    }

    public Error move(Coordinate... coordinates) {
        return this.moveController.move(coordinates);
    }

    public void cancel() {
        this.cancelController.cancel();
    }

    public Color getColor() {
        return this.game.getTurnColor();
    }

    public boolean isBlocked() {
        return this.game.isBlocked();
    }

    public void control() {
        Error error;
        do {
            error = null;
            this.string = this.getView().read(this.getColor());
            if (this.getView().isCanceledFormat())
                this.cancel();
            else if (!this.getView().isMoveFormat()) {
                error = Error.BAD_FORMAT;
                this.getView().writeError();
            } else {
                error = this.move(this.getCoordinates());
                this.write();
                if (error == null && this.isBlocked())
                    this.getView().writeLost();
            }
        } while (error != null);
    }

    private Coordinate[] getCoordinates() {
        assert this.getView().isMoveFormat();
        List<Coordinate> coordinateList = new ArrayList<Coordinate>();
        while (string.length() > 0) {
            coordinateList.add(Coordinate.getInstance(string.substring(0, 2)));
            string = string.substring(2, string.length());
            if (string.length() > 0 && string.charAt(0) == '.')
                string = string.substring(1, string.length());
        }
        Coordinate[] coordinates = new Coordinate[coordinateList.size()];
        for (int i = 0; i < coordinates.length; i++) {
            coordinates[i] = coordinateList.get(i);
        }
        return coordinates;
    }

}