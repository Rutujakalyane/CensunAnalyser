package Adapter;

import censusanalyser.IndiaCensusDAO;
import csv.USCensusCSV;
import exception.CensusAnalyserException;

import java.util.Map;

public class USCensusAdapter extends CensusAdapter {
        @Override
        public Map<String, IndiaCensusDAO> loadCensusData(String... csvFilePath) throws CensusAnalyserException {
            return super.loadCensusData(USCensusCSV.class, csvFilePath[0]);
        }
    }

