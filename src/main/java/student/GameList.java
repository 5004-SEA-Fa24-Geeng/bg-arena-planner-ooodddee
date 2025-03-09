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

    /** A set of board games stored in a sorted order by name. */
    private final Set<BoardGame> games;

    /** Creates an empty GameList with case-insensitive name sorting. */
    public GameList() {
        this.games = new TreeSet<>(Comparator.comparing(BoardGame::getName, String.CASE_INSENSITIVE_ORDER));
    }

    /** @return List of game names sorted alphabetically (case-insensitive). */
    @Override
    public List<String> getGameNames() {
        return games.stream()
                .map(BoardGame::getName)
                .sorted(String.CASE_INSENSITIVE_ORDER)
                .collect(Collectors.toList());
    }

    /** Removes all games from the list. */
    @Override
    public void clear() {
        games.clear();
    }

    /** @return The count of games in the list. */
    @Override
    public int count() {
        return games.size();
    }

    /**
     * Saves the list of game names to a file.
     *
     * @param filename The file name where games will be saved.
     * @throws RuntimeException If an I/O error occurs.
     */
    @Override
    public void saveGame(String filename) {
        Path filePath = Path.of(filename);
        try {
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
     *
     * @param str Selection string ("all", game name, single index, or range).
     * @param filtered Stream of board games to choose from.
     * @throws IllegalArgumentException If the format is invalid or index is out of range.
     */
    @Override
    public void addToList(String str, Stream<BoardGame> filtered) throws IllegalArgumentException {
        List<BoardGame> filteredList = filtered
                .sorted(Comparator.comparing(BoardGame::getName, String.CASE_INSENSITIVE_ORDER))
                .collect(Collectors.toList());

        if (str.equalsIgnoreCase("all")) {
            games.addAll(filteredList);
            return;
        }

        try {
            Optional<BoardGame> game = filteredList.stream()
                    .filter(g -> g.getName().equalsIgnoreCase(str))
                    .findFirst();
            game.ifPresentOrElse(games::add, () -> {
                throw new IllegalArgumentException("Game not found: " + str);
            });
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid format: " + str);
        }
    }

    /**
     * Removes games from the list based on a selection string.
     *
     * @param str Selection string ("all", game name, single index, or range).
     * @throws IllegalArgumentException If the format is invalid or index is out of range.
     */
    @Override
    public void removeFromList(String str) throws IllegalArgumentException {
        if (str.equalsIgnoreCase("all")) {
            clear();
            return;
        }

        removeGameByName(str);
    }

    /**
     * Removes a game by name.
     *
     * @param name The name of the game to remove.
     * @throws IllegalArgumentException If the game is not found.
     */
    private void removeGameByName(String name) {
        games.removeIf(game -> game.getName().equalsIgnoreCase(name));
    }
}
