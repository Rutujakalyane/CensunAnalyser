package censusanalyser;

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


public class CensusLoader<T> {//

   public <E> Map<String, IndiaCensusDAO> loadCensusData(Class<E> censusCSVClass, String... csvFilePath) throws CensusAnalyserException {
       Map<String, IndiaCensusDAO> censusCSVMap = new HashedMap();
       try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath[0]));) {
           ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
           Iterator<E> csvIterator = csvBuilder.getCSVFileIterator(reader, censusCSVClass);
           Iterable<E> csvIterable = () -> csvIterator;
           if (censusCSVClass.getName().equals("csv.IndiaCensusCSV"))
               StreamSupport.stream(csvIterable.spliterator(), false)
                       .map(IndiaCensusCSV.class::cast)
                       .forEach(censusCSV -> censusCSVMap.put(censusCSV.state, new IndiaCensusDAO(censusCSV)));
           if (censusCSVClass.getName().equals("csv.USCensusCSV"))
               StreamSupport.stream(csvIterable.spliterator(), false)
                       .map(USCensusCSV.class::cast)
                       .forEach(censusCSV -> censusCSVMap.put(censusCSV.state, new IndiaCensusDAO(censusCSV)));
           if (csvFilePath.length == 1) return censusCSVMap;
           return this.loadCensusData(censusCSVMap, csvFilePath[1]);
       } catch (RuntimeException e) {
           throw new CensusAnalyserException(e.getMessage(),
                   CensusAnalyserException.ExceptionType.WRONG_DELIMITER);
       } catch (IOException e) {
           throw new CensusAnalyserException(e.getMessage(),
                   CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
       }
   }


       public Map<String, IndiaCensusDAO> loadCensusData(Map<String, IndiaCensusDAO> censusStateMap, String indiaCodeCsvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(indiaCodeCsvFilePath));) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<CSVStates> stateCSVIterator = csvBuilder.getCSVFileIterator(reader, CSVStates.class);
            Iterable<CSVStates> csvIterable = () -> stateCSVIterator;
            StreamSupport.stream(csvIterable.spliterator(), false)
                    .filter(csvState -> censusStateMap.get(csvState.stateCode) != null)
                    .forEach(csvState -> censusStateMap.get(csvState.stateCode).stateCode = csvState.stateCode);
            return censusStateMap;
       } catch (IOException e) {
        throw new CensusAnalyserException(e.getMessage(),
                CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
    }  catch (Exception e) {
        throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.WRONG_HEADER_PROBLEM);
    }
}


}
