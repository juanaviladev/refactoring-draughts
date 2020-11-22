package usantatecla.draughts.controllers;

import usantatecla.draughts.models.Error;
import usantatecla.draughts.models.*;
import usantatecla.draughts.views.View;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class PlayController extends InteractorController {

    private CancelController cancelController;
    private MoveController moveController;
    private String input;

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
            this.input = this.getView().read(this.getColor());
            if (this.isCanceledFormat(this.input))
                this.cancel();
            else if (!this.isMoveFormat(this.input)) {
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
        assert this.isMoveFormat(this.input);
        List<Coordinate> coordinateList = new ArrayList<Coordinate>();
        while (input.length() > 0) {
            coordinateList.add(Coordinate.getInstance(input.substring(0, 2)));
            input = input.substring(2, input.length());
            if (input.length() > 0 && input.charAt(0) == '.')
                input = input.substring(1, input.length());
        }
        Coordinate[] coordinates = new Coordinate[coordinateList.size()];
        for (int i = 0; i < coordinates.length; i++) {
            coordinates[i] = coordinateList.get(i);
        }
        return coordinates;
    }

    public boolean isCanceledFormat(String text) {
        return text.equals(View.CANCEL_FORMAT);
    }

    public boolean isMoveFormat(String text) {
        return Pattern.compile(View.MOVEMENT_FORMAT).matcher(text).find();
    }

}