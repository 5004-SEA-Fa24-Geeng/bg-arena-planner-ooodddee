package student;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The Planner class filters and sorts board games based on user-defined criteria.
 */
public class Planner implements IPlanner {

    /** The original set of board games. */
    private final Set<BoardGame> games;
    /** The current filtered set of board games. */
    private Set<BoardGame> curGames;

    /**
     * Creates a Planner with a given set of board games.
     *
     * @param games The set of available board games.
     */
    public Planner(Set<BoardGame> games) {
        this.games = new HashSet<>(games);
        this.curGames = new HashSet<>(games);
    }

    @Override
    public Stream<BoardGame> filter(String filter) {
        return filter(filter, GameData.NAME, true);
    }

    @Override
    public Stream<BoardGame> filter(String filter, GameData sortOn) {
        return filter(filter, sortOn, true);
    }

    @Override
    public Stream<BoardGame> filter(String filter, GameData sortOn, boolean ascending) {
        Stream<BoardGame> filteredStream = curGames.stream();

        // Return sorted games if no filter is provided
        if (filter == null || filter.isEmpty()) {
            return applySorting(filteredStream, sortOn, ascending);
        }

        // Apply multiple filters
        List<String> filters = List.of(filter.split(","));
        for (String singleFilter : filters) {
            filteredStream = applySingleFilter(filteredStream, singleFilter.trim());
        }

        // Update curGames to reflect filtered results
        curGames = filteredStream.collect(Collectors.toSet());
        return applySorting(curGames.stream(), sortOn, ascending);
    }

    @Override
    public void reset() {
        curGames = new HashSet<>(games);
    }

    /**
     * Applies a single filter condition.
     *
     * @param stream The stream of games to filter.
     * @param filter The filter condition.
     * @return The filtered stream of games.
     */
    private Stream<BoardGame> applySingleFilter(Stream<BoardGame> stream, String filter) {
        Operations operator = Operations.getOperatorFromStr(filter);
        if (operator == null) {
            return stream;
        }

        String[] parts = filter.split(operator.getOperator());
        if (parts.length != 2) {
            return stream;
        }

        String field = parts[0].trim();
        String value = parts[1].trim();

        GameData column;
        try {
            column = GameData.fromString(field);
        } catch (IllegalArgumentException e) {
            return stream;
        }

        if (isNumericColumn(column)) {
            return applyNumericFilter(stream, column, operator, value);
        } else {
            return applyStringFilter(stream, column, operator, value);
        }
    }

    /**
     * Checks if the field is numeric.
     *
     * @param column The field to check.
     * @return True if the field is numeric, otherwise false.
     */
    private boolean isNumericColumn(GameData column) {
        return switch (column) {
            case RATING, DIFFICULTY, RANK, MIN_PLAYERS, MAX_PLAYERS, MIN_TIME, MAX_TIME, YEAR -> true;
            default -> false;
        };
    }

    /**
     * Applies a numeric filter condition.
     *
     * @param stream The stream of games to filter.
     * @param column The field to filter on.
     * @param operator The comparison operator.
     * @param value The value to compare.
     * @return The filtered stream of games.
     */
    private Stream<BoardGame> applyNumericFilter(Stream<BoardGame> stream, GameData column,
                                                 Operations operator, String value) {
        double numericValue;
        try {
            numericValue = Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return stream;
        }

        return stream.filter(game -> {
            double gameValue = game.getNumericValue(column);
            return switch (operator) {
                case EQUALS -> gameValue == numericValue;
                case NOT_EQUALS -> gameValue != numericValue;
                case GREATER_THAN -> gameValue > numericValue;
                case LESS_THAN -> gameValue < numericValue;
                case GREATER_THAN_EQUALS -> gameValue >= numericValue;
                case LESS_THAN_EQUALS -> gameValue <= numericValue;
                default -> true;
            };
        });
    }

    /**
     * Applies a string filter condition.
     *
     * @param stream The stream of games to filter.
     * @param column The field to filter on.
     * @param operator The comparison operator.
     * @param value The value to compare.
     * @return The filtered stream of games.
     */
    private Stream<BoardGame> applyStringFilter(Stream<BoardGame> stream, GameData column,
                                                Operations operator, String value) {
        return stream.filter(game -> {
            String gameValue = game.getStringValue(column);
            return switch (operator) {
                case EQUALS -> gameValue.equalsIgnoreCase(value);
                case NOT_EQUALS -> !gameValue.equalsIgnoreCase(value);
                case GREATER_THAN -> gameValue.compareToIgnoreCase(value) > 0;
                case LESS_THAN -> gameValue.compareToIgnoreCase(value) < 0;
                case GREATER_THAN_EQUALS -> gameValue.compareToIgnoreCase(value) >= 0;
                case LESS_THAN_EQUALS -> gameValue.compareToIgnoreCase(value) <= 0;
                case CONTAINS -> gameValue.toLowerCase().contains(value.toLowerCase());
                default -> true;
            };
        });
    }

    /**
     * Sorts the games based on a specified field.
     *
     * @param stream The stream of games to sort.
     * @param sortOn The field to sort on.
     * @param ascending True for ascending order, false for descending.
     * @return The sorted stream of games.
     */
    private Stream<BoardGame> applySorting(Stream<BoardGame> stream, GameData sortOn,
                                           boolean ascending) {
        Comparator<BoardGame> comparator = switch (sortOn) {
            case NAME -> Comparator.comparing(game -> game.getName().toLowerCase(),
                    String.CASE_INSENSITIVE_ORDER);
            case MAX_PLAYERS -> Comparator.comparingInt(BoardGame::getMaxPlayers);
            case MIN_PLAYERS -> Comparator.comparingInt(BoardGame::getMinPlayers);
            case MAX_TIME -> Comparator.comparingInt(BoardGame::getMaxPlayTime);
            case MIN_TIME -> Comparator.comparingInt(BoardGame::getMinPlayTime);
            case RANK -> Comparator.comparingInt(BoardGame::getRank);
            case DIFFICULTY -> Comparator.comparingDouble(BoardGame::getDifficulty);
            case YEAR -> Comparator.comparingInt(BoardGame::getYearPublished);
            default -> null;
        };

        if (comparator != null) {
            return ascending ? stream.sorted(comparator) : stream.sorted(comparator.reversed());
        }
        return stream;
    }
}
