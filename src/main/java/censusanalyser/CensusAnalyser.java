package censusanalyser;

import Adapter.CensusAdapterFactory;
import exception.CensusAnalyserException;
import com.google.gson.Gson;

import java.util.*;
import java.util.stream.Collectors;

public class CensusAnalyser <E> {

    List<IndiaCensusDAO> censusList = null;
    Map<String, IndiaCensusDAO> censusDAOMap = null;
    private Country country;

    //CONSTRUCTOR
    public CensusAnalyser(Country country) {
        this.country = country;
    }

    //ENUM FOR Sorting MODE
    public enum SortingMode {STATE, POPULATION, DENSITY, AREA}

    //ENUM FOR COUNTRY
    public enum Country {INDIA, US}

    public int loadCensusData(Country country, String... csvFilePath) throws CensusAnalyserException {
        censusDAOMap = CensusAdapterFactory.loadCensusData(country, csvFilePath);
        censusList = censusDAOMap.values().stream().collect(Collectors.toList());
        return censusDAOMap.size();
    }

    //METHOD TO SORT CENSUS DATA BY STATE
    public String SortedStateCensusData(SortingMode mode) throws CensusAnalyserException {
        if (censusList == null || censusList.size() == 0) {
            throw new CensusAnalyserException("No census data",CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        ArrayList arrayList = censusDAOMap.values().stream()
                .sorted(IndiaCensusDAO.getSortComparator(mode))
                .map(censusDAO -> censusDAO.getCensusDTO(country))
                .collect(Collectors.toCollection(ArrayList::new));
        return new Gson().toJson(arrayList);

    }
}
