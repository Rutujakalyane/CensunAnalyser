package censusanalyser;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import java.io.Reader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class OpenCSVBuilder <E> implements ICSVBuilder {
    @Override
    public Iterator getCSVFileIterator(Reader reader, Class csvStatesClass){
        try{
            return getCsvToBean(reader,csvStatesClass).iterator();
        }catch (Exception e){
            throw new CSVBuilderException(e.getMessage(),CSVBuilderException.ExceptionType.WRONG_DELIMITER);
        }
    }
    private <E> CsvToBean getCsvToBean(Reader reader, Class className) throws CSVBuilderException{
        try {
            CsvToBeanBuilder<E> csvToBeanBuilder = new CsvToBeanBuilder( reader );
            csvToBeanBuilder.withType( className );
            csvToBeanBuilder.withIgnoreLeadingWhiteSpace( true );
            return csvToBeanBuilder.build();
        } catch (Exception e) {
            throw new CSVBuilderException( e.getMessage(),
                    CSVBuilderException.ExceptionType.WRONG_DELIMITER );
        }
    }}