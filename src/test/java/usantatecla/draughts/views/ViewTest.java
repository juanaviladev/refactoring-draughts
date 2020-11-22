package usantatecla.draughts.views;

import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import usantatecla.draughts.controllers.InteractorController;
import usantatecla.draughts.controllers.PlayController;
import usantatecla.draughts.controllers.ResumeController;
import usantatecla.draughts.controllers.StartController;
import usantatecla.draughts.models.Color;
import usantatecla.draughts.models.Coordinate;
import usantatecla.draughts.models.Draught;
import usantatecla.draughts.models.Pawn;
import usantatecla.draughts.utils.Console;
import usantatecla.draughts.utils.YesNoDialog;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class ViewTest {

    @Mock
    private StartController startController;

    @Mock
    private Console console;

    @Mock
    YesNoDialog yesNoDialog;

    @Mock
    ResumeController resumeController;

    @Mock
    PlayController playController;

    @Mock
    InteractorController interactorController;

    private ByteArrayOutputStream outContent;

    @Spy
    @InjectMocks
    private View view;

    private final int DIMENSION = 5;
    private final Color black = Color.BLACK;
    private final Color white = Color.WHITE;

    private static final String CANCEL_FORMAT = "-1";
    private static final String LOST_MESSAGE = "Derrota!!! No puedes mover tus fichas!!!";

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        System.setOut(System.out);
        this.outContent = new ByteArrayOutputStream();
        doReturn(Color.WHITE).when(playController).getColor();
    }

    @Test
    public void testVisitStartViewVerifyInteractOnce() {
        StartController startController = mock(StartController.class);
        this.view.visit(startController);
        verify(startController,times(1)).control();
    }

    @Test
    public void testVisitPlayViewVerifyInteractOnce() {
        this.view.visit(playController);
        verify(this.playController,times(1)).control();
    }

    @Test
    public void testInteractControllerVerifyAcceptOnce() {
        InteractorController interactorController = mock(InteractorController.class);
        this.view.interact(interactorController);
        verify(interactorController, times(1)).accept(this.view);
        verifyNoMoreInteractions(interactorController);
    }

    @Test(expected = AssertionError.class)
    public void testVisitWithNullStartControllerShouldThrowAssertionError() {
        this.view.visit((StartController) null);
    }

    @Test(expected = AssertionError.class)
    public void testVisitWithNullPlayControllerShouldThrowAssertionError() {
        this.view.visit((PlayController) null);
    }

    @Test(expected = AssertionError.class)
    public void testVisitWithNullResumeControllerShouldThrowAssertionError() {
        this.view.visit((ResumeController) null);
    }

    @Test(expected = AssertionError.class)
    public void testInteractWithNullControllerShouldThrowAssertionError() {
        this.view.interact((InteractorController)null);
    }

    @Test
    public void testGivenResumeQuestionOnYesThenResetState() {
        when(this.yesNoDialog.read(anyString())).thenReturn(true);
        this.view.interact(resumeController);
        verify(resumeController, times(1)).reset();
    }

    @Test
    public void testGivenResumeQuestionOnNoThenStateFinal() {
        when(this.yesNoDialog.read(anyString())).thenReturn(false);
        this.view.interact(resumeController);
        verify(resumeController, times(1)).next();
    }

    @Test(expected = AssertionError.class)
    public void testInteractNullControllerShouldThrowError() {
        view.interact((StartController)null);
    }

    @Test
    public void testInteractShouldBeCalledOnce() {
        this.view.interact(this.startController);
        verify(startController, times(1)).accept(this.view);
        verify(startController, atMost(1)).accept(this.view);
    }

    @Test
    public void testWhenIntroducingCancelFormatThenCancelInvoked() {
        //TODO: Preguntar por qué con el @Spy de console no funciona el when().thenReturn()
        doReturn(CANCEL_FORMAT).when(console).readString(any());
        //when(console.readString(anyString())).thenReturn(CANCEL_FORMAT);
        this.view.interact(playController);
        verify(playController, times(1)).cancel();
    }

    @Test
    public void testWhenIntroducingWrongFormatCoordinateThenAskAgain() {
        doReturn("xxx", "12.23").when(console).readString(any());
        this.view.interact(playController);
        verify(console, times(2)).readString(any());
    }

    @Test
    public void testWhenIntroducingValidCoordinateAndBlockedThenLoose() {
        doReturn("12.23").when(console).readString(any());
        doReturn(true).when(playController).isBlocked();
        this.view.interact(playController);
        verify(console, times(1)).writeln(LOST_MESSAGE);
    }

    @Test
    public void testWriteNumbersOfColumnsAtTop() {
        System.setOut(new PrintStream(outContent));
        when(this.interactorController.getDimension()).thenReturn(DIMENSION);
        this.view.write(interactorController);
        assertEquals(" 12345", this.topAndBottomNumberLines().get(0));
    }

    @Test
    public void testWriteNumbersOfColumnsAtBottom() {
        System.setOut(new PrintStream(outContent));
        when(this.interactorController.getDimension()).thenReturn(DIMENSION);
        this.view.write(interactorController);
        assertEquals(" 12345", this.topAndBottomNumberLines().get(1));
    }

    @Test
    public void testWriteFirstRowNumberAtTheBeginning() {
        System.setOut(new PrintStream(outContent));
        when(this.interactorController.getDimension()).thenReturn(DIMENSION);
        this.view.write(interactorController);
        assertEquals("1", firstCharacterOfLine(boardLine(1)));
    }

    @Test
    public void testWriteLastRowNumberAtTheBeginning() {
        System.setOut(new PrintStream(outContent));
        when(this.interactorController.getDimension()).thenReturn(DIMENSION);
        this.view.write(interactorController);
        assertEquals("5", firstCharacterOfLine(boardLine(DIMENSION)));

    }

    @Test
    public void testWriteFirstRowNumberAtLastOfLine() {
        System.setOut(new PrintStream(outContent));
        when(this.interactorController.getDimension()).thenReturn(DIMENSION);
        this.view.write(interactorController);
        assertEquals("1", lastCharacterOfLine(bodyLines().get(0)));
    }

    @Test
    public void testWriteLastRowNumberAtLastOfLine() {
        System.setOut(new PrintStream(outContent));
        when(this.interactorController.getDimension()).thenReturn(DIMENSION);
        this.view.write(interactorController);
        assertEquals("5", lastCharacterOfLine(boardLine(DIMENSION)));
    }

    @Test
    public void testWriteBlackPieces() {
        System.setOut(new PrintStream(outContent));
        when(this.interactorController.getDimension()).thenReturn(DIMENSION);
        when(this.interactorController.getPiece(coordinate(0,0))).thenReturn(pawn(black));
        when(this.interactorController.getPiece(coordinate(0,2))).thenReturn(pawn(black));
        when(this.interactorController.getPiece(coordinate(0,4))).thenReturn(pawn(black));

        this.view.write(interactorController);
        assertEquals("1n n n1",boardLine(1));
    }

    @Test
    public void testWriteWhitePieces() {
        when(this.interactorController.getDimension()).thenReturn(DIMENSION);
        when(this.interactorController.getPiece(coordinate(3,1))).thenReturn(pawn(white));
        when(this.interactorController.getPiece(coordinate(3,3))).thenReturn(pawn(white));

        this.view.write(interactorController);

        assertEquals("4 b b 4",boardLine(4));
    }

    @Test
    public void testWriteNullPieceWriteSpace() {
        when(this.interactorController.getDimension()).thenReturn(DIMENSION);
        when(this.interactorController.getPiece(coordinate(0,0))).thenReturn(pawn(black));
        when(this.interactorController.getPiece(coordinate(0,2))).thenReturn(pawn(white));
        when(this.interactorController.getPiece(coordinate(0,5))).thenReturn(null);

        this.view.write(interactorController);

        assertEquals("1n b  1",boardLine(1));
    }

    @Test
    public void testWriteDraught() {
        when(this.interactorController.getDimension()).thenReturn(DIMENSION);
        when(this.interactorController.getPiece(coordinate(2,0))).thenReturn(draught(black));
        when(this.interactorController.getPiece(coordinate(2,4))).thenReturn(draught(white));

        this.view.write(interactorController);

        assertEquals("3N   B3",boardLine(3));
    }

    private List<String> bodyLines() {
        String output = this.outContent.toString();
        List<String> lines = new ArrayList<>(Arrays.asList(output.split("\r\n")));
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
        String[] lines = output.split("\r\n");
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
