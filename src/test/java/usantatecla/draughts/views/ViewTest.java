package usantatecla.draughts.views;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import usantatecla.draughts.controllers.InteractorController;
import usantatecla.draughts.controllers.PlayController;
import usantatecla.draughts.controllers.ResumeController;
import usantatecla.draughts.controllers.StartController;

import static org.mockito.Mockito.*;

public class ViewTest {
    @Mock
    private StartView startView;

    @Mock
    private PlayView playView;

    @Mock
    private ResumeView resumeView;

    @InjectMocks
    private final View view = new View();

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testVisitStartViewVerifyInteractOnce() {
        StartController startController = mock(StartController.class);
        this.view.visit(startController);
        verify(this.startView,times(1)).interact(startController);
        verifyNoMoreInteractions(startController);
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
        verify(this.resumeView,times(1)).interact(resumeController);
        verifyNoMoreInteractions(resumeController);
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
        this.view.interact(null);
    }
}
