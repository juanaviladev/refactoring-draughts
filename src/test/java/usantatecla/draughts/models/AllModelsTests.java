package usantatecla.draughts.models;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
        GameMovementTest.class,
        GamePiecesTest.class,
        CoordinateParametrizedTest.class,
        CoordinateAssertionErrorTest.class,
        TurnParameterizedTest.class,
        PieceParameterizedTest.class,
        StateTest.class,
        ColorTest.class,
        DirectionParametrizedTest.class
})
public class AllModelsTests {
}
