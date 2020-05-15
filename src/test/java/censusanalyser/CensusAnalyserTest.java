package censusanalyser;

import com.google.gson.Gson;
import csv.CSVStates;
import csv.IndiaCensusCSV;
import csv.USCensusCSV;
import exception.CensusAnalyserException;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

import static censusanalyser.CensusAnalyser.Country.INDIA;
import static censusanalyser.CensusAnalyser.Country.US;
public class CensusAnalyserTest {

    CensusAnalyser indianCensusAnalyzer = new CensusAnalyser(INDIA);
    CensusAnalyser usCensusAnalyzer = new CensusAnalyser(US);
    @Test
    public void givenNumberOfRecords_WhenMatched_ShouldReturnTrue() {
        final String CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
        try {
            int numberOfRecords = indianCensusAnalyzer.loadCensusData(INDIA, CSV_FILE_PATH);
            Assert.assertEquals(29, numberOfRecords);
        } catch (CensusAnalyserException e) {
            e.getStackTrace();
        }
    }

    @Test
    public void givenFilePath_WhenWrong_ShouldReturnCustomiseException() {
        final String CSV_FILE_PATH = "./src/main/resources/IndiaStateCensusData.csv";
        try {
            indianCensusAnalyzer.loadCensusData(INDIA, CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.NO_CENSUS_DATA, e.type);
        }
    }

    @Test
    public void givenFileType_WhenWrong_ShouldReturnCustomiseException() {
        final String CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.pdf";
        try {
            indianCensusAnalyzer.loadCensusData(INDIA, CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.NO_CENSUS_DATA, e.type);
        }
    }

    @Test
    public void givenFile_WhenDelimiterIncorrect_ShouldReturnCustomiseException() {
        final String CSV_FILE_PATH = "./src/test/resources/WrongCensusDelimiter.csv";
        try {
            indianCensusAnalyzer.loadCensusData(INDIA, CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.WRONG_DELIMITER, e.type);
        }
    }

    @Test
    public void givenFile_WhenHeaderIncorrect_ShouldReturnCustomiseException() {
        final String CSV_FILE_PATH = "./src/test/resources/WrongCensusHeader.csv";
        try {
            indianCensusAnalyzer.loadCensusData(INDIA, CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.WRONG_HEADER_PROBLEM, e.type);
        }
    }

    @Test
    public void givenNumberOfRecordsOfStateCode_WhenMatched_ShouldReturnTrue()  {
        final String CSV_FILE_PATH = "./src/test/resources/IndiaStateCode.csv";
        try {
            int numberOfRecords = indianCensusAnalyzer.loadCensusData(INDIA, CSV_FILE_PATH);
            Assert.assertEquals(37, numberOfRecords);
        } catch (CensusAnalyserException e) {
        }
    }

    @Test
    public void givenFilePathOfStateCode_WhenWrong_ShouldReturnCustomiseException() {
        final String CSV_FILE_PATH = "./src/main/resources/IndiaStateCode.csv";
        try {
            indianCensusAnalyzer.loadCensusData(INDIA, CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.NO_CENSUS_DATA, e.type);
        }
    }

    @Test
    public void givenFileTypeOfStateCode_WhenWrong_ShouldReturnCustomiseException() {
        final String CSV_FILE_PATH = "./src/test/resources/IndiaStateCode.doc";;
        try {
            indianCensusAnalyzer.loadCensusData(INDIA, CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.NO_CENSUS_DATA, e.type);
        }
    }


    @Test
    public void givenFileOfStateCode_WhenDelimiterIncorrect_ShouldReturnCustomiseException() {
        final String CSV_FILE_PATH = "./src/test/resources/WrongStateCodeDelimiter.csv";;
        try {
            indianCensusAnalyzer.loadCensusData(INDIA, CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.WRONG_DELIMITER, e.type);
        }
    }

    @Test
    public void givenFileOfStateCode_WhenHeadersIncorrect_ShouldReturnCustomiseException() {
        final String CSV_FILE_PATH = "./src/test/resources/WrongCSVHeader.csv";
        try {
            indianCensusAnalyzer.loadCensusData(INDIA, CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.WRONG_HEADER_PROBLEM, e.type);
        }
    }

   @Test
    public void givenCensusData_WhenSorted_ShouldReturnSortedList() {
        final String CSV_FILE_PATH =  "./src/test/resources/IndiaStateCensusData.csv";
        try {
            indianCensusAnalyzer.loadCensusData(INDIA, CSV_FILE_PATH);
            String sortedCensusData = indianCensusAnalyzer.SortedStateCensusData(CensusAnalyser.SortingMode.STATE);
            IndiaCensusCSV[] csvStatesCensus = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals("Andhra Pradesh", csvStatesCensus[csvStatesCensus.length-1].state);
        } catch (CensusAnalyserException e) {
            e.getStackTrace();
        }
    }

