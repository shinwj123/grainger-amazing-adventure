package student.adventure;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.After;
import org.junit.*;

import student.server.AdventureException;
import student.server.GraingerLibraryAdventureService;

import java.io.IOException;
import java.io.File;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;



public class AdventureTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private GameEngine engine = new GameEngine();

    public AdventureTest() throws IOException {
    }

    @Before
    public void setUp() {
        System.setOut(new PrintStream(outContent));
    }

    //JSON check
    @Test (expected = IllegalArgumentException.class)
    public void testJSONNull() throws IOException {
        File file = null;
        AdventureGraingerLibraryDeserialization deserialize = new AdventureGraingerLibraryDeserialization(file);
        Layout layout = deserialize.deserializeAdventureMap();
    }

    //test game start
    @Test
    public void testGetGameStarting() throws IOException, AdventureException {
        GraingerLibraryAdventureService adventureService = new GraingerLibraryAdventureService();
        adventureService.newGame();
        assertEquals("You are on the Engineering Quad, outside the Grainger Library\n" +
                "From here, you can go: North\n" +
                "Items visible: Squirrel\n" +
                "Your Items: ", adventureService.getGame(0).getMessage());
    }

    //invalid entry testing
    @Test
    public void testInvalidCommand() throws IOException {
        engine.inputCommands("dance like jeff");
        assertEquals("I don't understand dance like jeff\n", outContent.toString());
    }

    //mixed cases test
    @Test
    public void testMixedCases() throws IOException {
        engine.inputCommands("daNce LikE jeFf");
        assertEquals("I don't understand dance like jeff\n", outContent.toString());
    }

    //tab character test
    @Test
    public void testTabCharacter() throws IOException {
        engine.inputCommands("go   \t  \t East");
        assertEquals("I don't understand go   \t  \t East\n", outContent.toString());
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

    //the place where you can go
    @Test
    public void testMoveInvalidOutput() throws IOException {
        engine.inputCommands("Go Northeast");
        assertEquals("I can't go Northeast" + "\n", outContent.toString());
    }

    //Examine test
    @Test
    public void testExamineCommand() throws IOException {
        engine.inputCommands("Examine");
        assertEquals("You are on the Engineering Quad, outside the Grainger Library\n" +
                "From here, you can go: North\n" +
                "Items visible: Squirrel" + "\n",outContent.toString());
    }

    @Test
    public void testExamineCommandMixedCase() throws IOException {
        engine.inputCommands("exAmiNe");
        assertEquals("You are on the Engineering Quad, outside the Grainger Library\n" +
                "From here, you can go: North\n" +
                "Items visible: Squirrel" + "\n",outContent.toString());
    }

    @Test
    public void testExamineCommandWhitespace() throws IOException {
        engine.inputCommands("examine   ");
        assertEquals("You are on the Engineering Quad, outside the Grainger Library\n" +
                "From here, you can go: North\n" +
                "Items visible: Squirrel" + "\n",outContent.toString());
    }

    //moving room test
    @Test
    public void testMoveNull() throws IOException {
        engine.inputCommands("go" + null);
        assertEquals(0,engine.gameInterface.roomIndexPresent);
    }

    @Test
    public void testMoveInvalidDirectionFromStart() throws IOException {
        engine.inputCommands("Go West");
        assertEquals(0, engine.gameInterface.roomIndexPresent);
    }

    @Test
    public void testMoveInvalidDirectionFromStartMixedCases() throws IOException {
        engine.inputCommands("Go WeSt");
        assertEquals(0, engine.gameInterface.roomIndexPresent);
    }

    @Test
    public void testMoveWhitespace() throws IOException {
        engine.inputCommands("go east   ");
        assertEquals(2, engine.gameInterface.roomIndexPresent);
    }

    @Test
    public void testMoveWinningRoom() throws IOException {
        engine.inputCommands("Go North");
        engine.inputCommands("Go East");
        engine.inputCommands("Go Down");

        assertEquals(true, engine.reachEndRoom());
    }


    @Test
    public void testMoveWinningRoomMixedCases() throws IOException {
        engine.inputCommands("Go NOrTh");
        engine.inputCommands("gO EASt");
        engine.inputCommands("Go dOwN");

        assertEquals(true, engine.reachEndRoom());
    }

    @Test
    public void testMoveWinningRoomOutput() throws IOException {
        engine.inputCommands("Go North");
        engine.inputCommands("Go East");
        engine.runGame("Go Down");

        assertEquals("GAME OVER" + "\n", outContent.toString());
    }

    @Test
    public void testMoveWinningRoomOutputMixedCases() throws IOException {
        engine.inputCommands("Go NOrTh");
        engine.inputCommands("gO EASt");
        engine.runGame("Go dOwN");

        assertEquals("GAME OVER" + "\n", outContent.toString());
    }

    @Test
    public void testMoveBlankDirection() throws IOException {
        engine.inputCommands("Go");
        assertEquals("I don't understand Go\n", outContent.toString());
    }

    @Test
    public void testMoveMixedCase() throws IOException {
        engine.inputCommands("gO eAst");
        assertEquals(2, engine.gameInterface.roomIndexPresent);
    }

    //Testing History
    @Test
    public void testHistoryOneMove() throws IOException {
        engine.inputCommands("go north");
        engine.inputCommands("history");
        assertEquals("You are in the south entry of the Grainger Library. You can see the elevator, " +
                "the circulation desk, espresso royale, and hallways to the east and west.\n" +
                "From here, you can go: North, Southwest, West, East\n" +
                "Items visible: Map\n" +
                "Your Items: \n" +
                "Rooms Traversed: GraingerEntry\n", outContent.toString());
    }

    @Test
    public void testHistoryMultipleMoves() throws IOException {
        engine.inputCommands("go north");
        engine.inputCommands("go south");
        engine.inputCommands("go east");
        engine.inputCommands("history");
        assertEquals("You are in the east hallway. You can see the Computer Area and the Staff Area.\n" +
                "From here, you can go: South, West, East, Down\n" +
                "Items visible: \n" +
                "Your Items: \n" +
                "Rooms Traversed: GraingerEastHallway\n", outContent.toString());
    }

    @Test
    public void testHistoryBlank() throws IOException {
        engine.inputCommands("history");
        assertEquals("\n", outContent.toString());
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

    //Item Test Cases
    @Test
    public void testTakeInvalidItem() throws IOException {
        engine.inputCommands("Take NFT");
        assertEquals("There is no item \"NFT\" in the room.\n", outContent.toString());
    }

    @Test
    public void testTakeValidItem() throws IOException {
        engine.inputCommands("take squirrel");
        assertEquals("Squirrel", engine.gameInterface.playerItemList.get(0).getItemName());
    }

    @Test
    public void testTakeMixedCase() throws IOException {
        engine.inputCommands("TAkE SqUIrREL");
        assertEquals("Squirrel", engine.gameInterface.playerItemList.get(0).getItemName());
    }

    @Test
    public void testTakeTabKey() throws IOException {
        engine.inputCommands("take Squirrel \t");
        assertEquals("Squirrel", engine.gameInterface.playerItemList.get(0).getItemName());
    }

    @Test
    public void testTakeWhitespace() throws IOException {
        engine.inputCommands("take Squirrel    ");
        assertEquals("Squirrel", engine.gameInterface.playerItemList.get(0).getItemName());
    }

    @Test
    public void testTakeDuplicateItems() throws IOException {
        engine.inputCommands("take squirrel");
        engine.inputCommands("go north");
        engine.inputCommands("drop squirrel");
        engine.inputCommands("take squirrel");

        assertEquals("Squirrel", engine.gameInterface.playerItemList.get(0).getItemName());
    }

    @Test
    public void testDropValidItem() throws IOException {
        engine.inputCommands("Take squirrel");
        engine.inputCommands("Go North");
        engine.inputCommands("Drop squirrel");
        assertEquals("Items visible: Map, Squirrel", engine.gameInterface.displayVisibleItems());
    }

    @Test
    public void testDropMixedCase() throws IOException {
        engine.inputCommands("Take sQuirrel");
        engine.inputCommands("Go NorTh");
        engine.inputCommands("DROP sQuirrel");
        assertEquals("Items visible: Map, Squirrel", engine.gameInterface.displayVisibleItems());
    }

    @Test
    public void testDropWhitespace() throws IOException {
        engine.inputCommands("Take squirrel");
        engine.inputCommands("Go North");
        engine.inputCommands("Drop squirrel   ");
        assertEquals("Items visible: Map, Squirrel", engine.gameInterface.displayVisibleItems());
    }

    @Test
    public void testDropInvalidItem() throws IOException {
        engine.inputCommands("Drop NFT");
        assertEquals("You have no NFT\n", outContent.toString());
    }

    //Destroy Game
    @Test
    public void testValidDestroyGame() throws IOException,AdventureException {
        GraingerLibraryAdventureService adventureService = new GraingerLibraryAdventureService();
        adventureService.newGame();

        assertEquals(true, adventureService.destroyGame(0));
    }

    @Test
    public void tesInvalidDestroyGame() throws IOException {
        GraingerLibraryAdventureService adventureService = new GraingerLibraryAdventureService();
        assertEquals(false, adventureService.destroyGame(5));
    }

    @Test (expected = NullPointerException.class)
    public void testGetGameWithInvalidID() throws IOException {
        GraingerLibraryAdventureService adventureService = new GraingerLibraryAdventureService();
        adventureService.getGame(7);
    }

    @Test
    public void testCreateNewGame() throws IOException, AdventureException {
        GraingerLibraryAdventureService adventureService = new GraingerLibraryAdventureService();
        adventureService.newGame();
        assertEquals(1, adventureService.getGameInstances().size());
    }

    @Test
    public void testCreateMultipleNewGames() throws IOException, AdventureException {
        GraingerLibraryAdventureService adventureService = new GraingerLibraryAdventureService();
        adventureService.newGame();
        adventureService.newGame();
        adventureService.newGame();
        adventureService.newGame();
        adventureService.newGame();
        assertEquals(5, adventureService.getGameInstances().size());
    }

    @Test
    public void testReset() throws IOException, AdventureException {
        GraingerLibraryAdventureService adventureService = new GraingerLibraryAdventureService();
        adventureService.newGame();
        adventureService.reset();
        assertEquals(0, adventureService.getGameInstances().size());
    }

    @Test
    public void testResetNoNewGame() throws IOException {
        GraingerLibraryAdventureService adventureService = new GraingerLibraryAdventureService();
        adventureService.reset();
        assertEquals(0, adventureService.getGameInstances().size());
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
    }
}