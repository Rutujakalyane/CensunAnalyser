package exception;

public class CensusAnalyserException extends Exception {

    public enum ExceptionType {
        CENSUS_FILE_PROBLEM,WRONG_DELIMITER,WRONG_HEADER_PROBLEM,NO_CENSUS_DATA,INVALID_COUNTRY;
    }

    public ExceptionType type;

    public CensusAnalyserException(String message, ExceptionType type) {
        super(message);
        this.type = type;
    }

}
