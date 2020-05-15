package Adapter;

import CSVBuilder.CSVBuilderFactory;
import CSVBuilder.ICSVBuilder;
import censusanalyser.IndiaCensusDAO;
import csv.CSVStates;
import csv.IndiaCensusCSV;
import csv.USCensusCSV;
import exception.CensusAnalyserException;
import org.apache.commons.collections.map.HashedMap;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.StreamSupport;

import static java.nio.file.Files.newBufferedReader;

public abstract class CensusAdapter {
    public abstract Map<String, IndiaCensusDAO> loadCensusData(String... csvFilePath) throws CensusAnalyserException;

    public <E> Map<String, IndiaCensusDAO> loadCensusData(Class<E> censusCSVClass, String csvFilePath) throws CensusAnalyserException {
        Map<String, IndiaCensusDAO> censusDAOMap = new HashMap<>();
        try (Reader reader = newBufferedReader(Paths.get(csvFilePath));) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<E> csvIterator = csvBuilder.getIterator(reader, censusCSVClass);
            Iterable<E> csvIterable = () -> csvIterator;
            if (censusCSVClass.getName().contains("CSVStatesCensus")) {
                StreamSupport.stream(csvIterable.spliterator(), false)
                        .map(IndiaCensusCSV.class::cast)
                        .forEach(censusCSV -> censusDAOMap.put(censusCSV.state, new IndiaCensusDAO(censusCSV)));
            } else if (censusCSVClass.getName().contains("CSVUSCensus")) {
                StreamSupport.stream(csvIterable.spliterator(), false)
                        .map(USCensusCSV.class::cast)
                        .forEach(censusCSV -> censusDAOMap.put(censusCSV.state, new IndiaCensusDAO(censusCSV)));
            }
        } catch (RuntimeException e) {
            throw new CensusAnalyserException("Wrong delimiter", CensusAnalyserException.ExceptionType.WRONG_DELIMITER);
        } catch (IOException e) {
            throw new CensusAnalyserException("File not found", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        } catch(Exception e) {
            throw new CensusAnalyserException("Wrong header", CensusAnalyserException.ExceptionType.WRONG_HEADER_PROBLEM);
        }
        return censusDAOMap;
    }

}



