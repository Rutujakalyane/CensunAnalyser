package censusanalyser;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.stream.StreamSupport;

public class CensusAnalyser {
    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
        try {
            Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));
            CsvToBeanBuilder<IndiaCensusCSV> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
            csvToBeanBuilder.withType(IndiaCensusCSV.class);
            csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
            CsvToBean<IndiaCensusCSV> csvToBean = csvToBeanBuilder.build();
            Iterator<IndiaCensusCSV> censusCSVIterator = csvToBean.iterator();
            Iterable<IndiaCensusCSV> csvIterable = () -> censusCSVIterator;
            int namOfEateries = (int) StreamSupport.stream(csvIterable.spliterator(), false).count();
            return namOfEateries;
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (RuntimeException e) {
            throw new CensusAnalyserException("Wrong delimeter", CensusAnalyserException.ExceptionType.WRONG_DELIMITER, e.getCause());
        }catch(Exception e) {
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.WRONG_HEADER_PROBLEM);
        }
    }
    public int loadIndiaStateCodeData(String csvFilePath) throws CensusAnalyserException {
        try {
            Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));
            CsvToBeanBuilder<CSVStates> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
            csvToBeanBuilder.withType(CSVStates.class);
            csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
            CsvToBean<CSVStates> csvToBean = csvToBeanBuilder.build();
            Iterator<CSVStates> censusCSVIterator = csvToBean.iterator();;
            Iterable<CSVStates> csvIterable=()->censusCSVIterator;
            int namOfStates = (int) StreamSupport.stream(csvIterable.spliterator(),false).count();
            return namOfStates;
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }catch(Exception e) {
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.WRONG_HEADER_PROBLEM);
        }
    }
    public void loadIndiaCensusData(String csvFilePath, Class<IndiaCensusCSV> indiaCensusCSVClass, char c) throws CensusAnalyserException {
        if(c!=','){
            throw new CensusAnalyserException("Wrong Delimiter",CensusAnalyserException.ExceptionType.WRONG_DELIMITER);
        }else{
            loadIndiaCensusData(csvFilePath,indiaCensusCSVClass);
        }
    }
    private void loadIndiaCensusData(String csvFilePath, Class<IndiaCensusCSV> indiaCensusCSVClass) {
    }

    public void loadIndiaStateCodeData(String csvFilePath, Class<IndiaCensusCSV> indiaCensusCSVClass, char c) throws CensusAnalyserException {
        if(c!=','){
            throw new CensusAnalyserException("Wrong Delimiter",CensusAnalyserException.ExceptionType.WRONG_DELIMITER);
        }else{
            loadIndiaCensusData(csvFilePath,indiaCensusCSVClass);
        }
    }
}




