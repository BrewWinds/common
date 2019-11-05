package importer.extractor;

/**
 * @Date: 2019/8/20 15:48
 * @Description:
 */
@FunctionalInterface
public interface RowValidator<T> {

    String verify(int rowNumber, T rowData);

}
