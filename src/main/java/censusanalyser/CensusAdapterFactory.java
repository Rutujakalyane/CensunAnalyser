package censusanalyser;

import csv.IndiaCensusCSV;
import csv.USCensusCSV;
import exception.CensusAnalyserException;

import java.util.Map;

public class CensusAdapterFactory {
    public <E> Map<String, IndiaCensusDAO> loadCensusData(Class<E> country, String... csvFilePath) throws CensusAnalyserException {
        if (country.equals(CensusAnalyser.Country.INDIA))
            return this.loadCensusData(IndiaCensusCSV.class, csvFilePath);
        else if (country.equals(CensusAnalyser.Country.US))
            return this.loadCensusData(USCensusCSV.class, csvFilePath);
        else throw new CensusAnalyserException("Incorrect country", CensusAnalyserException.ExceptionType.INVALID_COUNTRY);
    }
}
