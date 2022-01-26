package App.Utils;

import java.util.Vector;

public class ArrayCopier {
    public int[] copyVectorToArray(Vector<Integer> array) {
        int[] arr = new int[array.size()];
        for (int i = 0; i < array.size(); i++) {
            arr[i] = array.get(i);
        }
        return arr;
    }
}
