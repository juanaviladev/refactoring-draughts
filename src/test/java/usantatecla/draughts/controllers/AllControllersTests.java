package usantatecla.draughts.controllers;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
        StartControllerTest.class,
        ResumeControllerTest.class,
        PlayControllerTest.class,
})
public class AllControllersTests {
}
