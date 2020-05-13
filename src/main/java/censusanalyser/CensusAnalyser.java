package censusanalyser;

import constants.filepaths;
import exception.CSVBuilderException;
import exception.CensusAnalyserException;
import censusanalyser.IndiaCensusDAO;
import com.google.gson.Gson;
import csv.CSVStates;
import csv.IndiaCensusCSV;
import csv.USCensusCSV;
import jdk.nashorn.api.scripting.ScriptObjectMirror;

import java.util.*;
import java.util.stream.Collectors;

public class CensusAnalyser implements filepaths {
    public static final Country INDIA = ;
    public static final Country US = ;
    static List<IndiaCensusDAO> censusList = null;
    Map<String, IndiaCensusDAO> censusDAOMap = null;
    private Country country;

    //CONSTRUCTOR
    public CensusAnalyser(Country country) {
        this.country = country;
    }

    public int loadCensusData(Country country, String... csvFilePath) throws CensusAnalyserException {
        censusDAOMap = CensusAdapterFactory.loadCensusData(country, csvFilePath);
        censusList = (List<IndiaCensusDAO>) censusDAOMap.values().stream().collect(Collectors.toList());
        return censusDAOMap.size();
    }


    //METHOD TO SORT CENSUS DATA BY STATE
    public String SortedCensusData(SortingMode mode) throws CensusAnalyserException {
        if (censusList == null || censusList.size() == 0) {
            throw new CensusAnalyserException("No census data", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        ArrayList arrayList = censusDAOMap.values().stream()
                .sorted(IndiaCensusDAO.getSortComparator(mode))
                .map(IndiaCensusDAO -> IndiaCensusDAO.getCensusDTO(country))
                .collect(Collectors.toCollection(ArrayList::new));
        return new Gson().toJson(arrayList);

    }

    //METHOD TO SORT STATE CENSUS DATA BY POPULATION
    public static String getPopulationWiseSortedCensusData() throws CensusAnalyserException {
        if (censusList == null || censusList.size() == 0) {
            throw new CensusAnalyserException("No census data", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<IndiaCensusDAO> censusComparator = Comparator.comparing(IndiaCensusDAO -> IndiaCensusDAO.population);
        sortData(censusComparator);
        Collections.reverse(censusList);
        String sortedStateCensusJson = new Gson().toJson(censusList);
        return sortedStateCensusJson;
    }

    //METHOD TO SORT CSV DATA
    private static void sortData(Comparator<IndiaCensusDAO> csvComparator) {
        for (int i = 0; i < censusList.size() - 1; i++) {
            for (int j = 0; j < censusList.size() - i - 1; j++) {
                IndiaCensusDAO census1 = censusList.get(j);
                IndiaCensusDAO census2 = censusList.get(j + 1);
                if (csvComparator.compare(census1, census2) > 0) {
                    censusList.set(j, census2);
                    censusList.set(j + 1, census1);
                }
            }
        }
    }



    //ENUM FOR Sorting MODE
    public enum SortingMode {STATE, POPULATION, DENSITY, AREA}

    //ENUM FOR COUNTRY
    public enum Country {INDIA, US}
}

    /*//ENUM FOR Sorting MODE
    public enum SortingMode {STATE, POPULATION, DENSITY, AREA}

    //ENUM FOR COUNTRY
    public enum Country {INDIA, US}
    public Country country;
    public CensusAnalyser(Country country) {
        this.country=country;
    }


    public CensusAnalyser() {

    }
    public int loadCensusData(Country country, String... csvFilePath) throws CensusAnalyserException {
        censusCSVMap = censusanalyser.CensusAdapterFactory.loadCensusData( country,csvFilePath );
        return censusCSVMap.size();
    }

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
}*/