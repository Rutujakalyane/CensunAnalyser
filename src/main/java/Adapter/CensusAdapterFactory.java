package Adapter;

import censusanalyser.CensusAnalyser;
import censusanalyser.IndiaCensusDAO;
import exception.CensusAnalyserException;

import java.util.Map;

public class CensusAdapterFactory {
        public static Map<String, IndiaCensusDAO> loadCensusData(CensusAnalyser.Country country, String[] csvFilePath) throws CensusAnalyserException {
            if (country.equals(CensusAnalyser.Country.INDIA))
                return new IndianCensusAdapter().loadCensusData(csvFilePath);
            else if (country.equals(CensusAnalyser.Country.US))
                return new USCensusAdapter().loadCensusData(csvFilePath);
            else
                throw new CensusAnalyserException( "Invalid country", CensusAnalyserException.ExceptionType.INVALID_COUNTRY);
        }
    }


