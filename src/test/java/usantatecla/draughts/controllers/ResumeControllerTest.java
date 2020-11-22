package usantatecla.draughts.controllers;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import usantatecla.draughts.models.Game;
import usantatecla.draughts.models.State;
import usantatecla.draughts.utils.YesNoDialog;
import usantatecla.draughts.views.View;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class ResumeControllerTest {

    @InjectMocks
    private ResumeController controller;

    @Mock
    private Game game;

    @Mock
    private YesNoDialog yesNoDialog;

    @Mock
    private State state;

    @Mock
    private View view;

    @Before
    public void setUp() {
        initMocks(this);
        this.controller = spy(controller);
        when(this.controller.getDialog()).thenReturn(yesNoDialog);
    }

    @Test
    public void testControlWhenDialogReturnsTrueThenReset() {
        when(yesNoDialog.read(anyString())).thenReturn(true);

        this.controller.control();

        verify(this.controller).reset();
    }

    @Test
    public void testControlWhenDialogReturnsFalseThenNext() {
        when(yesNoDialog.read(anyString())).thenReturn(false);

        this.controller.control();

        verify(this.controller).next();
    }
}
