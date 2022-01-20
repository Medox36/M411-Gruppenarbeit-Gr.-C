package App.Excel;

/**
 *
 *
 * @author Lorenzo Giuntini (Medox36)
 * @since 2022.01.19
 * @version 0.0.1
 */
public class DataArraySyntaxException extends Exception {
    public DataArraySyntaxException() {
        super("The Vector<long[]> is inconsistent and was found not to contain the data in the right order.");
    }
}