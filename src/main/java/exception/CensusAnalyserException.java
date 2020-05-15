package exception;

public class CensusAnalyserException extends Exception {
    public ExceptionType type;

    //CONSTRUCTOR
    public CensusAnalyserException(String message, ExceptionType type) {
        super(message);
        this.type = type;
    }

    //ENUM CLASS
    public enum ExceptionType {
       WRONG_DELIMITER,WRONG_HEADER_PROBLEM,NO_CENSUS_DATA,INVALID_COUNTRY;
    }
}
