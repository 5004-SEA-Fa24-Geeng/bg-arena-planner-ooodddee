package student;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the BoardGame class.
 */
public class BoardGameTest {
    private BoardGame game1;
    private BoardGame game2;
    private BoardGame game3;

    @BeforeEach
    void setUp() {
        // Create different BoardGame instances for testing
        game1 = new BoardGame("Chess", 1, 2, 2, 10, 30, 3.5, 50, 8.7, 2000);
        game2 = new BoardGame("Monopoly", 2, 2, 6, 60, 180, 2.0, 200, 6.5, 1995);
        game3 = new BoardGame("Go", 3, 2, 2, 30, 60, 4.5, 10, 9.2, 1990);
    }

    @Test
    void testGetters() {
        assertEquals("Chess", game1.getName());
        assertEquals(1, game1.getId());
        assertEquals(2, game1.getMinPlayers());
        assertEquals(2, game1.getMaxPlayers());
        assertEquals(10, game1.getMinPlayTime());
        assertEquals(30, game1.getMaxPlayTime());
        assertEquals(3.5, game1.getDifficulty());
        assertEquals(50, game1.getRank());
        assertEquals(8.7, game1.getRating());
        assertEquals(2000, game1.getYearPublished());
    }

    @Test
    void testNumericValues() {
        assertEquals(8.7, game1.getNumericValue(GameData.RATING));
        assertEquals(3.5, game1.getNumericValue(GameData.DIFFICULTY));
        assertEquals(50, game1.getNumericValue(GameData.RANK));
        assertEquals(2, game1.getNumericValue(GameData.MIN_PLAYERS));
        assertEquals(2, game1.getNumericValue(GameData.MAX_PLAYERS));
        assertEquals(10, game1.getNumericValue(GameData.MIN_TIME));
        assertEquals(30, game1.getNumericValue(GameData.MAX_TIME));
        assertEquals(2000, game1.getNumericValue(GameData.YEAR));
        assertEquals(1, game1.getNumericValue(GameData.ID));
    }

    @Test
    void testStringValues() {
        assertEquals("Chess", game1.getStringValue(GameData.NAME));
    }

    @Test
    void testInvalidNumericValueThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            game1.getNumericValue(GameData.NAME); // Name is not numeric
        });
        assertTrue(exception.getMessage().contains("Invalid numeric column"));
    }

    @Test
    void testInvalidStringValueThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            game1.getStringValue(GameData.RANK); // Rank is not a string
        });
        assertTrue(exception.getMessage().contains("Invalid string column"));
    }

    @Test
    void testToStringWithInfo() {
        assertEquals("Chess (8.70)", game1.toStringWithInfo(GameData.RATING));
        assertEquals("Chess (3.50)", game1.toStringWithInfo(GameData.DIFFICULTY));
        assertEquals("Chess (50)", game1.toStringWithInfo(GameData.RANK));
        assertEquals("Chess (2)", game1.toStringWithInfo(GameData.MIN_PLAYERS));
        assertEquals("Chess (30 min)", game1.toStringWithInfo(GameData.MAX_TIME));
        assertEquals("Chess (2000)", game1.toStringWithInfo(GameData.YEAR));
    }

    @Test
    void testToString() {
        String expected = "BoardGame{name='Chess', id=1, minPlayers=2, maxPlayers=2, maxPlayTime=30, " +
                "minPlayTime=10, difficulty=3.5, rank=50, averageRating=8.7, yearPublished=2000}";
        assertEquals(expected, game1.toString());
    }

    @Test
    void testEqualsAndHashCode() {
        BoardGame duplicateGame = new BoardGame("Chess", 1, 2, 2, 10, 30, 3.5, 50, 8.7, 2000);
        assertEquals(game1, duplicateGame);
        assertEquals(game1.hashCode(), duplicateGame.hashCode());

        assertNotEquals(game1, game2);
        assertNotEquals(game1.hashCode(), game2.hashCode());
    }

    @Test
    void testCompareTo() {
        assertTrue(game1.compareTo(game2) < 0); // "Chess" comes before "Monopoly"
        assertTrue(game2.compareTo(game3) > 0); // "Monopoly" comes after "Go"
        assertEquals(0, game1.compareTo(new BoardGame("Chess", 4, 2, 4, 10, 30, 3.0, 50, 8.5, 2001))); // Same name
    }
}
