# Board Game Arena Planner Design Document


This document is meant to provide a tool for you to demonstrate the design process. You need to work on this before you code, and after have a finished product. That way you can compare the changes, and changes in design are normal as you work through a project. It is contrary to popular belief, but we are not perfect our first attempt. We need to iterate on our designs to make them better. This document is a tool to help you do that.


## (INITIAL DESIGN): Class Diagram

Place your class diagrams below. Make sure you check the file in the browser on github.com to make sure it is rendering correctly. If it is not, you will need to fix it. As a reminder, here is a link to tools that can help you create a class diagram: [Class Resources: Class Design Tools](https://github.com/CS5004-khoury-lionelle/Resources?tab=readme-ov-file#uml-design-tools)

### Provided Code

Provide a class diagram for the provided code as you read through it.  For the classes you are adding, you will create them as a separate diagram, so for now, you can just point towards the interfaces for the provided code diagram.

```mermaid
---
title: Provided Code - Class Diagram (Plan)
---
classDiagram

    class BGArenaPlanner
    class BoardGame
    class ConsoleApp
    class GameData
    class GameList
    class GamesLoader
    class Operations
    class Planner
    class IGameList {
        <<interface>>
    }
    class IPlanner {
        <<interface>>
    }


    IGameList <|.. GameList
    IPlanner  <|.. Planner

    BGArenaPlanner --> IGameList : uses
    BGArenaPlanner --> IPlanner  : uses
    BGArenaPlanner --> GamesLoader : uses
    BGArenaPlanner --> ConsoleApp  : uses

    ConsoleApp --> IGameList : uses
    ConsoleApp --> IPlanner  : uses

    GameList --> BoardGame
    Planner  --> BoardGame
    GamesLoader --> BoardGame

```

### Your Plans/Design

Create a class diagram for the classes you plan to create. This is your initial design, and it is okay if it changes. Your starting points are the interfaces.

```mermaid
---
title: Arena Board Games - Full UML Diagram
---
classDiagram

    class BGArenaPlanner {
        <<final>>
        - DEFAULT_COLLECTION$: String
        - BGArenaPlanner()
        + main(String[] args)$: void
    }

    class BoardGame {
        - name : String
        - id : int
        - minPlayers : int
        - maxPlayers : int
        - minPlayTime : int
        - maxPlayTime : int
        - difficulty : double
        - rank : int
        - averageRating : double
        - yearPublished : int
        + BoardGame(String name, int id, int minPlayers, int maxPlayers, int minTime, int maxTime, double difficulty, int rank, double averageRating, int yearPublished)
        + getName() : String
        + getId() : int
        + getMinPlayers() : int
        + getMaxPlayers() : int
        + getMinPlayTime() : int
        + getMaxPlayTime() : int
        + getDifficulty() : double
        + getRank() : int
        + getRating() : double
        + getYearPublished() : int
        + toStringWithInfo(GameData col) : String
        + toString() : String
        + equals(Object obj) : boolean
        + hashCode() : int
        + main(String[] args) : void
    }

    class ConsoleApp {
        - IN : Scanner
        - DEFAULT_FILENAME : String
        - RND : Random
        - current : Scanner
        - gameList : IGameList
        - planner : IPlanner
        + ConsoleApp(IGameList gameList, IPlanner planner)
        + start() : void
        - randomNumber() : void
        - processHelp() : void
        - processFilter() : void
        - printFilterStream(Stream<BoardGame> games, GameData sortOn) : void
        - processListCommands() : void
        - printCurrentList() : void
        - nextCommand() : ConsoleText
        - remainder() : String
        - getInput(String format, Object...) : String
        - printOutput(String format, Object...) : void
    }

    class ConsoleText {
        <<enumeration>>
        + WELCOME
        + HELP
        + INVALID
        + GOODBYE
        + PROMPT
        + NO_FILTER
        + NO_GAMES_LIST
        + FILTERED_CLEAR
        + LIST_HELP
        + FILTER_HELP
        + INVALID_LIST
        + EASTER_EGG
        + CMD_EASTER_EGG
        + CMD_EXIT
        + CMD_HELP
        + CMD_QUESTION
        + CMD_FILTER
        + CMD_LIST
        + CMD_SHOW
        + CMD_ADD
        + CMD_REMOVE
        + CMD_CLEAR
        + CMD_SAVE
        + CMD_OPTION_ALL
        + CMD_SORT_OPTION
        + CMD_SORT_OPTION_DIRECTION_ASC
        + CMD_SORT_OPTION_DIRECTION_DESC
        + toString() : String
        + fromString(String str) : ConsoleText
    }

    class GameData {
        <<enumeration>>
        + NAME
        + ID
        + RATING
        + DIFFICULTY
        + RANK
        + MIN_PLAYERS
        + MAX_PLAYERS
        + MIN_TIME
        + MAX_TIME
        + YEAR
        - columnName : String
        - GameData(String columnName)
        + getColumnName() : String
        + fromColumnName(String columnName) : GameData
        + fromString(String name) : GameData
    }

    class GameList {
        + GameList()
        + getGameNames() : List<String>
        + clear() : void
        + count() : int
        + saveGame(String filename) : void
        + addToList(String str, Stream<BoardGame> filtered) : void
        + removeFromList(String str) : void
    }

    class GamesLoader {
        - DELIMITER$: String
        - GamesLoader()
        + loadGamesFile(String filename)$: Set<BoardGame>
        - toBoardGame(String line, Map<GameData, Integer> columnMap)$: BoardGame
        - processHeader(String header)$: Map<GameData, Integer>
    }

    class IGameList {
        <<interface>>
        + ADD_ALL : String
        + getGameNames() : List<String>
        + clear() : void
        + count() : int
        + saveGame(String filename) : void
        + addToList(String str, Stream<BoardGame> filtered) : void
        + removeFromList(String str) : void
    }

    class IPlanner {
        <<interface>>
        + filter(String filter) : Stream<BoardGame>
        + filter(String filter, GameData sortOn) : Stream<BoardGame>
        + filter(String filter, GameData sortOn, boolean ascending) : Stream<BoardGame>
        + reset() : void
    }

    class Operations {
        <<enumeration>>
        + EQUALS
        + NOT_EQUALS
        + GREATER_THAN
        + LESS_THAN
        + GREATER_THAN_EQUALS
        + LESS_THAN_EQUALS
        + CONTAINS
        - operator : String
        + getOperator() : String
        + fromOperator(String operator) : Operations
        + getOperatorFromStr(String str) : Operations
    }

    class Planner {
        + Planner(Set<BoardGame> games)
        + filter(String filter) : Stream<BoardGame>
        + filter(String filter, GameData sortOn) : Stream<BoardGame>
        + filter(String filter, GameData sortOn, boolean ascending) : Stream<BoardGame>
        + reset() : void
    }

    class Filter {
        - filterText : String
        + filterStream(String filterText) : Stream<List>
    }

    class Sort {
        - sortOn : String
        - isAscending : boolean
        + sortStream(String sortOn, boolean isAscending) : Stream<List>
    }


    IGameList <|.. GameList
    IPlanner <|.. Planner

    BGArenaPlanner ..> IPlanner : "uses"
    BGArenaPlanner ..> IGameList : "uses"
    BGArenaPlanner ..> GamesLoader : "uses"
    BGArenaPlanner ..> ConsoleApp : "uses"

    ConsoleApp ..> IGameList : "uses"
    ConsoleApp ..> IPlanner : "uses"

    IGameList ..> BoardGame : "uses"
    GamesLoader ..> BoardGame : "uses"

    Planner ..> Filter : "uses"
    Planner ..> Sort : "uses"
    Planner ..> GameData : "uses"
    Filter ..> Operations : "uses"
    Sort ..> Operations : "uses"

    GamesLoader ..> GameData : "uses"

    Planner --> BoardGame
    GameList --> BoardGame


```



## (INITIAL DESIGN): Tests to Write - Brainstorm

Write a test (in english) that you can picture for the class diagram you have created. This is the brainstorming stage in the TDD process.

> [!TIP]
> As a reminder, this is the TDD process we are following:
> 1. Figure out a number of tests by brainstorming (this step)
> 2. Write **one** test
> 3. Write **just enough** code to make that test pass
> 4. Refactor/update  as you go along
> 5. Repeat steps 2-4 until you have all the tests passing/fully built program

You should feel free to number your brainstorm.

1. GameList Tests:
    * Test getGameNames():
        * Add several games and verify that getGameNames() returns them in alphabetical order.
   * Test clear():
     * Add games, call clear(), and confirm the game count is zero.
   * Test addToList() with a specific name:
       * Add "Chess" using addToList() and verify that only "Chess" is added.
2. Planner Tests:
    * Test filter() by name equals: Call filter("name == Chess") and verify that only "Chess" is returned.




## (FINAL DESIGN): Class Diagram

Go through your completed code, and update your class diagram to reflect the final design. Make sure you check the file in the browser on github.com to make sure it is rendering correctly. It is normal that the two diagrams don't match! Rarely (though possible) is your initial design perfect.

For the final design, you just need to do a single diagram that includes both the original classes and the classes you added.

> [!WARNING]
> If you resubmit your assignment for manual grading, this is a section that often needs updating. You should double check with every resubmit to make sure it is up to date.

```mermaid
classDiagram
   
    
    class BoardGame {
        - String name
        - int id
        - int minPlayers
        - int maxPlayers
        - int minPlayTime
        - int maxPlayTime
        - double difficulty
        - int rank
        - double averageRating
        - int yearPublished
        + String getName()
        + int getId()
        + int getMinPlayers()
        + int getMaxPlayers()
        + int getMinPlayTime()
        + int getMaxPlayTime()
        + double getDifficulty()
        + int getRank()
        + double getRating()
        + int getYearPublished()
        + double getNumericValue(GameData col)
        + String getStringValue(GameData col)
        + String toStringWithInfo(GameData col)
        + boolean equals(Object obj)
        + int hashCode()
        + int compareTo(BoardGame other)
    }

    class GameData {
        <<enumeration>>
        NAME
        ID
        RATING
        DIFFICULTY
        RANK
        MIN_PLAYERS
        MAX_PLAYERS
        MIN_TIME
        MAX_TIME
        YEAR
        + String getColumnName()
        + static GameData fromColumnName(String columnName)
        + static GameData fromString(String name)
    }

    class Operations {
        <<enumeration>>
        EQUALS
        NOT_EQUALS
        GREATER_THAN
        LESS_THAN
        GREATER_THAN_EQUALS
        LESS_THAN_EQUALS
        CONTAINS
        + String getOperator()
        + static Operations fromOperator(String operator)
        + static Operations getOperatorFromStr(String str)
    }

    class IGameList {
        <<interface>>
        + List<String> getGameNames()
        + void clear()
        + int count()
        + void saveGame(String filename)
        + void addToList(String str, Stream<BoardGame> filtered)
        + void removeFromList(String str)
    }

    class GameList {
        - Set<BoardGame> games
        + GameList()
        + List<String> getGameNames()
        + void clear()
        + int count()
        + void saveGame(String filename)
        + void addToList(String str, Stream<BoardGame> filtered)
        + void removeFromList(String str)
        - void removeGameByName(String name)
    }

    class IPlanner {
        <<interface>>
        + Stream<BoardGame> filter(String filter)
        + Stream<BoardGame> filter(String filter, GameData sortOn)
        + Stream<BoardGame> filter(String filter, GameData sortOn, boolean ascending)
        + void reset()
    }

    class Planner {
        - Set<BoardGame> games
        - Set<BoardGame> curGames
        + Planner(Set<BoardGame> games)
        + Stream<BoardGame> filter(String filter)
        + Stream<BoardGame> filter(String filter, GameData sortOn)
        + Stream<BoardGame> filter(String filter, GameData sortOn, boolean ascending)
        + void reset()
        - Stream<BoardGame> applySingleFilter(Stream<BoardGame> stream, String filter)
        - boolean isNumericColumn(GameData column)
        - Stream<BoardGame> applyNumericFilter(Stream<BoardGame> stream, GameData column, Operations operator, String value)
        - Stream<BoardGame> applyStringFilter(Stream<BoardGame> stream, GameData column, Operations operator, String value)
        - Stream<BoardGame> applySorting(Stream<BoardGame> stream, GameData sortOn, boolean ascending)
    }

    class GamesLoader {
        - static final String DELIMITER
        - private GamesLoader()
        + static Set<BoardGame> loadGamesFile(String filename)
        - static BoardGame toBoardGame(String line, Map<GameData, Integer> columnMap)
        - static Map<GameData, Integer> processHeader(String header)
    }

    class ConsoleApp {
        - static final Scanner IN
        - static final String DEFAULT_FILENAME
        - static final Random RND
        - Scanner current
        - IGameList gameList
        - IPlanner planner
        + ConsoleApp(IGameList gameList, IPlanner planner)
        + void start()
        - void randomNumber()
        - void processHelp()
        - void processFilter()
        - static void printFilterStream(Stream<BoardGame> games, GameData sortON)
        - void processListCommands()
        - void printCurrentList()
        - ConsoleText nextCommand()
        - String remainder()
        - static String getInput(String format, Object... args)
        - static void printOutput(String format, Object... output)
    }

    class ConsoleText {
        <<enumeration>>
        WELCOME
        HELP
        INVALID
        GOODBYE
        PROMPT
        NO_FILTER
        NO_GAMES_LIST
        FILTERED_CLEAR
        LIST_HELP
        FILTER_HELP
        INVALID_LIST
        EASTER_EGG
        CMD_EASTER_EGG
        CMD_EXIT
        CMD_HELP
        CMD_QUESTION
        CMD_FILTER
        CMD_LIST
        CMD_SHOW
        CMD_ADD
        CMD_REMOVE
        CMD_CLEAR
        CMD_SAVE
        CMD_OPTION_ALL
        CMD_SORT_OPTION
        CMD_SORT_OPTION_DIRECTION_ASC
        CMD_SORT_OPTION_DIRECTION_DESC
        + static ConsoleText fromString(String str)
    }

 
    GameList --|> IGameList
    Planner --|> IPlanner
    Planner --> GameData
    Planner --> Operations
    Planner --> BoardGame
    GamesLoader --> BoardGame
    GamesLoader --> GameData
    ConsoleApp --> IGameList
    ConsoleApp --> IPlanner
    ConsoleApp --> ConsoleText

```



## (FINAL DESIGN): Reflection/Retrospective

> [!IMPORTANT]
> The value of reflective writing has been highly researched and documented within computer science, from learning to information to showing higher salaries in the workplace. For this next part, we encourage you to take time, and truly focus on your retrospective.

Take time to reflect on how your design has changed. Write in *prose* (i.e. do not bullet point your answers - it matters in how our brain processes the information). Make sure to include what were some major changes, and why you made them. What did you learn from this process? What would you do differently next time? What was the most challenging part of this process? For most students, it will be a paragraph or two. 

* The design evolved significantly as I refined functionality and improved class cohesion. Initially, filtering and sorting were rigid, but I introduced the Operations enum to support flexible filtering with operators like >, <, and ==. The Planner class was also refactored, separating numeric and string filtering for better maintainability. The GameList class improved error handling, especially in user input parsing. I learned that real-world constraints often require more adaptable designs. Next time, I would focus on modularity earlier. The biggest challenge was balancing flexibility with simplicity while keeping the system intuitive.