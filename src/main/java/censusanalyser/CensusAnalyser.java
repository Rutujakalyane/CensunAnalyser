package censusanalyser;


import exception.CSVBuilderException;
import exception.CensusAnalyserException;
import censusanalyser.IndiaCensusDAO;
import com.google.gson.Gson;
import csv.CSVStates;
import csv.IndiaCensusCSV;
import csv.USCensusCSV;

import java.util.*;

public class CensusAnalyser {
    Map<String, IndiaCensusDAO> censusCSVMap = null;
    List<IndiaCensusDAO> censusCSVList = null;
    List<IndiaCensusDAO> stateCSVList = null;


    public int loadIndiaCensusData(String... csvFilePath) throws CensusAnalyserException {
        censusCSVMap = new CensusLoader().loadCensusData(IndiaCensusCSV.class, csvFilePath);
        return censusCSVMap.size();
    }

    public int loadUSCensusData(String... csvFilePath) throws CensusAnalyserException {
        censusCSVMap = new CensusLoader().loadCensusData(USCensusCSV.class, csvFilePath);
        return censusCSVMap.size();
    }


        public void isNull(List list) throws CensusAnalyserException {
            if (list == null || list.size() == 0) {
                throw new CensusAnalyserException("No Census Data", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
            }
        }
        public String json(List list) {
            String json = new Gson().toJson(list);
            return json;
        }
        public String getStateWiseSortedCensusData() throws CensusAnalyserException {
            isNull(censusCSVList);
            Comparator<IndiaCensusDAO> comparing = Comparator.comparing(census -> census.state);
            this.sort(comparing,censusCSVList);
            return json(censusCSVList);
        }
        public String getStateCodeWiseSortedCensusData() throws CensusAnalyserException {
            isNull(stateCSVList);
            Comparator<IndiaCensusDAO> comparing = Comparator.comparing(census -> census.stateCode);
            this.sort(comparing,stateCSVList);
            return json(stateCSVList);
        }
        public String getPopulationWiseSortedCensusData() throws CensusAnalyserException {
            isNull(censusCSVList);
            Comparator<IndiaCensusDAO> comparing = Comparator.comparing(census -> census.population);
            this.sortDescending(comparing,censusCSVList);
            return json(censusCSVList);
        }
        public String getPopulationDensityWiseSortedCensusData() throws CensusAnalyserException {
            isNull(censusCSVList);
            Comparator<IndiaCensusDAO> comparing = Comparator.comparing(census -> census.densityPerSqKm);
            this.sortDescending(comparing,censusCSVList);
            return json(censusCSVList);
        }
        public String getAreaWiseSortedCensusData() throws CensusAnalyserException {
            isNull(censusCSVList);
            Comparator<IndiaCensusDAO> comparing = Comparator.comparing(census -> census.areaInSqKm);
            this.sortDescending(comparing,censusCSVList);
            return json(censusCSVList);
        }
        private void sortDescending(Comparator comparing, List list) {
            for (int i = 0; i < this.censusCSVList.size() - 1; i++) {
                for (int j = 0; j < this.censusCSVList.size() - i - 1; j++) {
                    IndiaCensusDAO census1 = this.censusCSVList.get(j);
                    IndiaCensusDAO census2 = this.censusCSVList.get(j + 1);
                    if (comparing.compare(census1, census2) < 0) {
                        this.censusCSVList.set(j, census2);
                        this.censusCSVList.set(j + 1, census1);
                    }
                }
            }
        }
        private void sort(Comparator comparing, List stateCSVList) {
            for (int i = 0; i < stateCSVList.size() - 1; i++) {
                for (int j = 0; j < stateCSVList.size() - i - 1; j++) {
                    IndiaCensusDAO census1 = this.stateCSVList.get(j);
                    IndiaCensusDAO census2 = this.stateCSVList.get(j + 1);
                    if (comparing.compare(census1, census2) > 0) {
                        stateCSVList.set(j, census2);
                        stateCSVList.set(j + 1, census1);
                    }
                }
            }
        }

    }


