package CSVBuilder;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import exception.CensusAnalyserException;

import java.io.Reader;
import java.util.Iterator;
import java.util.List;

public class OpenCSVBuilder <E> implements ICSVBuilder {
    //    ITERATOR OF CSV FILE
    @Override
    public <E> Iterator<E> getIterator(Reader reader, Class<E> csvClass) throws CensusAnalyserException {
        try {
            return this.getCSVToBean(reader, csvClass).iterator();
        }catch (CensusAnalyserException e){
            e.printStackTrace();
        }
        return null;
    }

    // Return csvtoBean
    private  <E> CsvToBean<E> getCSVToBean(Reader reader, Class<E> csvClass) throws CensusAnalyserException {
        try {
            CsvToBeanBuilder<E> csvToBeanBuilder = new CsvToBeanBuilder(reader);
            csvToBeanBuilder.withType(csvClass);
            csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
            return csvToBeanBuilder.build();
        }  catch (IllegalStateException e) {
            throw new CensusAnalyserException("Wrong file", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
    }
}
