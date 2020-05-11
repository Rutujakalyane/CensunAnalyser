package exception;

public class CensusAnalyserException extends Exception {

    public enum ExceptionType {
        CENSUS_FILE_PROBLEM,WRONG_DELIMITER,WRONG_HEADER_PROBLEM,NO_CENSUS_DATA;
    }

    public ExceptionType type;

   /* public CensusAnalyserException(String message, String name) {
        super(message);
        this.type = ExceptionType.valueOf(name);
    }*/

    public CensusAnalyserException(String message, ExceptionType type) {
        super(message);
        this.type = type;
    }

    public CensusAnalyserException(String message, ExceptionType type, Throwable cause) {
        super(message, cause);
        this.type = type;
    }
}
