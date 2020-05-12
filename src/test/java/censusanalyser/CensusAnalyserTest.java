package censusanalyser;

import csv.CSVStates;
import csv.IndiaCensusCSV;
import com.google.gson.Gson;

import exception.CensusAnalyserException;
import org.junit.Assert;
import org.junit.Test;
import constants.filepath;

public class CensusAnalyserTest {
    CensusAnalyser censusAnalyser = new CensusAnalyser();

    @Test  //1.1
    public void givenIndianCensusCSVFileReturnsCorrectRecords() {
        try {
            int numOfRecords = censusAnalyser.loadIndiaCensusData(filepath.INDIA_CENSUS_CSV_FILE_PATH);
            Assert.assertEquals(29, numOfRecords);
        } catch (CensusAnalyserException e) {
        }
    }

    @Test //1.2
    public void givenIndiaCensusData_WithWrongFile_ShouldThrowException() {
        try {
            censusAnalyser.loadIndiaCensusData(filepath.WRONG_CENSUS_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }

    @Test //1.3
    public void givenIndianCensusData_WithWrongFileType_Should_ReturnException() {
        try {
            censusAnalyser.loadIndiaCensusData(filepath.WRONG_CENSUS_CSV_FILE_TYPE);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }

    @Test //1.4
    public void givenIndianCensusData_WithIncorrectDelimiter_Should_ReturnException() {
        try {
            censusAnalyser.loadIndiaCensusData(filepath.WRONG_CENSUS_DELIMITER);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.WRONG_DELIMITER, e.type);
        }
    }
    @Test //1.5
    public void givenIndiaStateCensusData_WithWrongHeader_ShouldThrowException() {
        try {
            int numOfRecords = censusAnalyser.loadIndiaCensusData(filepath.INDIA_CENSUS_WRONG_HEADER_PATH);
            Assert.assertEquals(29, numOfRecords);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.WRONG_HEADER_PROBLEM, e.type);
        }
    }
    @Test //2.1
    public void givenIndianStateCodeCSVFileReturnsCorrectRecords() {
        try {
            int numOfRecords = censusAnalyser.loadIndiaStateCodeData(filepath.INDIA_STATE_CODE_CSV_FILE_PATH);
            Assert.assertEquals(29, numOfRecords);
        } catch (CensusAnalyserException e) {
        }
    }
    @Test //2.2
    public void givenIndianStateCodeData_WithWrongFile_ShouldThrowException() {
        try {
            censusAnalyser.loadIndiaStateCodeData(filepath.WRONG_STATE_CODE_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }
    @Test //2.3
    public void givenStateCode_WithWrongFileType_Should_ReturnException() {
        try {
            censusAnalyser.loadIndiaStateCodeData(filepath.WRONG_STATE_CODE_CSV_FILE_TYPE);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }
    @Test //2.4
    public void givenStateCode_WithIncorrectDelimiter_Should_ReturnException() {
        try {
            censusAnalyser.loadIndiaStateCodeData(filepath.WRONG_STATE_CODE_DELIMITER);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.WRONG_DELIMITER, e.type);
        }
    }

    @Test //2.5
    public void givenIndiaStateCodeData_WithWrongHeader_ShouldThrowException() {
        try {
            int numOfRecords = censusAnalyser.loadIndiaStateCodeData(filepath.INDIA_STATE_CODE_WRONG_HEADER_PATH);
            Assert.assertEquals(37, numOfRecords);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.WRONG_HEADER_PROBLEM, e.type);
        }
    }
    @Test
    public void givenIndianCensusData_WhenSortedOnState_ShouldReturnSortedResult() {
        try {
            censusAnalyser.loadIndiaCensusData(filepath.INDIA_CENSUS_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyser.getStateWiseSortedCensusData();
            IndiaCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals("Andhra Pradesh", censusCSV[0].state);
        }catch (CensusAnalyserException e){

        }
    }
    @Test
    public void givenIndianCensusData_WhenSortedOnStateCode_ShouldReturnSortedResult() {
        try {
            censusAnalyser.loadIndiaCensusData(filepath.INDIA_STATE_CODE_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyser.getStateCodeWiseSortedCensusData();
            CSVStates[] censusCSV = new Gson().fromJson(sortedCensusData, CSVStates[].class);
            Assert.assertEquals("Andhra Pradesh new", censusCSV[0].state);
        }catch (CensusAnalyserException e){ }
    }
    @Test
    public void givenIndianCensusData_WhenSortedOnPopulation_ShouldReturnSortedResult() {
        try {
            censusAnalyser.loadIndiaCensusData(filepath.INDIA_CENSUS_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyser.getPopulationWiseSortedCensusData();
            IndiaCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals("Uttar Pradesh", censusCSV[0].state);
        }catch (CensusAnalyserException e){ }
    }
    @Test

    public void givenIndianCensusData_WhenSortedOnPopulationDensityPerSqKm_ShouldReturnSortedResult() {
        try {
            censusAnalyser.loadIndiaCensusData(filepath.INDIA_CENSUS_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyser.getPopulationDensityWiseSortedCensusData();
            IndiaCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals("Bihar", censusCSV[0].state);
        }catch (CensusAnalyserException e){

        }
    }
    @Test
    public void givenIndianCensusData_WhenSortedOnArea_ShouldReturnSortedResult() {
        try {
            censusAnalyser.loadIndiaCensusData(filepath.INDIA_CENSUS_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyser.getAreaWiseSortedCensusData();
            IndiaCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals("Rajasthan", censusCSV[0].state);
        }catch (CensusAnalyserException e){

        }
    }
    @Test
    public void givenUSCensusCSVFileReturnsCorrectRecords() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            int numOfRecords = censusAnalyser.loadUSCensusData(filepath.US_CSV_FILE_PATH);
            Assert.assertEquals(51,numOfRecords);
        } catch (CensusAnalyserException e) {
            
        }
    }
}





