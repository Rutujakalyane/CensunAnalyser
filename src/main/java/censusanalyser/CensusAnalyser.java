package censusanalyser;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.stream.StreamSupport;

public class CensusAnalyser {
    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
        try {
            Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            List<IndiaCensusCSV> censusCSVList = csvBuilder.getCSVFileList(reader,IndiaCensusCSV.class);
            return censusCSVList.size();
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }  catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(),e.type.name());
        }catch (RuntimeException e) {
            throw new CensusAnalyserException(e.getMessage(),CensusAnalyserException.ExceptionType.WRONG_DELIMITER);
        } catch (Exception e) {
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.WRONG_HEADER_PROBLEM);
        }
    }

    public int loadIndiaStateCodeData(String csvFilePath) throws CensusAnalyserException {
        try {
            Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            List<CSVStates> censusCSVList = csvBuilder.getCSVFileList(reader,CSVStates.class);
            return censusCSVList.size();
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(),e.type.name());
        } catch (RuntimeException e) {
            throw new CensusAnalyserException(e.getMessage(),CensusAnalyserException.ExceptionType.WRONG_DELIMITER);
        } catch (Exception e) {
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.WRONG_HEADER_PROBLEM);
        }
    }

    public <E> int getCount(Iterator<E> censusCSVIterator) {
        Iterable<E> csvIterable = () -> censusCSVIterator;
        return (int) StreamSupport.stream(csvIterable.spliterator(), false).count();
    }
    public String getStateWiseSortedCensusData(String indiaStateCodeCsvFilePath) {
        return null;
    }
}


