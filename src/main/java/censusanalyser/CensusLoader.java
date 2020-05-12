package censusanalyser;

import csv.CSVStates;
import csv.IndiaCensusCSV;
import csv.USCensusCSV;
import exception.CSVBuilderException;
import exception.CensusAnalyserException;
import org.apache.commons.collections.map.HashedMap;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;


public class CensusLoader {
    Map<String, IndiaCensusDAO> censusCSVMap = null;

    public  <E> Map<String, IndiaCensusDAO> loadCensusData(String csvFilePath, Class<E> censusCSVClass) throws CensusAnalyserException {
        Map<String ,IndiaCensusDAO > censusCSVMap = new HashedMap();
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<E> csvIterator = csvBuilder.getCSVFileIterator(reader, censusCSVClass);
            Iterable<E> csvIterable = () -> csvIterator;
            if (censusCSVClass.getName().equals("csv.IndiaCensusCSV")) {
                StreamSupport.stream(csvIterable.spliterator(), false)
                        .map(IndiaCensusCSV.class::cast)
                        .forEach(censusCSV -> censusCSVMap.put(censusCSV.state, new IndiaCensusDAO(censusCSV)));
            }if (censusCSVClass.getName().equals("csv.USCensusCSV")) {
                StreamSupport.stream(csvIterable.spliterator(), false)
                        .map(USCensusCSV.class::cast)
                        .forEach(censusCSV -> censusCSVMap.put(censusCSV.state, new IndiaCensusDAO(censusCSV)));
            }return this.censusCSVMap.size();
        } catch (RuntimeException e) {
            throw new CensusAnalyserException( e.getMessage(),
                    CensusAnalyserException.ExceptionType.WRONG_DELIMITER );
        } catch (IOException e) {
            throw new CensusAnalyserException( e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM );
        }

    }
    public int loadIndiaStateCodeData(String csvFilePath) throws CensusAnalyserException {
        try {
            Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<CSVStates> csvFileIterator = csvBuilder.getCSVFileIterator(reader, CSVStates.class);
            Iterable<CSVStates> csvStatesIterable = () -> csvFileIterator;
            Stream<CSVStates> stream = StreamSupport.stream(csvStatesIterable.spliterator(), false);
            stream.forEach(censusCSV -> censusCSVMap.put(censusCSV.state, new IndiaCensusDAO(censusCSV)));
            censusCSVList.addAll(censusCSVMap.values());
            return censusCSVMap.size();
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }  catch (Exception e) {
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.WRONG_HEADER_PROBLEM);
        }
    }
}