    @Test   //sort state code
    public void givenStateCodeData_WhenSorted_ShouldReturnSortedList() {
        final String CSV_FILE_PATH = "./src/test/resources/IndiaStateCode.csv";
        try {
            indianCensusAnalyzer.loadCensusData(INDIA, CSV_FILE_PATH);
            String sortedCensusData = indianCensusAnalyzer.SortedStateCensusData(CensusAnalyser.SortingMode.STATE);
            CSVStates[] csvStateCodes = new Gson().fromJson(sortedCensusData, CSVStates[].class);
            Assert.assertEquals("AD", csvStateCodes[csvStateCodes.length-1].StateCode);
        } catch (CensusAnalyserException e) {
            e.getStackTrace();
        }
    }

    @Test
    public void givenTheStateCensusData_WhenSortedOnPopulation_ShouldReturnSortedResult() {
        final String CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
        try {
            indianCensusAnalyzer.loadCensusData(INDIA, CSV_FILE_PATH);
            String sortedCensusData = indianCensusAnalyzer.SortedStateCensusData(CensusAnalyser.SortingMode.POPULATION);
            IndiaCensusCSV[] csvStatesCensus = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals("Uttar Pradesh", csvStatesCensus[0].population);
        } catch (CensusAnalyserException e) {
            e.getStackTrace();
        }
    }

    @Test
    public void givenTheStateCensusData_WhenSortedOnDensityPerSqKm_ShouldReturnSortedResult() {
        final String CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
        try {
            indianCensusAnalyzer.loadCensusData(INDIA, CSV_FILE_PATH);
            String sortedCensusData = indianCensusAnalyzer.SortedStateCensusData(CensusAnalyser.SortingMode.DENSITY);
            IndiaCensusCSV[] csvStateCensuses = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals("Bihar", csvStateCensuses[0].densityPerSqKm);
        } catch (CensusAnalyserException e) {
            e.getStackTrace();
        }
    }

    @Test
    public void givenTheStateCensusData_WhenSortedOnAreaInPerSqKm_ShouldReturnSortedResult() {
        final String CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
        try {
            indianCensusAnalyzer.loadCensusData(INDIA, CSV_FILE_PATH);
            String sortedCensusData = indianCensusAnalyzer.SortedStateCensusData(CensusAnalyser.SortingMode.AREA);
            IndiaCensusCSV[] csvStateCensus = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals("Uttar Pradesh", csvStateCensus[0].areaInSqKm);
        } catch (CensusAnalyserException e) {
            e.getStackTrace();
        }
    }

    @Test
    public void givenUSCensusAnalyserFile_WhenTrue_NumberOfRecordShouldMatch() throws IOException {
        final String CSV_FILE_PATH = "./src/test/resources/USCensusData.csv";
        int count = 0;
        try {
            count = usCensusAnalyzer.loadCensusData(US, CSV_FILE_PATH);
            Assert.assertEquals(51, count);
        } catch (CensusAnalyserException e) {
        }
    }

    @Test
    public void givenTheUSCensusData_WhenSortedOnPopulation_ShouldReturnSortedResult() {
        final String CSV_FILE_PATH = "./src/test/resources/USCensusData.csv";
        try {
            usCensusAnalyzer.loadCensusData(CensusAnalyser.Country.US, CSV_FILE_PATH);
            String sortedCensusData = usCensusAnalyzer.SortedStateCensusData(CensusAnalyser.SortingMode.POPULATION);
            USCensusCSV[] csvUsCensus = new Gson().fromJson(sortedCensusData, USCensusCSV[].class);
            Assert.assertEquals("California",csvUsCensus[0].state);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenTheUSCensusData_WhenSortedOnPopulationDensity_ShouldReturnSortedResult() {
        final String CSV_FILE_PATH = "./src/test/resources/USCensusData.csv";
        try {
            usCensusAnalyzer.loadCensusData(US, CSV_FILE_PATH);
            String sortedCensusData = usCensusAnalyzer.SortedStateCensusData(CensusAnalyser.SortingMode.DENSITY);
            USCensusCSV[] csvUsCensus = new Gson().fromJson(sortedCensusData, USCensusCSV[].class);
            Assert.assertEquals("District of Columbia",csvUsCensus [0].state);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenTheUSCensusData_WhenSortedOnArea_ShouldReturnSortedResult() {
        final String CSV_FILE_PATH = "./src/test/resources/USCensusData.csv";
        try {
            usCensusAnalyzer.loadCensusData(US, CSV_FILE_PATH);
            String sortedCensusData = usCensusAnalyzer.SortedStateCensusData(CensusAnalyser.SortingMode.AREA);
            USCensusCSV[] csvusCensus = new Gson().fromJson(sortedCensusData,  USCensusCSV[].class);
            Assert.assertEquals("Alaska",csvusCensus[0].state);
        } catch (CensusAnalyserException e) {
            e.getStackTrace();
        }
    }
}







