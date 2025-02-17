package duke.parser;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import duke.exception.DukeException;
import duke.exception.DukeInvalidArgumentException;
import duke.exception.DukeInvalidCommandException;
import duke.storage.Storage;
import duke.task.TaskList;


/**
 * Represents a parser that reads user commands and processes them.
 */
public class Parser {
    /**
     * Returns a LocalDateTime object representing the date and time specified.
     *
     * @param dateTime The string containing the date and time.
     * @return A LocalDateTime object.
     * @throws DukeException If the given {@code dateTime} is in an invalid format
     *     according to the specified pattern.
     */
    public static LocalDateTime parseDateTime(String dateTime) throws DukeException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

        try {
            return LocalDateTime.parse(dateTime, formatter);
        } catch (DateTimeParseException e) {
            throw new DukeInvalidArgumentException("Please enter a valid datetime: dd-MM-yyyy HH:mm");
        }
    }

    /**
     * Returns the response after processing the given user command
     * and performing actions on the TaskList.
     *
     * @param userCommand The user command.
     * @param storage The storage object to store the updated {@code TaskList}.
     * @param tasks The {@code TaskList} to perform actions on.
     * @throws DukeException If the {@code userCommand} is invalid or an error occurs
     *     while processing the command.
     */
    public static String processCommand(String userCommand, Storage storage, TaskList tasks) throws DukeException {

        assert userCommand != null : "userCommand parameter cannot be null";
        assert storage != null : "storage parameter cannot be null";
        assert tasks != null : "tasks parameter cannot be null";

        if (userCommand.isEmpty()) {
            throw new DukeInvalidCommandException("Please enter a command");
        }

        String command = userCommand.split(" ", 2)[0];
        String response;

        // Single word commands
        if (userCommand.equals("list")) {
            return tasks.printTaskList();
        } else if (userCommand.equals("sort")) {
            return tasks.sortByDeadline();
        }

        // Multi-word commands
        switch (command) {
        case "todo":
            response = tasks.addToDo(userCommand);
            break;
        case "deadline":
            response = tasks.addDeadline(userCommand);
            break;
        case "event":
            response = tasks.addEvent(userCommand);
            break;
        case "mark":
            response = tasks.markTask(userCommand);
            break;
        case "unmark":
            response = tasks.unmarkTask(userCommand);
            break;
        case "delete":
            response = tasks.deleteTask(userCommand);
            break;
        case "find":
            response = tasks.findTask(userCommand);
            break;
        default:
            throw new DukeInvalidCommandException("beep...boop... unrecognized command!");
        }

        assert response != null && !response.isEmpty() : "Response can't be empty";
        storage.storeTaskList(tasks.getTaskList());
        return response;
    }
}
