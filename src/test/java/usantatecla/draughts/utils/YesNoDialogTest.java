package usantatecla.draughts.utils;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class YesNoDialogTest {

    @InjectMocks
    private YesNoDialog yesNoDialog;

    @Mock
    Console console;

    private final char AFFIRMATIVE = 'y';
    private final char NEGATIVE = 'n';
    private final String ERROR = "The value must be '" + AFFIRMATIVE + "' or '" + NEGATIVE + "'";

    @Before
    public void before() {
        initMocks(this);
    }

    @Test(expected = AssertionError.class)
    public void testReadWhenTitleIsNull() {
        this.yesNoDialog.read(null);
    }

    @Test
    public void testAnswerYes() {
        when(this.console.readChar(any())).thenReturn(this.AFFIRMATIVE);
        assertTrue(this.yesNoDialog.read("title"));
    }

    @Test
    public void testAnswerNo() {
        when(this.console.readChar(any())).thenReturn(this.NEGATIVE);
        assertFalse(this.yesNoDialog.read("title"));
    }

    @Test
    public void testWrongAnswer() {
        when(this.console.readChar(any())).thenReturn('x', this.NEGATIVE);
        this.yesNoDialog.read("title");
        verify(console, times(1)).writeln(ERROR);
    }
}