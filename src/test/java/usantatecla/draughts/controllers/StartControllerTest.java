package usantatecla.draughts.controllers;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import usantatecla.draughts.models.Game;
import usantatecla.draughts.models.State;
import usantatecla.draughts.views.View;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class StartControllerTest {

    @InjectMocks
    private StartController controller;

    @Mock
    private Game game;

    @Mock
    private State state;

    @Mock
    private View view;

    @Before
    public void setUp() {
        initMocks(this);
        this.controller = spy(controller);
    }

    @Test
    public void testControlShouldPrintTitle() {
        when(this.controller.getView()).thenReturn(view);
        this.controller.control();
        verify(this.view).writeln("Draughts");
    }
}
