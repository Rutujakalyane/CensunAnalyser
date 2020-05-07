package censusanalyser;
import java.io.Reader;
import java.util.Iterator;
import java.util.List;

public interface ICSVBuilder {

    <E> List<E> getCSVFileList(Reader reader, Class csvClass) ;

}
