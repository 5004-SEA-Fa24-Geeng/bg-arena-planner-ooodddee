# Report

Submitted report to be manually graded. We encourage you to review the report as you read through the provided
code as it is meant to help you understand some of the concepts.

## Technical Questions

1. What is the difference between == and .equals in java? Provide a code example of each, where they would return different results for an object. Include the code snippet using the hash marks (```) to create a code block.
   
   * `==` checks if two references point to the exact same object, while `.equals()` compares if their values are equal.

   ```java
   Integer num1 = new Integer(1000);
   Integer num2 = new Integer(1000);

   System.out.println(num1 == num2);       // false: different objects in memory
   System.out.println(num1.equals(num2));  // true: same integer value    
   ```
   

2. Logical sorting can be difficult when talking about case. For example, should "apple" come before "Banana" or after? How would you sort a list of strings in a case-insensitive manner?

   * To sort strings case-insensitively, compare their lowercase/uppercase versions while preserving original casing in the result.




3. In our version of the solution, we had the following code (snippet)
    ```java
    public static Operations getOperatorFromStr(String str) {
        if (str.contains(">=")) {
            return Operations.GREATER_THAN_EQUALS;
        } else if (str.contains("<=")) {
            return Operations.LESS_THAN_EQUALS;
        } else if (str.contains(">")) {
            return Operations.GREATER_THAN;
        } else if (str.contains("<")) {
            return Operations.LESS_THAN;
        } else if (str.contains("=="))...
    ```
   Why would the order in which we checked matter (if it does matter)? Provide examples either way proving your point.
      * Order matters because longer operators (like >=, <=, ==) must be checked before shorter ones (>, <, =). Otherwise, shorter operators will incorrectly match parts of longer ones.
        
      * Example: Checking > before >= would parse ">=2" as > (wrong), missing the >=.


4. What is the difference between a List and a Set in Java? When would you use one over the other?
   * List: Ordered, allows duplicates, index access. 
   * Set: Unique elements, usually no order, faster contains(). 
   * Use List for ordered data with duplicates; Set for uniqueness/checks.



5. In [GamesLoader.java](src/main/java/student/GamesLoader.java), we use a Map to help figure out the columns. What is a map? Why would we use a Map here?
   * A Map is a collection of key-value pairs, where each unique key maps to a specific value. In GamesLoader.java, a Map is used to store column names with their corresponding indexes, making it efficient to look up and retrieve data based on the column name



6. [GameData.java](src/main/java/student/GameData.java) is actually an `enum` with special properties we added to help with column name mappings. What is an `enum` in Java? Why would we use it for this application?
   * An enum in Java is a special type used to define a fixed set of constants. Using GameData as an enum helps avoid magic strings by defining all column names in one place, which improves readability, consistency, and reduces errors when sorting and filtering data.






7. Rewrite the following as an if else statement inside the empty code block.
    ```java
    switch (ct) {
                case CMD_QUESTION: // same as help
                case CMD_HELP:
                    processHelp();
                    break;
                case INVALID:
                default:
                    CONSOLE.printf("%s%n", ConsoleText.INVALID);
            }
    ``` 

    ```java
    // your code here, don't forget the class name that is dropped in the switch block..
      if (ct == CMD_QUESTION || ct == CMD_HELP)
      processHelp();
      } else {
      CONSOLE.printf("%s%n", ConsoleText.INVALID);
      }
    ```

## Deeper Thinking

ConsoleApp.java uses a .properties file that contains all the strings
that are displayed to the client. This is a common pattern in software development
as it can help localize the application for different languages. You can see this
talked about here on [Java Localization – Formatting Messages](https://www.baeldung.com/java-localization-messages-formatting).

Take time to look through the console.properties file, and change some of the messages to
another language (probably the welcome message is easier). It could even be a made up language and for this - and only this - alright to use a translator. See how the main program changes, but there are still limitations in
the current layout.

Post a copy of the run with the updated languages below this. Use three back ticks (```) to create a code block.

```text
// your consoles output here
欢迎使用BG Arena游戏管理器！
请输入命令：
帮助：您可以输入以下命令：help, filter, list, exit。
感谢使用，再见！
```

Now, thinking about localization - we have the question of why does it matter? The obvious
one is more about market share, but there may be other reasons.  I encourage
you to take time researching localization and the importance of having programs
flexible enough to be localized to different languages and cultures. Maybe pull up data on the
various spoken languages around the world? What about areas with internet access - do they match? Just some ideas to get you started. Another question you are welcome to talk about - what are the dangers of trying to localize your program and doing it wrong? Can you find any examples of that? Business marketing classes love to point out an example of a car name in Mexico that meant something very different in Spanish than it did in English - however [Snopes has shown that is a false tale](https://www.snopes.com/fact-check/chevrolet-nova-name-spanish/).  As a developer, what are some things you can do to reduce 'hick ups' when expanding your program to other languages?


As a reminder, deeper thinking questions are meant to require some research and to be answered in a paragraph for with references. The goal is to open up some of the discussion topics in CS, so you are better informed going into industry. 

   * Localization is important because it allows software to reach a wider audience and improves user experience by making it accessible in their native language. It also shows respect for cultural differences, which can build trust and loyalty. However, doing it wrong can lead to misunderstandings or offense, damaging a brand's reputation. For example, poor translations or culturally insensitive content can alienate users. To avoid these issues, developers should collaborate with native speakers, use reliable translation tools, and test thoroughly. Proper localization ensures software is inclusive and effective for global users.