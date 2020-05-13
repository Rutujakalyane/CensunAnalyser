package csv;

import com.opencsv.bean.CsvBindByName;

public class USCensusCSV {
        @CsvBindByName(column = "State", required = true)
        public String state;

        @CsvBindByName(column = "StateId", required = true)
        public String stateId;

        @CsvBindByName(column = "Population", required = true)
        public int population;

        @CsvBindByName(column = "TotalArea", required = true)
        public double totalArea;

        @CsvBindByName(column = "PopulationDensity", required = true)
        public double density;

     public USCensusCSV(String state, String stateId, int population, double density, double area) {
        this.state = state;
        this.population = population;
        this.stateId = stateId;
        this.density = density;
        this.totalArea = area;
    }

    @Override
    public String toString() {
        return "USCensusCSV{" +
                "State='" + state + '\'' +
                "State ID='" + stateId + '\'' +
                ", Population='" + population + '\'' +
                ", TotalArea='" + totalArea + '\'' +
                ", PopulationDensity='" + density + '\'' +
                '}';
    }
}
