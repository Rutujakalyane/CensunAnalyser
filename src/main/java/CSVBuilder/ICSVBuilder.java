package CSVBuilder;

package CSVBuilder;

import java.io.Reader;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public interface ICSVBuilder <E> {
    Iterator<E> getCSVFileIterator(Reader reader, Class<E> csvStatesClass);
    //List<E> getCSVFileList(Reader reader, Class csvClass) ;


}