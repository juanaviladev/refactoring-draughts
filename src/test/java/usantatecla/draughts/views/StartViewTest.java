package usantatecla.draughts.views;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import usantatecla.draughts.controllers.StartController;
import usantatecla.draughts.utils.Console;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.mockito.Mockito.*;

public class StartViewTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Mock
    private StartController startController;

    @Spy
    private Console console;

    @InjectMocks
    private final StartView startView = new StartView();

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        System.setOut(new PrintStream(outContent));
    }

    @Test(expected = AssertionError.class)
    public void testInteractNullControllerShouldThrowError() {
        startView.interact(null);
    }

    @Test
    public void testInteractShouldBeCalledOnce() {
        this.startView.interact(this.startController);
        verify(startController, times(1)).start();
        verify(startController, atMost(1)).start();
    }

    @Test
    public void testInteractConsoleShouldWriteTitle() {
        this.startView.interact(this.startController);
        verify(this.console, times(1)).writeln(anyString());
    }

    @Test
    public void testInteractConsoleShouldPrintTitle() {
        startView.interact(this.startController);
        Assert.assertTrue(outContent.toString().contains("Draughts"));
    }
}