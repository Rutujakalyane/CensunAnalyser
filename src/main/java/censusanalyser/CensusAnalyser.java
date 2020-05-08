package censusanalyser;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class CensusAnalyser {
    List<IndiaCensusCSV> censusCSVList = null;
    List<CSVStates> censusStateList = null;

    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
        try {
            Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            List<IndiaCensusCSV>  censusCSVList = csvBuilder.getCSVFileList(reader,IndiaCensusCSV.class);
            return censusCSVList.size();
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(),e.type.name());
        }

    }
    public int loadIndiaStateCodeData(String csvFilePath) throws CensusAnalyserException {
        try {
            Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            List<CSVStates> stateCSVList = csvBuilder.getCSVFileList(reader,CSVStates.class);
            return stateCSVList.size();
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(),e.type.name());
        }
    }

  /*  public <E> int getCount(Iterator<E> censusCSVIterator) {
        Iterable<E> csvIterable = () -> censusCSVIterator;
        return (int) StreamSupport.stream(csvIterable.spliterator(), false).count();
    }*/
  private void isNull(List list) throws CensusAnalyserException {
      if(list == null || list.size()==0){
          throw new CensusAnalyserException("No Census Data",CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
      }
  }

    private String getJson(List list){
        String json = new Gson().toJson(list);
        return json;
    }
  public String getStateWiseCensusData() throws CensusAnalyserException {
      isNull(censusCSVList);
      Comparator<IndiaCensusCSV> comparing = Comparator.comparing( census -> census.state );
      this.sortDescending(comparing);
      return getJson(censusCSVList);
  }
    private void sortDescending(Comparator<IndiaCensusCSV> comparing) {
        for (int i = 0; i < censusCSVList.size() - 1; i++) {
            for (int j = 0; j < censusCSVList.size() - i - 1; j++) {
                IndiaCensusCSV census1 = censusCSVList.get( j );
                IndiaCensusCSV census2 = censusCSVList.get( j + 1 );
                if (comparing.compare( census1, census2 ) < 0) {
                    censusCSVList.set( j, census2 );
                    censusCSVList.set( j + 1, census1 );
                }
            }
        }
    }
    public String getStateCodeWiseCensusData() throws CensusAnalyserException {
        isNull(censusStateList);
        Comparator<CSVStates> comparing = Comparator.comparing( census -> census.stateCode);
        this.sort(comparing,censusStateList );
        return getJson(censusStateList);
    }

    private void sort(Comparator comparing,List list) {
        for (int i = 0; i < censusStateList.size() - 1; i++) {
            for (int j = 0; j < censusStateList.size() - i - 1; j++) {
                CSVStates census1 = censusStateList.get( j );
                CSVStates census2 = censusStateList.get( j + 1 );
                if (comparing.compare( census1, census2 ) > 0) {
                    censusStateList.set( j, census2 );
                    censusStateList.set( j + 1, census1 );
                }
            }
        }
    }

}



