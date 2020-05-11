package exception;


public class CSVBuilderException extends RuntimeException {
    public enum ExceptionType {
         WRONG_DELIMITER, WRONG_HEADER_PROBLEM,CENSUS_FILE_PROBLEM;
    }
    ExceptionType type;

    public CSVBuilderException(String message, CSVBuilderException.ExceptionType type) {
        super(message);
        this.type = type;
    }
}

