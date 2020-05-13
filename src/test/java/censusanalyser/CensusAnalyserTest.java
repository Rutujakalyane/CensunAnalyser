package censusanalyser;

import com.google.gson.Gson;
import csv.CSVStates;
import exception.CensusAnalyserException;
import org.junit.Assert;
import org.junit.Test;

import static censusanalyser.CensusAnalyser.Country.INDIA;
import static censusanalyser.CensusAnalyser.Country.US;
public class CensusAnalyserTest {

    CensusAnalyser indianCensusAnalyzer = new CensusAnalyser(CensusAnalyser.INDIA);
    CensusAnalyser usCensusAnalyzer = new CensusAnalyser(CensusAnalyser.US);

    @Test
    public void givenNumberOfRecords_WhenMatched_ShouldReturnTrue() {
        String INDIA_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
        try {
            int numberOfRecords = indianCensusAnalyzer.loadCensusData(INDIA,INDIA_CENSUS_CSV_FILE_PATH);
            Assert.assertEquals(29, numberOfRecords);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenFileName_WhenWrong_ShouldReturnCustomiseException() {
        String INDIA_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
        try {
            indianCensusAnalyzer.loadCensusData(INDIA, INDIA_CENSUS_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }

    @Test
    public void givenFileType_WhenWrong_ShouldReturnCustomiseException() {
        String WRONG_CENSUS_CSV_FILE_TYPE = "./src/test/resources/IndiaStateCensusData.doc";
        try {
            indianCensusAnalyzer.loadCensusData(INDIA, WRONG_CENSUS_CSV_FILE_TYPE);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }

    @Test
    public void givenFile_WhenDelimiterIncorrect_ShouldReturnCustomiseException() {
        String WRONG_CENSUS_DELIMITER =  "./src/test/resources/WrongCensusDelimiter.csv";
        try {
            indianCensusAnalyzer.loadCensusData(INDIA, WRONG_CENSUS_DELIMITER);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.WRONG_DELIMITER, e.type);
        }
    }

    @Test
    public void givenFile_WhenHeaderIncorrect_ShouldReturnCustomiseException() {
        String INDIA_CENSUS_WRONG_HEADER_PATH ="./src/test/resources/WrongCensusHeader.csv";
        try {
            indianCensusAnalyzer.loadCensusData(INDIA, INDIA_CENSUS_WRONG_HEADER_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.WRONG_HEADER_PROBLEM, e.type);
        }
    }
    @Test
    public void givenNumberOfRecordsOfStateCode_WhenMatched_ShouldReturnTrue() {
        String INDIA_STATE_CODE_CSV_FILE_PATH = "./src/test/resources/IndiaStateCode.csv";
        try {
            int numberOfRecords = indianCensusAnalyzer.loadCensusData(INDIA, INDIA_STATE_CODE_CSV_FILE_PATH);
            Assert.assertEquals(37, numberOfRecords);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenFileNameOfStateCode_WhenWrong_ShouldReturnCustomiseException() {
        String INDIA_STATE_CODE_CSV_FILE_PATH = "./src/test/resources/IndiaStateCode.csv";
        try {
            indianCensusAnalyzer.loadCensusData(INDIA, INDIA_STATE_CODE_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }

    @Test
    public void givenFileTypeOfStateCode_WhenWrong_ShouldReturnCustomiseException() {
        String WRONG_STATE_CODE_CSV_FILE_TYPE = "./src/test/resources/IndiaStateCode.doc";
        try {
            indianCensusAnalyzer.loadCensusData(INDIA, WRONG_STATE_CODE_CSV_FILE_TYPE);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }

    @Test
    public void givenFileOfStateCode_WhenDelimiterIncorrect_ShouldReturnCustomiseException() {
        String WRONG_STATE_CODE_DELIMITER =  "./src/test/resources/WrongStateCodeDelimiter.csv";
        try {
            indianCensusAnalyzer.loadCensusData(INDIA, WRONG_STATE_CODE_DELIMITER);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.WRONG_DELIMITER, e.type);
        }
    }

    @Test
    public void givenFileOfStateCode_WhenHeadersIncorrect_ShouldReturnCustomiseException() {
        String INDIA_STATE_CODE_WRONG_HEADER_PATH = "./src/test/resources/WrongCSVHeader.csv";
        try {
            indianCensusAnalyzer.loadCensusData(INDIA, INDIA_STATE_CODE_WRONG_HEADER_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.WRONG_HEADER_PROBLEM, e.type);
        }
    }

    @Test
    public void givenCensusData_WhenSorted_ShouldReturnSortedList() {
        String INDIA_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateStateData.csv";
        try {
            indianCensusAnalyzer.loadCensusData(INDIA, INDIA_CENSUS_CSV_FILE_PATH);
            String sortedCensusData = indianCensusAnalyzer.SortedCensusData(CensusAnalyser.SortingMode.STATE);
            IndiaCensusDAO[] csvStatesCensus = new Gson().fromJson(sortedCensusData, IndiaCensusDAO[].class);
            Assert.assertEquals("Andhra Pradesh", csvStatesCensus[0].state);
        } catch (CensusAnalyserException e) {
            e.getStackTrace();
        }
    }

    @Test
    public void givenTheStateCensusData_WhenSortedOnPopulation_ShouldReturnSortedResult() {
        String INDIA_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
        try {
            indianCensusAnalyzer.loadCensusData(INDIA, INDIA_CENSUS_CSV_FILE_PATH);
            String sortedCensusData = indianCensusAnalyzer.SortedCensusData(CensusAnalyser.SortingMode.POPULATION);
            IndiaCensusDAO[] csvStatesCensus = new Gson().fromJson(sortedCensusData, IndiaCensusDAO[].class);
            Assert.assertEquals("Uttar Pradesh", csvStatesCensus[0].population);
        } catch (CensusAnalyserException e) {
            e.getStackTrace();
        }
    }

    @Test
    public void givenTheStateCensusData_WhenSortedOnDensityPerSqKm_ShouldReturnSortedResult() {
        final String INDIA_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
        try {
            indianCensusAnalyzer.loadCensusData(INDIA, INDIA_CENSUS_CSV_FILE_PATH);
            String sortedCensusData = indianCensusAnalyzer.SortedCensusData(CensusAnalyser.SortingMode.DENSITY);
            IndiaCensusDAO[] csvStateCensuses = new Gson().fromJson(sortedCensusData, IndiaCensusDAO[].class);
            Assert.assertEquals("Rajasthan", csvStateCensuses[0].density);
        } catch (CensusAnalyserException e) {
            e.getStackTrace();
        }
    }

    @Test
    public void givenTheStateCensusData_WhenSortedOnAreaInPerSqKm_ShouldReturnSortedResult() {
        final String INDIA_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
        try {
            indianCensusAnalyzer.loadCensusData(INDIA, INDIA_CENSUS_CSV_FILE_PATH);
            String sortedCensusData = indianCensusAnalyzer.SortedCensusData(CensusAnalyser.SortingMode.AREA);
            IndiaCensusDAO[] csvStateCensuses = new Gson().fromJson(sortedCensusData, IndiaCensusDAO[].class);
            Assert.assertEquals("Andhra Pradesh New", csvStateCensuses[0].areaInSqKm);
        } catch (CensusAnalyserException e) {
            e.getStackTrace();
        }
    }

    @Test
    public void givenUSCensusAnalyserFile_WhenTrue_NumberOfRecordShouldMatch() throws CensusAnalyserException {
        String US_CSV_FILE_PATH = "./src/test/resources/USCensusData.csv";
        int count = usCensusAnalyzer.loadCensusData(US, US_CSV_FILE_PATH);
        Assert.assertEquals(51, count);
    }
    @Test
    public void givenTheUSCensusData_WhenSortedOnPopulation_ShouldReturnSortedResult() {
        String US_CSV_FILE_PATH = "./src/test/resources/USCensusData.csv";
        try {
            int numberOfRecords = usCensusAnalyzer.loadCensusData(CensusAnalyser.Country.US, US_CSV_FILE_PATH);
            String sortedCensusData = usCensusAnalyzer.SortedCensusData(CensusAnalyser.SortingMode.POPULATION);
            IndiaCensusDAO[] censusDAOS = new Gson().fromJson(sortedCensusData, IndiaCensusDAO[].class);
            Assert.assertEquals("California",censusDAOS[0].state);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenTheUSCensusData_WhenSortedOnPopulationDensity_ShouldReturnSortedResult() {
        String US_CSV_FILE_PATH = "./src/test/resources/USCensusData.csv";
        try {
            int numberOfRecords = usCensusAnalyzer.loadCensusData(CensusAnalyser.Country.US, US_CSV_FILE_PATH);
            String sortedCensusData = usCensusAnalyzer.SortedCensusData(CensusAnalyser.SortingMode.DENSITY);
            IndiaCensusDAO[] censusDAOS = new Gson().fromJson(sortedCensusData, IndiaCensusDAO[].class);
            Assert.assertEquals("District of Columbia",censusDAOS[0].state);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void givenTheUSCensusData_WhenSortedOnArea_ShouldReturnSortedResult() {
        String US_CSV_FILE_PATH = "./src/test/resources/USCensusData.csv";
        try {
            int numberOfRecords = usCensusAnalyzer.loadCensusData(CensusAnalyser.Country.US, US_CSV_FILE_PATH);
            String sortedCensusData = usCensusAnalyzer.SortedCensusData(CensusAnalyser.SortingMode.AREA);
            IndiaCensusDAO[] censusDAOS = new Gson().fromJson(sortedCensusData, IndiaCensusDAO[].class);
            Assert.assertEquals("Alaska",censusDAOS[0].state);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }




}








