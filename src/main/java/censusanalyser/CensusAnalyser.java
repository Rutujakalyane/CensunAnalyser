package censusanalyser;
import csv.CSVStates;
import csv.IndiaCensusCSV;
import csv.USCensusCSV;
import com.google.gson.Gson;
import exception.CensusAnalyserException;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.StreamSupport;

    public class CensusAnalyser {
        Map<String,IndiaCensusDAO> censusCSVMap=null;
        List<IndiaCensusDAO> censusCSVList = null;
        List<IndiaCensusDAO> stateCSVList = null;
        public CensusAnalyser(){
            this.censusCSVMap=new HashMap<>();
        }
        public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
            return this.loadCensusData(csvFilePath, IndiaCensusCSV.class);
        }
        public int loadUSCensusData(String csvFilePath) throws CensusAnalyserException {
            return this.loadCensusData( csvFilePath, USCensusCSV.class );
        }
        public int loadIndiaStateCodeData(String csvFilePath) throws CensusAnalyserException {
            return this.loadCensusData( csvFilePath, CSVStates.class );
        }

        private <E> int loadCensusData(String csvFilePath, Class<E> censusCSVClass) throws CensusAnalyserException {
            try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
                ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
                Iterator<E> csvIterator = csvBuilder.getCSVFileIterator(reader, censusCSVClass);
                Iterable<E> csvIterable = () -> csvIterator;
                if (censusCSVClass.getName().equals("csv.IndiaCensusCSV")) {
                    StreamSupport.stream(csvIterable.spliterator(), false)
                            .map(IndiaCensusCSV.class::cast)
                            .forEach(censusCSV -> censusCSVMap.put(censusCSV.state, new IndiaCensusDAO(censusCSV)));
                }else if (censusCSVClass.getName().equals("csv.USCensusCSV")) {
                    StreamSupport.stream(csvIterable.spliterator(), false)
                            .map(USCensusCSV.class::cast)
                            .forEach(censusCSV -> censusCSVMap.put(censusCSV.state, new IndiaCensusDAO(censusCSV)));
                }else if (censusCSVClass.getName().equals("csv.CSVStates")) {
                    StreamSupport.stream(csvIterable.spliterator(), false)
                            .map(CSVStates.class::cast)
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
        /* public <E> int getCount(Iterator<E> censusCSVIterator) {
              Iterable<E> csvIterable = () -> censusCSVIterator;
              return (int) StreamSupport.stream(csvIterable.spliterator(), false).count();
          }*/
        public void isNull(List list) throws CensusAnalyserException {
            if (list == null || list.size() == 0) {
                throw new CensusAnalyserException("No Census Data", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
            }
        }
        public String json(List list) {
            String json = new Gson().toJson(list);
            return json;
        }
        public String getStateWiseSortedCensusData() throws CensusAnalyserException {
            isNull(censusCSVList);
            Comparator<IndiaCensusDAO> comparing = Comparator.comparing(census -> census.state);
            this.sort(comparing,censusCSVList);
            return json(censusCSVList);
        }
        public String getStateCodeWiseSortedCensusData() throws CensusAnalyserException {
            isNull(stateCSVList);
            Comparator<IndiaCensusDAO> comparing = Comparator.comparing(census -> census.stateCode);
            this.sort(comparing,stateCSVList);
            return json(stateCSVList);
        }
        public String getPopulationWiseSortedCensusData() throws CensusAnalyserException {
            isNull(censusCSVList);
            Comparator<IndiaCensusDAO> comparing = Comparator.comparing(census -> census.population);
            this.sortDescending(comparing,censusCSVList);
            return json(censusCSVList);
        }
        public String getPopulationDensityWiseSortedCensusData() throws CensusAnalyserException {
            isNull(censusCSVList);
            Comparator<IndiaCensusDAO> comparing = Comparator.comparing(census -> census.densityPerSqKm);
            this.sortDescending(comparing,censusCSVList);
            return json(censusCSVList);
        }
        public String getAreaWiseSortedCensusData() throws CensusAnalyserException {
            isNull(censusCSVList);
            Comparator<IndiaCensusDAO> comparing = Comparator.comparing(census -> census.areaInSqKm);
            this.sortDescending(comparing,censusCSVList);
            return json(censusCSVList);
        }
        private void sortDescending(Comparator comparing, List list) {
            for (int i = 0; i < censusCSVList.size() - 1; i++) {
                for (int j = 0; j < censusCSVList.size() - i - 1; j++) {
                    IndiaCensusDAO census1 = censusCSVList.get(j);
                    IndiaCensusDAO census2 = censusCSVList.get(j + 1);
                    if (comparing.compare(census1, census2) < 0) {
                        censusCSVList.set(j, census2);
                        censusCSVList.set(j + 1, census1);
                    }
                }
            }
        }
        private void sort(Comparator comparing, List list) {
            for (int i = 0; i < stateCSVList.size() - 1; i++) {
                for (int j = 0; j < stateCSVList.size() - i - 1; j++) {
                    IndiaCensusDAO census1 = stateCSVList.get(j);
                    IndiaCensusDAO census2 = stateCSVList.get(j + 1);
                    if (comparing.compare(census1, census2) > 0) {
                        stateCSVList.set(j, census2);
                        stateCSVList.set(j + 1, census1);
                    }
                }
            }
        }

    }


