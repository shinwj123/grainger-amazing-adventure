package student.adventure;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.After;

import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;


public class AdventureTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;
    private GameEngine engine = new GameEngine();

    public AdventureTest() throws IOException {
    }

    @Before
    public void setUp() {
        System.setOut(new PrintStream(outContent));
    }

    //invalid entry testing
    @Test
    public void testInvalidCommand() throws IOException {
        engine.inputCommands("dance like jeff");
        assertEquals("I don't understand dance like jeff\n", outContent.toString());
    }

    //invalid entry testing -Non Alphabet
    @Test
    public void testInvalidCommandNonAlphabet() throws IOException {
            engine.inputCommands("$$$$$");
            assertEquals("I don't understand $$$$\n", outContent.toString());
    }

    //Display testing
    @Test
    public void testDisplayStartingRoomDescription() throws IOException {
        assertEquals("You are on the Engineering Quad, outside the Grainger Library",
                engine.gameInterface.displayRoomInformation());
    }

    //Room Option Display testing
    @Test
    public void testDisplayStartingRoomOptions() throws IOException {
        assertEquals("From here, you can go: North", engine.gameInterface.displayRoomOptionList());
    }

    //Items Option Display testing
    @Test
    public void testDisplayStartingRoomItems() throws IOException {
        assertEquals("Items visible: Squirrel", engine.gameInterface.displayVisibleItems());
    }

    //Examine test
    @Test
    public void testExamineCommand() throws IOException {
        engine.inputCommands("Examine");
        assertEquals("You are on the Engineering Quad, outside the Grainger Library\n" +
                "From here, you can go: North.\n" +
                "Items visible: Squirrel." + "\n",outContent.toString());
    }

    @Test
    public void testExamineCommandMixedCase() throws IOException {
        engine.inputCommands("exAmiNe");
        assertEquals("You are on the Engineering Quad, outside the Grainger Library\n" +
                "From here, you can go: North.\n" +
                "Items visible: Squirrel." + "\n",outContent.toString());
    }

    @Test
    public void testExamineCommandWhitespace() throws IOException {
        engine.inputCommands("examine   ");
        assertEquals("You are on the Engineering Quad, outside the Grainger Library\n" +
                "From here, you can go: North.\n" +
                "Items visible: Squirrel." + "\n",outContent.toString());
    }

    //Game Manual Quit testing
    @Test
    public void testQuitGameWithCommandQuit() throws IOException {
        assertEquals(true, engine.manualQuitGame("quit"));
    }

    @Test
    public void testQuitGameWithCommandExit() throws IOException {
        assertEquals(true, engine.manualQuitGame("exit"));
    }

    @Test
    public void testQuitGameCommandUpperCase() throws IOException {
        assertEquals(true, engine.manualQuitGame("QUIT"));
    }

    @Test
    public void testQuitGameCommandWhiteSpace() throws IOException {
        assertEquals(true, engine.manualQuitGame("quit   "));
    }

    @Test
    public void testQuitGameCommandSpecialCharacter() throws IOException {
        assertEquals(true, engine.manualQuitGame("exit %%#$#^@*@"));
    }

    @Test
    public void testQuitGameCommandInvalid() throws IOException {
        assertEquals(false, engine.manualQuitGame("stop"));
    }

    @Test
    public void testQuitGameCommandInvalidShort() throws IOException {
        assertEquals(false, engine.manualQuitGame("q"));
    }

    @Test
    public void

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
    }
}