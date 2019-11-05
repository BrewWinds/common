package importer.extractor;

/**
 * @Description:
 */
@FunctionalInterface
public interface RowProcessor<T> {

    void process(int rowNum, T rowData);
}
