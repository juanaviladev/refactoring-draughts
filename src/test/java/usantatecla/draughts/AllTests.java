package usantatecla.draughts;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import usantatecla.draughts.controllers.AllControllersTests;
import usantatecla.draughts.models.AllModelsTests;
import usantatecla.draughts.utils.AllUtilsTests;
import usantatecla.draughts.views.AllViewTests;

@RunWith(Suite.class)
@SuiteClasses({
	AllControllersTests.class,
	AllModelsTests.class,
	AllUtilsTests.class,
	AllViewTests.class
})
public class AllTests {
}
