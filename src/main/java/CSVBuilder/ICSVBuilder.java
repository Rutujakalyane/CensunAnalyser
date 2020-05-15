package CSVBuilder;

import exception.CensusAnalyserException;

import java.io.Reader;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public interface ICSVBuilder {
    <E> Iterator<E> getIterator(Reader reader, Class<E> csvClass) throws CensusAnalyserException;
}
