package usantatecla.draughts.views;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import usantatecla.draughts.controllers.ResumeController;
import usantatecla.draughts.utils.YesNoDialog;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class ResumeViewTest {

	@InjectMocks
	ResumeView resumeView = new ResumeView();

	@Mock
	ResumeController resumeController;

	@Mock
	YesNoDialog yesNoDialog;

	@Before
	public void before() {
		initMocks(this);
	}

	@Test
	public void testGivenResumeQuestionOnYesThenResetState() {
		when(this.yesNoDialog.read(anyString())).thenReturn(true);
		this.resumeView.interact(resumeController);
		verify(resumeController, times(1)).reset();
	}
	
	@Test
	public void testGivenResumeQuestionOnNoThenStateFinal() {
		when(this.yesNoDialog.read(anyString())).thenReturn(false);
		this.resumeView.interact(resumeController);
		verify(resumeController, times(1)).next();
	}
}
