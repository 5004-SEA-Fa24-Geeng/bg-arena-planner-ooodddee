package student;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import java.util.List;

/**
 * Represents a board game with various attributes such as players, playtime, difficulty, rating, and rank.
 * This class is immutable, meaning all fields are final and can only be accessed through getters.
 */
public class BoardGame implements Comparable<BoardGame> {

    private final String name; // Game name
    private final int id; // Unique game identifier
    private final int minPlayers; // Minimum players required
    private final int maxPlayers; // Maximum players allowed
    private final int maxPlayTime; // Maximum play time in minutes
    private final int minPlayTime; // Minimum play time in minutes
    private final double difficulty; // Average difficulty rating
    private final int rank; // Game rank
    private final double averageRating; // Average user rating
    private final int yearPublished; // Year the game was published

    /**
     * Creates a new BoardGame object.
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
                     int maxPlayTime, double difficulty, int rank, double averageRating, int yearPublished) {
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

    public String getName() { return name; }
    public int getId() { return id; }
    public int getMinPlayers() { return minPlayers; }
    public int getMaxPlayers() { return maxPlayers; }
    public int getMaxPlayTime() { return maxPlayTime; }
    public int getMinPlayTime() { return minPlayTime; }
    public double getDifficulty() { return difficulty; }
    public int getRank() { return rank; }
    public double getRating() { return averageRating; }
    public int getYearPublished() { return yearPublished; }

    /**
     * Returns the numeric value of the game attribute based on the given GameData type.
     * Throws an exception if the attribute is not numeric.
     * @param col The GameData attribute
     * @return The corresponding numeric value
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
     * Throws an exception if the attribute is not a string.
     * @param col The GameData attribute
     * @return The corresponding string value
     */
    public String getStringValue(GameData col) throws IllegalArgumentException {
        if (col == GameData.NAME) {
            return name;
        }
        throw new IllegalArgumentException("Invalid string column: " + col);
    }

    /**
     * Returns a formatted string containing the game name and the specified attribute value.
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
     * @return A string containing all game attributes
     */
    @Override
    public String toString() {
        return "BoardGame{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", minPlayers=" + minPlayers +
                ", maxPlayers=" + maxPlayers +
                ", maxPlayTime=" + maxPlayTime +
                ", minPlayTime=" + minPlayTime +
                ", difficulty=" + difficulty +
                ", rank=" + rank +
                ", averageRating=" + averageRating +
                ", yearPublished=" + yearPublished +
                '}';
    }

    /**
     * Checks if two BoardGame objects are equal.
     * Ignores fields: minPlayers, maxPlayers, minPlayTime, maxPlayTime, difficulty, rank, rating, yearPublished.
     * @param obj The object to compare
     * @return True if the objects are equal, otherwise false
     */
    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj,
                List.of("minPlayers", "maxPlayers", "maxPlayTime", "minPlayTime",
                        "difficulty", "rank", "averageRating", "yearPublished"));
    }

    /**
     * Computes the hash code for the BoardGame object.
     * Ignores fields: minPlayers, maxPlayers, minPlayTime, maxPlayTime, difficulty, rank, rating, yearPublished.
     * @return The hash code of the object
     */
    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this,
                List.of("minPlayers", "maxPlayers", "maxPlayTime", "minPlayTime",
                        "difficulty", "rank", "averageRating", "yearPublished"));
    }

    /**
     * Compares two BoardGame objects by name (case-insensitive).
     * @param other The other BoardGame object
     * @return Comparison result based on name
     */
    @Override
    public int compareTo(BoardGame other) {
        return this.name.compareToIgnoreCase(other.name);
    }

    /**
     * Main method for quick testing.
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        BoardGame bg = new BoardGame("Catan", 1, 3, 4, 60, 30, 2.5, 1, 4.5, 1995);
        BoardGame bg2 = new BoardGame("Catan", 1, 3, 4, 60, 30, 2.5, 1, 4.5, 1995);
        BoardGame bg3 = new BoardGame("Catan", 2, 3, 4, 60, 30, 2.5, 1, 4.5, 1995);

        System.out.println(bg);
        System.out.println(bg.equals(bg) + " " + (bg.hashCode() == bg2.hashCode()));
        System.out.println(bg.equals(bg2) + " " + (bg.hashCode() == bg2.hashCode()));
        System.out.println(bg.equals(bg3) + " " + (bg.hashCode() == bg3.hashCode()));
    }
}
