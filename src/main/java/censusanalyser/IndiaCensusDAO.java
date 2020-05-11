package censusanalyser;
import csv.CSVStates;
import csv.IndiaCensusCSV;
import csv.USCensusCSV;


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
        this.population=census.population;
        this.density=census.populationDensity;
    }

}
