package censusanalyser;

import csv.IndiaCensusCSV;
import csv.USCensusCSV;
import exception.CensusAnalyserException;

import java.util.Map;

public class CensusAdapterFactory {


    public static Map<String, IndiaCensusDAO> loadCensusData(Class <E>,CensusAnalyser.Country country, String[] csvFilePath) throws CensusAnalyserException {
        if (country.equals(CensusAnalyser.Country.INDIA)) {
            return loadCensusData(IndiaCensusCSV.class, csvFilePath);
        } else if (country.equals(CensusAnalyser.Country.US))
            return loadCensusData(USCensusCSV.class, csvFilePath);
        else throw new CensusAnalyserException("Incorrect country", CensusAnalyserException.ExceptionType.INVALID_COUNTRY);
    }
}


