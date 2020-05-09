package censusanalyser;

public class IndiaCensusDAO {
    public String state;
    public int areaInSqKm;
    public int densityPerSqKm;
    public int population;
    public String stateCode;
    public String sortCode;
    public IndiaCensusDAO(IndiaCensusCSV indiaCensusCSV) {
        state = indiaCensusCSV.state;
        areaInSqKm = indiaCensusCSV.areaInSqKm;
        densityPerSqKm = indiaCensusCSV.densityPerSqKm;
        population = indiaCensusCSV.population;
        sortCode=state;
    }
    public IndiaCensusDAO(CSVStates indiaState) {
        state = indiaState.state;
        stateCode = indiaState.stateCode;
        sortCode=stateCode;
    }


}
