package student;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Manages a list of board games, allowing adding, removing, listing, and saving games.
 */
public class GameList implements IGameList {

    private final Set<BoardGame> games; // Stores board games in a sorted set

    /**
     * Creates an empty GameList with case-insensitive name sorting.
     */
    public GameList() {
        this.games = new TreeSet<>(Comparator.comparing(BoardGame::getName, String.CASE_INSENSITIVE_ORDER));
    }

    /**
     * Returns a sorted list of game names.
     * @return List of game names sorted alphabetically (case-insensitive).
     */
    @Override
    public List<String> getGameNames() {
        return games.stream()
                .map(BoardGame::getName)
                .sorted(String.CASE_INSENSITIVE_ORDER)
                .collect(Collectors.toList());
    }

    /**
     * Removes all games from the list.
     */
    @Override
    public void clear() {
        games.clear();
    }

    /**
     * Returns the total number of games in the list.
     * @return The count of games in the list.
     */
    @Override
    public int count() {
        return games.size();
    }

    /**
     * Saves the list of game names to a file.
     * @param filename The file name where games will be saved.
     * @throws RuntimeException If an I/O error occurs.
     */
    @Override
    public void saveGame(String filename) {
        Path filePath = Path.of(filename);
        try {
            // Create directories if necessary
            if (filePath.getParent() != null) {
                Files.createDirectories(filePath.getParent());
            }
            List<String> gameNames = getGameNames();
            Files.write(filePath, gameNames, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Error writing to file: " + filename, e);
        }
    }

    /**
     * Adds games to the list based on a selection string.
     * Supports adding all games, a single game, or a range of games.
     * @param str Selection string ("all", game name, single index, or range).
     * @param filtered Stream of board games to choose from.
     * @throws IllegalArgumentException If the format is invalid or index is out of range.
     */
    @Override
    public void addToList(String str, Stream<BoardGame> filtered) throws IllegalArgumentException {
        List<BoardGame> filteredList = filtered
                .sorted(Comparator.comparing(BoardGame::getName, String.CASE_INSENSITIVE_ORDER))
                .collect(Collectors.toList());

        // Add all filtered games
        if (str.equalsIgnoreCase("all")) {
            games.addAll(filteredList);
            return;
        }

        try {
            if (str.matches("\\d+")) { // Single index
                int index = Integer.parseInt(str) - 1;
                if (index < 0 || index >= filteredList.size()) {
                    throw new IllegalArgumentException("Index out of range: " + str);
                }
                games.add(filteredList.get(index));
            } else if (str.matches("\\d+-\\d+")) { // Range selection
                String[] range = str.split("-");
                int start = Integer.parseInt(range[0]) - 1;
                int end = Integer.parseInt(range[1]) - 1;
                if (start < 0 || end >= filteredList.size() || start > end) {
                    throw new IllegalArgumentException("Invalid range: " + str);
                }
                games.addAll(filteredList.subList(start, end + 1));
            } else { // Add by name
                Optional<BoardGame> game = filteredList.stream()
                        .filter(g -> g.getName().equalsIgnoreCase(str))
                        .findFirst();
                game.ifPresentOrElse(games::add, () -> {
                    throw new IllegalArgumentException("Game not found: " + str);
                });
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid format: " + str);
        }
    }

    /**
     * Removes games from the list based on a selection string.
     * Supports removing all games, a single game, or a range of games.
     * @param str Selection string ("all", game name, single index, or range).
     * @throws IllegalArgumentException If the format is invalid or index is out of range.
     */
    @Override
    public void removeFromList(String str) throws IllegalArgumentException {
        if (str.equalsIgnoreCase("all")) {
            clear();
            return;
        }

        List<String> gameNames = getGameNames();

        try {
            if (str.matches("\\d+")) { // Single index
                int index = Integer.parseInt(str) - 1;
                if (index < 0 || index >= gameNames.size()) {
                    throw new IllegalArgumentException("Index out of range: " + str);
                }
                removeGameByName(gameNames.get(index));
            } else if (str.matches("\\d+-\\d+")) { // Range selection
                String[] range = str.split("-");
                int start = Integer.parseInt(range[0]) - 1;
                int end = Integer.parseInt(range[1]) - 1;
                if (start < 0 || end >= gameNames.size() || start > end) {
                    throw new IllegalArgumentException("Invalid range: " + str);
                }
                for (int i = start; i <= end; i++) {
                    removeGameByName(gameNames.get(i));
                }
            } else { // Remove by name
                removeGameByName(str);
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid format: " + str);
        }
    }

    /**
     * Removes a game by name.
     * @param name The name of the game to remove.
     * @throws IllegalArgumentException If the game is not found.
     */
    private void removeGameByName(String name) {
        Iterator<BoardGame> iterator = games.iterator();
        while (iterator.hasNext()) {
            BoardGame game = iterator.next();
            if (game.getName().equalsIgnoreCase(name)) {
                iterator.remove();
                return;
            }
        }
        throw new IllegalArgumentException("Game not found: " + name);
    }
}
