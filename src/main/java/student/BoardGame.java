package student;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import java.util.List;

/**
 * Represents a board game with attributes such as players, playtime, difficulty, rating, and rank.
 * This class is immutable, meaning all fields are final and can only be accessed through getters.
 */
public class BoardGame implements Comparable<BoardGame> {

    /** Game name. */
    private final String name;
    /** Unique game identifier. */
    private final int id;
    /** Minimum players required. */
    private final int minPlayers;
    /** Maximum players allowed. */
    private final int maxPlayers;
    /** Maximum play time in minutes. */
    private final int maxPlayTime;
    /** Minimum play time in minutes. */
    private final int minPlayTime;
    /** Average difficulty rating. */
    private final double difficulty;
    /** Game rank. */
    private final int rank;
    /** Average user rating. */
    private final double averageRating;
    /** Year the game was published. */
    private final int yearPublished;

    /**
     * Creates a new BoardGame object.
     *
     * @param name Game name
     * @param id Unique identifier
     * @param minPlayers Minimum players required
     * @param maxPlayers Maximum players allowed
     * @param minPlayTime Minimum play time in minutes
     * @param maxPlayTime Maximum play time in minutes
     * @param difficulty Average difficulty rating
     * @param rank Game rank
     * @param averageRating Average user rating
     * @param yearPublished Year the game was published
     */
    public BoardGame(String name, int id, int minPlayers, int maxPlayers, int minPlayTime,
                     int maxPlayTime, double difficulty, int rank,
                     double averageRating, int yearPublished) {
        this.name = name;
        this.id = id;
        this.minPlayers = minPlayers;
        this.maxPlayers = maxPlayers;
        this.maxPlayTime = maxPlayTime;
        this.minPlayTime = minPlayTime;
        this.difficulty = difficulty;
        this.rank = rank;
        this.averageRating = averageRating;
        this.yearPublished = yearPublished;
    }

    /** @return the name of the board game. */
    public String getName() {
        return name;
    }

    /** @return the game ID. */
    public int getId() {
        return id;
    }

    /** @return the minimum number of players. */
    public int getMinPlayers() {
        return minPlayers;
    }

    /** @return the maximum number of players. */
    public int getMaxPlayers() {
        return maxPlayers;
    }

    /** @return the maximum play time. */
    public int getMaxPlayTime() {
        return maxPlayTime;
    }

    /** @return the minimum play time. */
    public int getMinPlayTime() {
        return minPlayTime;
    }

    /** @return the difficulty rating. */
    public double getDifficulty() {
        return difficulty;
    }

    /** @return the rank of the game. */
    public int getRank() {
        return rank;
    }

    /** @return the average rating. */
    public double getRating() {
        return averageRating;
    }

    /** @return the year of publication. */
    public int getYearPublished() {
        return yearPublished;
    }

    /**
     * Returns the numeric value of the game attribute based on the given GameData type.
     *
     * @param col The GameData attribute
     * @return The corresponding numeric value
     * @throws IllegalArgumentException If the attribute is not numeric
     */
    public double getNumericValue(GameData col) throws IllegalArgumentException {
        return switch (col) {
            case RATING -> averageRating;
            case DIFFICULTY -> difficulty;
            case RANK -> rank;
            case MIN_PLAYERS -> minPlayers;
            case MAX_PLAYERS -> maxPlayers;
            case MIN_TIME -> minPlayTime;
            case MAX_TIME -> maxPlayTime;
            case YEAR -> yearPublished;
            case ID -> id;
            default -> throw new IllegalArgumentException("Invalid numeric column: " + col);
        };
    }

    @Override
    public int compareTo(BoardGame other) {
        return this.name.compareToIgnoreCase(other.name);
    }
}
