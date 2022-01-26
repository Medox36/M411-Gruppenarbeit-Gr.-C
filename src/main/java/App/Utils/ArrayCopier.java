package App.Utils;

import java.util.Vector;

/**
 * <h1>ArrayCopier</h1>
 * This class only purpose is to copy a Vector<Integer> into a plain int[]
 *
 * @author Tarlos Andras
 * @since 2022.01.26
 * @version 0.1.2
 */
public class ArrayCopier {
    /**
     * This method copies a Vector<Integer> into a normal int array
     *
     * @param array is the
     * @return the int[] array copied from the Vector
     */
    public int[] copyVectorToArray(Vector<Integer> array) {
        int[] arr = new int[array.size()];
        for (int i = 0; i < array.size(); i++) {
            arr[i] = array.get(i);
        }
        return arr;
    }
}
