package censusanalyser;

import CSVBuilder.CSVBuilderFactory;
import CSVBuilder.ICSVBuilder;
import csv.CSVStates;
import csv.IndiaCensusCSV;
import csv.USCensusCSV;
import exception.CensusAnalyserException;
import org.apache.commons.collections.map.HashedMap;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.StreamSupport;

public abstract class CensusAdapter {

    public <E> Map<String, IndiaCensusDAO> loadCensusData(Class<E> ClassType, String... csvFilePath) throws CensusAnalyserException {
        Map<String, IndiaCensusDAO> censusCSVMap = new HashedMap();
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath[0]));) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<E> csvIterator = csvBuilder.getCSVFileIterator(reader, ClassType);
            Iterable<E> csvIterable = () -> csvIterator;
            if (ClassType.getName().equals("csv.IndiaCensusCSV"))
                StreamSupport.stream(csvIterable.spliterator(), false)
                        .map(IndiaCensusCSV.class::cast)
                        .forEach(censusCSV -> censusCSVMap.put(censusCSV.state, new IndiaCensusDAO(censusCSV)));
            else if (ClassType.getName().equals("csv.USCensusCSV"))
                StreamSupport.stream(csvIterable.spliterator(), false)
                        .map(USCensusCSV.class::cast)
                        .forEach(censusCSV -> censusCSVMap.put(censusCSV.state, new IndiaCensusDAO(censusCSV)));
            else if (ClassType.getName().equals("csv.CSVStates"))
                StreamSupport.stream(csvIterable.spliterator(), false)
                        .map(CSVStates.class::cast)
                        .forEach(censusCSV -> censusCSVMap.put(censusCSV.state, new IndiaCensusDAO(censusCSV)));
            return censusCSVMap;
        } catch (RuntimeException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.WRONG_DELIMITER);
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);

        }
    }
}
