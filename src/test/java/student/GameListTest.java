package student;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit test class for the GameList class.
 */
public class GameListTest {
    private GameList gameList;
    private List<BoardGame> gamesList; // Store games in a list to avoid stream reuse issues

    /**
     * Sets up the test environment before each test.
     */
    @BeforeEach
    void setUp() {
        gameList = new GameList();

        gamesList = List.of(
                new BoardGame("17 days", 6, 1, 8, 70, 70, 9.0, 600, 9.0, 2005),
                new BoardGame("Chess", 7, 2, 2, 10, 20, 10.0, 700, 10.0, 2006),
                new BoardGame("Go", 1, 2, 5, 30, 30, 8.0, 100, 7.5, 2000),
                new BoardGame("Go Fish", 2, 2, 10, 20, 120, 3.0, 200, 6.5, 2001),
                new BoardGame("golang", 4, 2, 7, 50, 55, 7.0, 400, 9.5, 2003),
                new BoardGame("GoRami", 3, 6, 6, 40, 42, 5.0, 300, 8.5, 2002),
                new BoardGame("Monopoly", 8, 6, 10, 20, 1000, 1.0, 800, 5.0, 2007),
                new BoardGame("Tucano", 5, 10, 20, 60, 90, 6.0, 500, 8.0, 2004)
        );
    }

    /**
     * Tests retrieving game names in sorted order.
     */
    @Test
    void getGameNames() {
        gameList.addToList("all", gamesList.stream());
        List<String> expectedNames = List.of("17 days", "Chess", "Go", "Go Fish", "golang", "GoRami", "Monopoly", "Tucano");

        assertEquals(expectedNames, gameList.getGameNames());
    }

    /**
     * Tests clearing the game list.
     */
    @Test
    void clear() {
        gameList.addToList("all", gamesList.stream());
        gameList.clear();
        assertEquals(0, gameList.count());
    }

    /**
     * Tests counting the number of games in the list.
     */
    @Test
    void count() {
        assertEquals(0, gameList.count());
        gameList.addToList("Go", gamesList.stream());
        assertEquals(1, gameList.count());
    }

    /**
     * Tests saving the game list to a file.
     */
    @Test
    void saveGame() throws IOException {
        gameList.addToList("all", gamesList.stream());
        String filename = "test_game_list.txt";
        gameList.saveGame(filename);

        File file = new File(filename);
        assertTrue(file.exists());

        List<String> lines = Files.readAllLines(file.toPath());
        assertEquals(8, lines.size());
        assertTrue(lines.contains("Chess"));
        assertTrue(lines.contains("Monopoly"));

        file.delete();
    }

    /**
     * Tests adding a single game by index.
     */
    @Test
    void addToListByIndex() {
        gameList.addToList("1", gamesList.stream());
        assertEquals(1, gameList.count());
        assertEquals(List.of("17 days"), gameList.getGameNames());
    }

    /**
     * Tests adding a range of games.
     */
    @Test
    void addToListByRange() {
        gameList.addToList("1-3", gamesList.stream());
        assertEquals(3, gameList.count());
        assertTrue(gameList.getGameNames().containsAll(List.of("17 days", "Chess", "Go")));
    }

    /**
     * Tests removing a game by name.
     */
    @Test
    void removeFromListByName() {
        gameList.addToList("all", gamesList.stream());
        gameList.removeFromList("Go");
        assertEquals(7, gameList.count());
        assertFalse(gameList.getGameNames().contains("Go"));
    }

    /**
     * Tests removing a game by index.
     */
    @Test
    void removeFromListByIndex() {
        gameList.addToList("all", gamesList.stream());
        gameList.removeFromList("1");
        assertEquals(7, gameList.count());
        assertFalse(gameList.getGameNames().contains("17 days"));
    }

    /**
     * Tests removing a range of games.
     */
    @Test
    void removeFromListByRange() {
        gameList.addToList("all", gamesList.stream());
        gameList.removeFromList("1-2");
        assertEquals(6, gameList.count());
        assertFalse(gameList.getGameNames().containsAll(List.of("17 days", "Chess")));
    }

    /**
     * Tests adding invalid inputs to the list.
     */
    @Test
    void testAddToListInvalidInput() {
        // Invalid index
        assertThrows(IllegalArgumentException.class, () -> gameList.addToList("20", gamesList.stream()));

        // Invalid range format
        assertThrows(IllegalArgumentException.class, () -> gameList.addToList("1-xyz", gamesList.stream()));

        // Invalid format (random string)
        assertThrows(IllegalArgumentException.class, () -> gameList.addToList("invalid-input", gamesList.stream()));

        // Non-existent game name
        assertThrows(IllegalArgumentException.class, () -> gameList.addToList("NonExistentGame", gamesList.stream()));
    }

    /**
     * Tests removing invalid inputs from the list.
     */
    @Test
    void testRemoveFromListInvalidInput() {
        gameList.addToList("all", gamesList.stream());

        // Invalid index
        assertThrows(IllegalArgumentException.class, () -> gameList.removeFromList("20"));

        // Invalid range format
        assertThrows(IllegalArgumentException.class, () -> gameList.removeFromList("1-xyz"));

        // Invalid format (random string)
        assertThrows(IllegalArgumentException.class, () -> gameList.removeFromList("invalid-input"));

        // Non-existent game name
        assertThrows(IllegalArgumentException.class, () -> gameList.removeFromList("NonExistentGame"));
    }
}
