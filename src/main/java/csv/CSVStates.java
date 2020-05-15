package csv;

import com.opencsv.bean.CsvBindByName;

public class CSVStates {
    @CsvBindByName(column = "State Name", required = true)
    public String StateName;

    @CsvBindByName(column = "StateCode", required = true)
    public String StateCode;

    public CSVStates( String stateName, String stateCode) {
        this.StateName = stateName;
        this.StateCode = stateCode;

    }
    @Override
    public String toString() {
        return "CSVStates{" +
                "StateName='" + StateName + '\'' +
                ", StateCode='" + StateCode + '\'' +
                '}';
    }


}
