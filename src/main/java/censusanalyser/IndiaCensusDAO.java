package censusanalyser;


import csv.CSVStates;
import csv.IndiaCensusCSV;
import csv.USCensusCSV;

import java.util.Comparator;

public class IndiaCensusDAO {
    public int density;
    public int area;
    public String stateId;
    public String stateCode;
    public int population;
    public int densityPerSqKm;
    public int areaInSqKm;
    public String state;

    public IndiaCensusDAO(IndiaCensusCSV indiaCensusCSV) {
        this.state = indiaCensusCSV.state;
        this.areaInSqKm = indiaCensusCSV.areaInSqKm;
        this.densityPerSqKm = indiaCensusCSV.densityPerSqKm;
        this.population = indiaCensusCSV.population;
    }

  /*  public IndiaCensusDAO(CSVStates csvStates) {
        this.state = csvStates.StateName;
        this.stateCode = csvStates.StateCode;
    }*/

    public IndiaCensusDAO(USCensusCSV census) {
        this.state = census.state;
        this.stateId = census.stateId;
        this.area = census.totalArea;
        this.density = census.density;
    }

    public static Comparator<IndiaCensusDAO> getSortComparator(CensusAnalyser.SortingMode mode) {
        if (mode.equals(CensusAnalyser.SortingMode.STATE))
            return Comparator.comparing(census -> census.state);
        if (mode.equals(CensusAnalyser.SortingMode.POPULATION))
            return Comparator.comparing(IndiaCensusDAO::getPopulation).reversed();
        if (mode.equals(CensusAnalyser.SortingMode.AREA))
            return Comparator.comparing(IndiaCensusDAO::getAreaInSqKm).reversed();
        if (mode.equals(CensusAnalyser.SortingMode.DENSITY))
            return Comparator.comparing(IndiaCensusDAO::getDensityPerSqkm).reversed();
        return null;
    }

    public int getPopulation()
    {
        return population;
    }

    public int getAreaInSqKm()
    {
        return  areaInSqKm;
    }

    public int getDensityPerSqkm()
    {
        return  densityPerSqKm;
    }
    public Object getCensusDTO(CensusAnalyser.Country country) {
        if (country.equals(CensusAnalyser.Country.INDIA))
            return new IndiaCensusCSV(state, population, areaInSqKm, densityPerSqKm);
        if (country.equals(CensusAnalyser.Country.US))
            return new USCensusCSV(state, stateId, population, density, area);
        return null;
    }

}
