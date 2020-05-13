package censusanalyser;


import csv.CSVStates;
import csv.IndiaCensusCSV;
import csv.USCensusCSV;

import java.util.Comparator;

public class IndiaCensusDAO {
    public double density;
    public double area;
    public String stateId;
    public String stateCode;
    public int population;
    public double densityPerSqKm;
    public double areaInSqKm;
    public String state;

    public IndiaCensusDAO(IndiaCensusCSV indiaCensusCSV) {
        state = indiaCensusCSV.state;
        areaInSqKm = indiaCensusCSV.areaInSqKm;
        densityPerSqKm = indiaCensusCSV.densityPerSqKm;
        population = indiaCensusCSV.population;
    }

    public IndiaCensusDAO(CSVStates indiaCensusCSV ) {
        state=indiaCensusCSV.state;
        stateCode=indiaCensusCSV.stateCode;
    }

    public IndiaCensusDAO(USCensusCSV census) {
        this.state = census.state;
        this.stateId = census.stateId;
        this.area=census.totalArea;
        this.density=census.density;
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

    public long getPopulation() {
        return population;
    }

    public long getAreaInSqKm() {
        return (long) areaInSqKm;
    }

    public long getDensityPerSqkm() {
        return (long) densityPerSqKm;
    }
    public Object getCensusDTO(CensusAnalyser.Country country) {
        if (country.equals(CensusAnalyser.Country.INDIA))
            return new IndiaCensusCSV(state, population, areaInSqKm, densityPerSqKm);
        if (country.equals(CensusAnalyser.Country.US))
            return new USCensusCSV(state, stateId, population, density, area);
        return null;
    }

}
