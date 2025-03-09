package student;

import java.util.Objects;

/**
 * Represents a board game with attributes such as players, playtime, difficulty, rating, and rank.
 * This class is immutable, meaning all fields are final and can only be accessed through getters.
 */
public class BoardGame implements Comparable<BoardGame> {

    /** The name of the board game. */
    private final String name;
    /** The unique game identifier. */
    private final int id;
    /** The minimum number of players required. */
    private final int minPlayers;
    /** The maximum number of players allowed. */
    private final int maxPlayers;
    /** The maximum play time in minutes. */
    private final int maxPlayTime;
    /** The minimum play time in minutes. */
    private final int minPlayTime;
    /** The average difficulty rating of the game. */
    private final double difficulty;
    /** The ranking of the game. */
    private final int rank;
    /** The average user rating of the game. */
    private final double averageRating;
    /** The year the game was published. */
    private final int yearPublished;

    /**
     * Constructs a new BoardGame object with the specified attributes.
     *
     * @param name The name of the game.
     * @param id The unique identifier.
     * @param minPlayers The minimum number of players required.
     * @param maxPlayers The maximum number of players allowed.
     * @param minPlayTime The minimum play time in minutes.
     * @param maxPlayTime The maximum play time in minutes.
     * @param difficulty The average difficulty rating.
     * @param rank The ranking of the game.
     * @param averageRating The average user rating.
     * @param yearPublished The year the game was published.
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

    /** @return The name of the board game. */
    public String getName() {
        return name;
    }

    /** @return The unique game identifier. */
    public int getId() {
        return id;
    }

    /** @return The minimum number of players required. */
    public int getMinPlayers() {
        return minPlayers;
    }

    /** @return The maximum number of players allowed. */
    public int getMaxPlayers() {
        return maxPlayers;
    }

    /** @return The maximum play time in minutes. */
    public int getMaxPlayTime() {
        return maxPlayTime;
    }

    /** @return The minimum play time in minutes. */
    public int getMinPlayTime() {
        return minPlayTime;
    }

    /** @return The average difficulty rating of the game. */
    public double getDifficulty() {
        return difficulty;
    }

    /** @return The ranking of the game. */
    public int getRank() {
        return rank;
    }

    /** @return The average user rating of the game. */
    public double getRating() {
        return averageRating;
    }

    /** @return The year the game was published. */
    public int getYearPublished() {
        return yearPublished;
    }

    /**
     * Retrieves the numeric value of a specified game attribute.
     *
     * @param col The attribute type from GameData.
     * @return The numeric value associated with the attribute.
     * @throws IllegalArgumentException If the attribute is not numeric.
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
     * Retrieves the string value of a specified game attribute.
     *
     * @param col The attribute type from GameData.
     * @return The string value associated with the attribute.
     * @throws IllegalArgumentException If the attribute is not a string.
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
     * @param col The attribute type from GameData.
     * @return A formatted string with the game name and the specified attribute.
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
     * @return A string containing all game attributes in the correct order.
     */
    @Override
    public String toString() {
        return "BoardGame{name='" + name + "', id=" + id + ", minPlayers=" + minPlayers
                + ", maxPlayers=" + maxPlayers + ", maxPlayTime=" + maxPlayTime
                + ", minPlayTime=" + minPlayTime + ", difficulty=" + difficulty
                + ", rank=" + rank + ", averageRating=" + averageRating
                + ", yearPublished=" + yearPublished + "}";
    }

    /**
     * Compares if two BoardGame objects are equal based on their attributes.
     *
     * @param obj The object to compare.
     * @return True if the objects are equal, otherwise false.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        BoardGame boardGame = (BoardGame) obj;
        return id == boardGame.id
                && minPlayers == boardGame.minPlayers
                && maxPlayers == boardGame.maxPlayers
                && maxPlayTime == boardGame.maxPlayTime
                && minPlayTime == boardGame.minPlayTime
                && Double.compare(boardGame.difficulty, difficulty) == 0
                && rank == boardGame.rank
                && Double.compare(boardGame.averageRating, averageRating) == 0
                && yearPublished == boardGame.yearPublished
                && name.equals(boardGame.name);
    }

    /**
     * Computes the hash code for the BoardGame object.
     *
     * @return The hash code of the object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, id, minPlayers, maxPlayers, minPlayTime,
                maxPlayTime, difficulty, rank, averageRating, yearPublished);
    }

    /**
     * Compares two BoardGame objects based on their name in a case-insensitive manner.
     *
     * @param other The other BoardGame object.
     * @return A comparison result based on the game name.
     */
    @Override
    public int compareTo(BoardGame other) {
        return this.name.compareToIgnoreCase(other.name);
    }
}
