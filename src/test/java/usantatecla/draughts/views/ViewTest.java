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
import usantatecla.draughts.utils.Console;
import usantatecla.draughts.utils.YesNoDialog;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.mockito.Mockito.*;

public class ViewTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Mock
    private StartController startController;

    @Spy
    private Console console;

    @Mock
    private PlayView playView;

    @Mock
    YesNoDialog yesNoDialog;

    @Mock
    ResumeController resumeController;

    @Spy
    @InjectMocks
    private final View view = new View();

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        System.setOut(new PrintStream(outContent));
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
        this.view.visit(playController);
        verify(this.playView,times(1)).interact(playController);
        verifyNoMoreInteractions(playController);
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
        Assert.assertTrue(outContent.toString().contains("Draughts"));
    }
}
