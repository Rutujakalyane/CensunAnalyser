package censusanalyser;

import exception.CSVBuilderException;
import exception.CensusAnalyserException;
import censusanalyser.IndiaCensusDAO;
import com.google.gson.Gson;
import csv.CSVStates;
import csv.IndiaCensusCSV;
import csv.USCensusCSV;
import jdk.nashorn.api.scripting.ScriptObjectMirror;

import java.util.*;

public class CensusAnalyser {

    public enum Country {INDIA, US}

    Map<String, IndiaCensusDAO> censusCSVMap = null;
    List<IndiaCensusDAO> censusCSVList = null;
    List<IndiaCensusDAO> stateCSVList = null;

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
        this.sort(comparing, censusCSVList);
        return json(censusCSVList);
    }

    public String getStateCodeWiseSortedCensusData() throws CensusAnalyserException {
        isNull(stateCSVList);
        Comparator<IndiaCensusDAO> comparing = Comparator.comparing(census -> census.stateCode);
        this.sort(comparing, stateCSVList);
        return json(stateCSVList);
    }

    public String getPopulationWiseSortedCensusData() throws CensusAnalyserException {
        isNull(censusCSVList);
        Comparator<IndiaCensusDAO> comparing = Comparator.comparing(census -> census.population);
        this.sort(comparing, censusCSVList);
        return json(censusCSVList);
    }

    public String getPopulationDensityWiseSortedCensusData() throws CensusAnalyserException {
        isNull(censusCSVList);
        Comparator<IndiaCensusDAO> comparing = Comparator.comparing(census -> census.densityPerSqKm);
        this.sort(comparing, censusCSVList);
        return json(censusCSVList);
    }

    public String getAreaWiseSortedCensusData() throws CensusAnalyserException {
        isNull(censusCSVList);
        Comparator<IndiaCensusDAO> comparing = Comparator.comparing(census -> census.areaInSqKm);
        this.sort(comparing, censusCSVList);
        return json(censusCSVList);
    }

    private void sort(Comparator comparing, List list) {
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = 0; j < list.size() - i - 1; j++) {
                IndiaCensusDAO census1 = (IndiaCensusDAO) list.get(j);
                IndiaCensusDAO census2 = (IndiaCensusDAO) list.get(j + 1);
                    if (comparing.compare(census1, census2) < 0) {
                    list.set(j, census2);
                    list.set(j + 1, census1);
                } else {
                    (comparing.compare(census1, census2) >0)
                    {
                        list.set(j, census2);
                        list.set(j + 1, census1);
                    }
                }
            }
        }
    }
}