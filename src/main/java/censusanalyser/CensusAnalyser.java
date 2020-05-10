package censusanalyser;
import censusanalyser.CSV.CSVStates;
import censusanalyser.CSV.IndiaCensusCSV;
import censusanalyser.CSV.USCensusCSV;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

    public class CensusAnalyser<E> {
        Map<String, IndiaCensusDAO> censusCSVMap = null;
        List<IndiaCensusDAO> censusCSVList = null;

        public CensusAnalyser() {
            censusCSVMap = new HashMap<String, IndiaCensusDAO>();
            censusCSVList = new ArrayList<IndiaCensusDAO>();
        }

        public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
            try {
                Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));
                ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
                Iterator<IndiaCensusCSV> csvFileIterator = csvBuilder.getCSVFileIterator(reader, IndiaCensusCSV.class);
                Iterable<IndiaCensusCSV> csvStatesIterable = () -> csvFileIterator;
                Stream<IndiaCensusCSV> stream = StreamSupport.stream(csvStatesIterable.spliterator(), false);
                stream.forEach(censusCSV -> censusCSVMap.put(censusCSV.state, new IndiaCensusDAO(censusCSV)));
                censusCSVList.addAll(censusCSVMap.values());
                return censusCSVMap.size();
            } catch (IOException e) {
                throw new CensusAnalyserException(e.getMessage(),
                        CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
            } catch (CSVBuilderException e) {
                throw new CensusAnalyserException(e.getMessage(), e.type.name());
            } catch (Exception e) {
                throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.WRONG_HEADER_PROBLEM);
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
            } catch (CSVBuilderException e) {
                throw new CensusAnalyserException(e.getMessage(), e.type.name());
            } catch (Exception e) {
                throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.WRONG_HEADER_PROBLEM);
            }
        }

        /* public <E> int getCount(Iterator<E> censusCSVIterator) {
              Iterable<E> csvIterable = () -> censusCSVIterator;
              return (int) StreamSupport.stream(csvIterable.spliterator(), false).count();
          }*/
        private void isNull(List list) throws CensusAnalyserException {
            if (list == null || list.size() == 0) {
                throw new CensusAnalyserException("No Census Data", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
            }
        }

        private String getJson(List list) {
            String json = new Gson().toJson(list);
            return json;
        }

        public String getStateWiseCensusData() throws CensusAnalyserException {
            isNull(censusCSVList);
            Comparator<IndiaCensusDAO> comparing = Comparator.comparing(census -> census.state);
            this.sortDescending(comparing, censusCSVList);
            return getJson(censusCSVList);
        }

        private void sortDescending(Comparator comparing, List censusList) {
            for (int i = 0; i < censusList.size() - 1; i++) {
                for (int j = 0; j < censusList.size() - i - 1; j++) {
                    IndiaCensusDAO census1 = (IndiaCensusDAO) censusList.get(j);
                    IndiaCensusDAO census2 = (IndiaCensusDAO) censusList.get(j + 1);
                    if (comparing.compare(census1, census2) < 0) {
                        censusList.set(j, census2);
                        censusList.set(j + 1, census1);
                    }
                }
            }
        }

        public String getStateCodeWiseCensusData() throws CensusAnalyserException {
            isNull(censusCSVList);
            Comparator<IndiaCensusDAO> comparing = Comparator.comparing(census -> census.stateCode);
            this.sort(comparing, censusCSVList);
            return getJson(censusCSVList);
        }

        private void sort(Comparator comparing, List list) {
            for (int i = 0; i < censusCSVList.size() - 1; i++) {
                for (int j = 0; j < censusCSVList.size() - i - 1; j++) {
                    IndiaCensusDAO census1 = censusCSVList.get(j);
                    IndiaCensusDAO census2 = censusCSVList.get(j + 1);
                    if (comparing.compare(census1, census2) > 0) {
                        censusCSVList.set(j, census2);
                        censusCSVList.set(j + 1, census1);
                    }
                }
            }
        }

        public int loadUSCensusData(String csvFilePath) throws CensusAnalyserException {
            try {
                //checkType((Class<E>) USCensusCSV.class,USCensusCSVClass);
                //checkSeparator(c);
                Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));
                ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
                Iterator<USCensusCSV> csvFileIterator = csvBuilder.getCSVFileIterator(reader, USCensusCSV.class);
                Iterable<USCensusCSV> csvStatesIterable = () -> csvFileIterator;
                Stream<USCensusCSV> stream = StreamSupport.stream(csvStatesIterable.spliterator(), false);
                stream.forEach(censusCSV -> censusCSVMap.put(censusCSV.state, new IndiaCensusDAO(censusCSV)));
                censusCSVList.addAll(censusCSVMap.values());
                return censusCSVMap.size();
            } catch (IOException e) {
                throw new CensusAnalyserException(e.getMessage(),
                        CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
            } catch (CSVBuilderException e) {
                throw new CensusAnalyserException(e.getMessage(), e.type.name());
            }
        }
    }


