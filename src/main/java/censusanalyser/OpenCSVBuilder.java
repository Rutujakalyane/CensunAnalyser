package censusanalyser;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.Reader;
import java.util.List;

public class OpenCSVBuilder implements ICSVBuilder {
    @Override
        public List getCSVFileList(Reader reader, Class className) {
            try{
                return getCsvToBean(reader,className).parse();
            }catch (RuntimeException e){
                throw new CSVBuilderException(e.getMessage(),CSVBuilderException.ExceptionType.WRONG_DELIMITER);
            }
        }
        private <E> CsvToBean getCsvToBean(Reader reader, Class className) throws CSVBuilderException{

                CsvToBeanBuilder<E> CsvToBeanBuilder= new CsvToBeanBuilder<>(reader);
                CsvToBeanBuilder.withType(className);
                CsvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
                CsvToBeanBuilder.withSeparator(',');
               return CsvToBeanBuilder.build();

        }
}