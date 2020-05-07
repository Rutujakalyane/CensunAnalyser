package censusanalyser;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.Reader;
import java.util.Iterator;
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

        private CsvToBean getCsvToBean(Reader reader, Class className) {
            try{
                return (CsvToBean) new CsvToBeanBuilder<>(reader)
                        .withType(className)
                        .withIgnoreLeadingWhiteSpace(true)
                        .withSeparator(',')
                        .build();
            }catch (RuntimeException e){
                throw new CSVBuilderException(e.getMessage(),CSVBuilderException.ExceptionType.WRONG_HEADER_PROBLEM);
            }
        }
}