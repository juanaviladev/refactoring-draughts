package usantatecla.draughts.views;

import usantatecla.draughts.models.Color;
import usantatecla.draughts.utils.Console;
import usantatecla.draughts.utils.YesNoDialog;

import java.util.regex.Pattern;

public class View {

    public static final String MESSAGE = "¿Queréis jugar otra";
    public static final String TITTLE = "Draughts";
    private static final String COLOR_PARAM = "#color";
    private static final String[] COLOR_VALUES = { "blancas", "negras" };
    private static final String PROMPT = "Mueven las " + COLOR_PARAM + ": ";
    private static final String CANCEL_FORMAT = "-1";
    private static final String MOVEMENT_FORMAT = "[1-8]{2}(\\.[1-8]{2}){1,2}";
    private static final String ERROR_MESSAGE = "Error!!! Formato incorrecto";
    private static final String LOST_MESSAGE = "Derrota!!! No puedes mover tus fichas!!!";

    private YesNoDialog yesNoDialog;
    private Console console;
    private String string;

    public View(){
        this.yesNoDialog = new YesNoDialog();
        this.console = new Console();
    }

    public String read(Color color) {
        final String titleColor = PROMPT.replace(COLOR_PARAM ,COLOR_VALUES[color.ordinal()]);
        return this.console.readString(titleColor);
    }

    public boolean isCanceledFormat() {
        return string.equals(CANCEL_FORMAT);
    }

    public boolean isMoveFormat() {
        return Pattern.compile(MOVEMENT_FORMAT).matcher(string).find();
    }

    public void writeError(){
        this.console.writeln(ERROR_MESSAGE);
    }

    public void writeLost() {
        this.console.writeln(LOST_MESSAGE);
    }

    public void write(String text) {
        console.write(text);
    }

    public void writeln(String text) {
        console.writeln(text);
    }

    public void writeNumbersLine(final int DIMENSION) {
        this.console.write(" ");
        for (int i = 0; i < DIMENSION; i++)
            this.console.write((i + 1) + "");
        this.console.writeln();
    }
}
