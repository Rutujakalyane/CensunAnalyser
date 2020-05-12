package csv;

import com.opencsv.bean.CsvBindByName;

public class USCensusCSV<E> {
        @CsvBindByName(column = "State", required = true)
        public String state;

        @CsvBindByName(column = "StateId", required = true)
        public String stateId;

        @CsvBindByName(column = "Population", required = true)
        public int population;

        @CsvBindByName(column = "TotalArea", required = true)
        public double areaInSqKm;

        @CsvBindByName(column = "PopulationDensity", required = true)
        public double populationDensity;

        @Override
        public String toString() {
            return "USCensusCSV{" +
                "State='" + state + '\'' +
                "State ID='" + stateId + '\'' +
                ", Population='" + population + '\'' +
                ", TotalArea='" + areaInSqKm + '\'' +
                ", PopulationDensity='" + populationDensity + '\'' +
                '}';
    }

}
