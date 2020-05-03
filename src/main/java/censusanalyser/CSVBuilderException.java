package censusanalyser;

public class CSVBuilderException extends RuntimeException {
    enum ExceptionType {
        CENSUS_FILE_PROBLEM, WRONG_DELIMITER, WRONG_HEADER_PROBLEM;
    }
    CSVBuilderException.ExceptionType type;

    public CSVBuilderException(String message, CSVBuilderException.ExceptionType type) {
        super(message);
        this.type = type;
    }
}

