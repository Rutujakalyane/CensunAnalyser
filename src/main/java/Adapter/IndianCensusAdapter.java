package Adapter;

import CSVBuilder.CSVBuilderFactory;
import CSVBuilder.ICSVBuilder;
import censusanalyser.IndiaCensusDAO;
import csv.CSVStates;
import csv.IndiaCensusCSV;
import exception.CensusAnalyserException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.StreamSupport;

public class IndianCensusAdapter extends CensusAdapter {
    @Override
    public Map<String, IndiaCensusDAO> loadCensusData(String... csvFilePath) throws CensusAnalyserException {
        Map<String, IndiaCensusDAO> censusDAOMap = super.loadCensusData(IndiaCensusCSV.class, csvFilePath[0]);
        if (csvFilePath.length == 1)
            return censusDAOMap;
        return loadStateCodeCSVData(censusDAOMap, csvFilePath[1]);
    }

    private Map<String, IndiaCensusDAO> loadStateCodeCSVData(Map<String, IndiaCensusDAO> censusDAOMap, String csvFilePath) throws CensusAnalyserException {
        String extension = csvFilePath.substring(csvFilePath.lastIndexOf(".") + 1);
        if (!extension.equals("csv")) {
            throw new CensusAnalyserException("Incorrect file type", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<CSVStates> stateCodeIterator = csvBuilder.getIterator(reader, CSVStates.class);
            Iterable<CSVStates> stateCodes = () -> stateCodeIterator;
            StreamSupport.stream(stateCodes.spliterator(), false)
                    .filter(CSVStates -> censusDAOMap.get(CSVStates.StateName) != null)
                    .forEach(CSVStates -> censusDAOMap.get(CSVStates.StateName).stateCode = CSVStates.StateCode);
        } catch (RuntimeException e) {
            throw new CensusAnalyserException("Incorrect delimiter", CensusAnalyserException.ExceptionType.WRONG_DELIMITER);
        } catch (IOException e) {
            throw new CensusAnalyserException("No such file", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        } catch(Exception e) {
            throw new CensusAnalyserException("Wrong header", CensusAnalyserException.ExceptionType.WRONG_HEADER_PROBLEM);
        }
        return censusDAOMap;
    }}

