package student.server;

import student.adventure.Directions;
import student.adventure.GameEngine;
import student.adventure.Layout;

import java.io.IOException;
import java.util.*;

public class GraingerLibraryAdventureService implements AdventureService{
    int id = 0;
    Map<Integer, GameEngine> gameInstances = new HashMap<>();

    // setter for gameInstances
    public void setGameInstances(Map<Integer, GameEngine> gameInstances) {
        this.gameInstances = gameInstances;
    }

    //getter for gameInstances
    public Map<Integer, GameEngine> getGameInstances() {
        return gameInstances;
    }

    /**
     * Resets the service to its initial state.
     */
    @Override
    public void reset() {
        id = 0;
        gameInstances.clear();
    }

    /**
     * Creates a new Adventure game and stores it.
     * @return the id of the game.
     */
    @Override
    public int newGame() throws AdventureException, IOException {
        GameEngine gameEngine = new GameEngine();
        int previousId = id;

        gameInstances.put(id, gameEngine);

        return previousId;
    }

    /**
     * Returns the state of the game instance associated with the given ID.
     * @param id the instance id
     * @return the current state of the game
     */
    @Override
    public GameStatus getGame(int id) {
        boolean error = false;
        GameEngine gameEngine = gameInstances.get(id);
        Map<String, List<String>> commandOptions = new HashMap<>();
        AdventureState state = new AdventureState(gameEngine.getGameInterface().displayTraversedRoomsService());

        commandOptions.put("Go", gameEngine.getGameInterface().listRoomOptions());
        commandOptions.put("Drop", gameEngine.getGameInterface().listPlayerItems());
        commandOptions.put("Take", gameEngine.getGameInterface().listRoomItems());

        GameStatus gameStatus = new GameStatus(error, id, gameEngine.getGameInterface().displayMessage() ,null, null, state, commandOptions);

        return gameStatus;
    }

    /**
     * Removes & destroys a game instance with the given ID.
     * @param id the instance id
     * @return false if the instance could not be found and/or was not deleted
     */
    @Override
    public boolean destroyGame(int id) {
        if (gameInstances.containsKey(id)) {
            gameInstances.remove(id);
            return true;
        }
        return false;
    }

    /**
     * Executes a command on the game instance with the given id, changing the game state if applicable.
     * @param id the instance id
     * @param command the issued command
     */
    @Override
    public void executeCommand(int id, Command command) {
        GameEngine gameEngine = gameInstances.get(id);
        String commandLine = command.getCommandName() + " " + command.getCommandValue();

        gameEngine.inputCommands(commandLine);
    }

    /**
     * Returns a sorted leaderboard of player "high" scores.
     * @return a sorted map of player names to scores
     */
    @Override
    public SortedMap<String, Integer> fetchLeaderboard() {
        return null;
    }


}
