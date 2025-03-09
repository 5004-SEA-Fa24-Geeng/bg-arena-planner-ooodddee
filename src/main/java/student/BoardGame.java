package student;

import java.util.Objects;

/**
 * Represents a board game with attributes such as players, playtime, difficulty, rating, and rank.
 * This class is immutable, meaning all fields are final and can only be accessed through getters.
 */
public class BoardGame implements Comparable<BoardGame> {

    private final String name;
    private final int id;
    private final int minPlayers;
    private final int maxPlayers;
    private final int maxPlayTime;
    private final int minPlayTime;
    private final double difficulty;
    private final int rank;
    private final double averageRating;
    private final int yearPublished;

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

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public int getMinPlayers() {
        return minPlayers;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public int getMaxPlayTime() {
        return maxPlayTime;
    }

    public int getMinPlayTime() {
        return minPlayTime;
    }

    public double getDifficulty() {
        return difficulty;
    }

    public int getRank() {
        return rank;
    }

    public double getRating() {
        return averageRating;
    }

    public int getYearPublished() {
        return yearPublished;
    }

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

    public String getStringValue(GameData col) throws IllegalArgumentException {
        if (col == GameData.NAME) {
            return name;
        }
        throw new IllegalArgumentException("Invalid string column: " + col);
    }

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

    @Override
    public String toString() {
        return "BoardGame{name='" + name + "', id=" + id + ", minPlayers=" + minPlayers
                + ", maxPlayers=" + maxPlayers + ", maxPlayTime=" + maxPlayTime
                + ", minPlayTime=" + minPlayTime + ", difficulty=" + difficulty
                + ", rank=" + rank + ", averageRating=" + averageRating
                + ", yearPublished=" + yearPublished + "}";
    }

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

    @Override
    public int hashCode() {
        return Objects.hash(name, id, minPlayers, maxPlayers, minPlayTime,
                maxPlayTime, difficulty, rank, averageRating, yearPublished);
    }

    @Override
    public int compareTo(BoardGame other) {
        return this.name.compareToIgnoreCase(other.name);
    }
}
