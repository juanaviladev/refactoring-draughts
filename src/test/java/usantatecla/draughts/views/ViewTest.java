package usantatecla.draughts.views;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import usantatecla.draughts.controllers.InteractorController;
import usantatecla.draughts.controllers.PlayController;
import usantatecla.draughts.controllers.ResumeController;
import usantatecla.draughts.controllers.StartController;
import usantatecla.draughts.models.Color;
import usantatecla.draughts.utils.Console;
import usantatecla.draughts.utils.YesNoDialog;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

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

    @Spy
    @InjectMocks
    private View view;

    private static final String CANCEL_FORMAT = "-1";
    private static final String LOST_MESSAGE = "Derrota!!! No puedes mover tus fichas!!!";

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        doReturn(Color.WHITE).when(playController).getColor();
    }

    @Test
    public void testVisitStartViewVerifyInteractOnce() {
        StartController startController = mock(StartController.class);
        this.view.visit(startController);
        verify(this.view,times(1)).interact(startController);
    }

    @Test
    public void testVisitPlayViewVerifyInteractOnce() {
        PlayController playController = mock(PlayController.class);
        doReturn(Color.WHITE).when(playController).getColor();
        doReturn("12.23").when(console).readString("Mueven las blancas: ");
        this.view.visit(playController);
        verify(this.view,times(1)).interact(playController);
    }

    @Test
    public void testVisitResumeViewVerifyInteractOnce() {
        ResumeController resumeController = mock(ResumeController.class);
        this.view.visit(resumeController);
        verify(this.view,times(1)).interact(resumeController);
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
        verify(startController, times(1)).start();
        verify(startController, atMost(1)).start();
    }

    @Test
    public void testInteractConsoleShouldWriteTitle() {
        this.view.interact(this.startController);
        verify(this.console, times(1)).writeln(anyString());
    }
    @Test
    public void testInteractConsoleShouldPrintTitle() {
        this.view.interact(this.startController);
        verify(this.console).writeln("Draughts");
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
}
