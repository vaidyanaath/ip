package duke.exception;

public class DukeInvalidCommandException extends DukeException{
    public DukeInvalidCommandException(String errorMessage) {
        super(errorMessage);
    }
}