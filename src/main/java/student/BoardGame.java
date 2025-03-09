package student;

import java.util.Objects;

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

    /**
     * Returns the string value of the game attribute based on the given GameData type.
     *
     * @param col The GameData attribute
     * @return The corresponding string value
     * @throws IllegalArgumentException If the attribute is not a string
     */
    public String getStringValue(GameData col) throws IllegalArgumentException {
        if (col == GameData.NAME) {
            return name;
        }
        throw new IllegalArgumentException("Invalid string column: " + col);
    }

    /**
     * Returns a formatted string containing the game name and the specified attribute value.
     *
     * @param col The GameData attribute
     * @return A formatted string with the game name and the specified attribute
     */
    public String toStringWithInfo(GameData col) {
        return switch (col) {
            case NAME -> name;
            case RATING -> String.format("%s (%.2f)", name, averageRating);
            case DIFFICULTY -> String.format("%s (%.2f)", name, difficulty);
            case RANK -> String.format("%s (%d)", name, rank);
            case MIN_PLAYERS -> String.format("%s (%d)", name, minPlayers);
            case MAX_PLAYERS -> String.format("%s (%d)", name, maxPlayers);
            case MIN_TIME -> String.format("%s (%d min)", name, minPlayTime);
            case MAX_TIME -> String.format("%s (%d min)", name, maxPlayTime);
            case YEAR -> String.format("%s (%d)", name, yearPublished);
            default -> name;
        };
    }

    /**
     * Returns a string representation of the BoardGame object.
     *
     * @return A string containing all game attributes in the correct order
     */
    @Override
    public String toString() {
        return String.format(
                "BoardGame{name='%s', id=%d, minPlayers=%d, maxPlayers=%d, maxPlayTime=%d, minPlayTime=%d, " +
                        "difficulty=%.1f, rank=%d, averageRating=%.1f, yearPublished=%d}",
                name, id, minPlayers, maxPlayers, maxPlayTime, minPlayTime,
                difficulty, rank, averageRating, yearPublished
        );
    }


    /**
     * Checks if two BoardGame objects are equal.
     *
     * @param obj The object to compare
     * @return True if the objects are equal, otherwise false
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        BoardGame boardGame = (BoardGame) obj;
        return id == boardGame.id &&
                minPlayers == boardGame.minPlayers &&
                maxPlayers == boardGame.maxPlayers &&
                maxPlayTime == boardGame.maxPlayTime &&
                minPlayTime == boardGame.minPlayTime &&
                Double.compare(boardGame.difficulty, difficulty) == 0 &&
                rank == boardGame.rank &&
                Double.compare(boardGame.averageRating, averageRating) == 0 &&
                yearPublished == boardGame.yearPublished &&
                name.equals(boardGame.name);
    }

    /**
     * Computes the hash code for the BoardGame object.
     *
     * @return The hash code of the object
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, id, minPlayers, maxPlayers, minPlayTime, maxPlayTime, difficulty, rank, averageRating, yearPublished);
    }

    /**
     * Compares two BoardGame objects by name (case-insensitive).
     *
     * @param other The other BoardGame object
     * @return Comparison result based on name
     */
    @Override
    public int compareTo(BoardGame other) {
        return this.name.compareToIgnoreCase(other.name);
    }
}
