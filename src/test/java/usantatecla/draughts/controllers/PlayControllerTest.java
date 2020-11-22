package usantatecla.draughts.controllers;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import usantatecla.draughts.models.*;
import usantatecla.draughts.views.View;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class PlayControllerTest {

    @InjectMocks
    private PlayController controller;

    @Mock
    private Game game;

    @Mock
    private State state;

    @Spy
    private View view;

    private final int DIMENSION = 5;
    private final Color black = Color.BLACK;
    private final Color white = Color.WHITE;

    private static final String CANCEL_FORMAT = "-1";
    private static final String LOST_MESSAGE = "Derrota!!! No puedes mover tus fichas!!!";

    private ByteArrayOutputStream outContent;

    @Before
    public void setUp() {
        initMocks(this);
        this.controller = spy(controller);
        this.outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        doReturn(Color.WHITE).when(this.controller).getColor();
        when(this.controller.getView()).thenReturn(this.view);
        when(this.game.getDimension()).thenReturn(DIMENSION);
        doReturn("12.23").when(this.view).read(any());
    }

    @Test
    public void testWhenIntroducingCancelFormatThenCancelInvoked() {
        doReturn(View.CANCEL_FORMAT).when(this.view).read(any());
        when(view.read(Color.WHITE)).thenReturn(View.CANCEL_FORMAT);
        this.controller.control();
        verify(this.controller, times(1)).cancel();
    }

    @Test
    public void testWhenIntroducingWrongFormatCoordinateThenAskAgain() {
        doReturn("xxx", "12.23").when(this.view).read(any());
        this.controller.control();
        verify(this.view, times(2)).read(any());
    }

    @Test
    public void testWhenIntroducingValidCoordinateAndBlockedThenLoose() {
        doReturn(true).when(this.game).isBlocked();
        this.controller.control();
        verify(this.view, times(1)).writeLost();
    }

    @Test
    public void testWriteNumbersOfColumnsAtTop() {
        this.controller.control();
        assertEquals(" 12345", this.topAndBottomNumberLines().get(0));
    }

    @Test
    public void testWriteNumbersOfColumnsAtBottom() {
        this.controller.control();
        assertEquals(" 12345", this.topAndBottomNumberLines().get(1));
    }

    @Test
    public void testWriteFirstRowNumberAtTheBeginning() {
        this.controller.control();
        assertEquals("1", firstCharacterOfLine(boardLine(1)));
    }

    @Test
    public void testWriteLastRowNumberAtTheBeginning() {
        this.controller.control();
        assertEquals("5", firstCharacterOfLine(boardLine(DIMENSION)));
    }

    @Test
    public void testWriteFirstRowNumberAtLastOfLine() {
        this.controller.control();
        assertEquals("1", lastCharacterOfLine(bodyLines().get(0)));
    }

    @Test
    public void testWriteLastRowNumberAtLastOfLine() {
        this.controller.control();
        assertEquals("5", lastCharacterOfLine(boardLine(DIMENSION)));
    }

    @Test
    public void testWriteBlackPieces() {
        when(this.controller.getPiece(coordinate(0,0))).thenReturn(pawn(black));
        when(this.controller.getPiece(coordinate(0,2))).thenReturn(pawn(black));
        when(this.controller.getPiece(coordinate(0,4))).thenReturn(pawn(black));

        this.controller.control();
        assertEquals("1n n n1",boardLine(1));
    }

    @Test
    public void testWriteWhitePieces() {
        when(this.controller.getPiece(coordinate(3,1))).thenReturn(pawn(white));
        when(this.controller.getPiece(coordinate(3,3))).thenReturn(pawn(white));

        this.controller.control();

        assertEquals("4 b b 4",boardLine(4));
    }

    @Test
    public void testWriteNullPieceWriteSpace() {
        when(this.controller.getPiece(coordinate(0,0))).thenReturn(pawn(black));
        when(this.controller.getPiece(coordinate(0,2))).thenReturn(pawn(white));
        when(this.controller.getPiece(coordinate(0,5))).thenReturn(null);

        this.controller.control();

        assertEquals("1n b  1",boardLine(1));
    }

    @Test
    public void testWriteDraught() {
        when(this.controller.getPiece(coordinate(2,0))).thenReturn(draught(black));
        when(this.controller.getPiece(coordinate(2,4))).thenReturn(draught(white));

        this.controller.control();

        assertEquals("3N   B3",boardLine(3));
    }

    private List<String> bodyLines() {
        String output = this.outContent.toString();
        List<String> lines = new ArrayList<>(Arrays.asList(output.split(System.lineSeparator())));
        lines.remove(0);
        lines.remove(lines.size() - 1);
        return lines;
    }

    private String boardLine(int position) {
        return bodyLines().get(position-1);
    }

    private String lastCharacterOfLine(String line) {
        List<String> characters = Arrays.asList(line.split(""));
        return characters.get(characters.size() - 1);
    }

    private String firstCharacterOfLine(String line) {
        List<String> characters = Arrays.asList(line.split(""));
        return characters.get(0);
    }

    private List<String> topAndBottomNumberLines() {
        String output = this.outContent.toString();
        String[] lines = output.split(System.lineSeparator());
        List<String> numberLines = new ArrayList<>();
        numberLines.add(lines[0]);
        numberLines.add(lines[lines.length-1]);
        return numberLines;
    }

    private Pawn pawn(Color color) {
        return new Pawn(color);
    }

    private Draught draught(Color color) {
        return new Draught(color);
    }

    private Coordinate coordinate(int row, int column) {
        return new Coordinate(row, column);
    }

}
