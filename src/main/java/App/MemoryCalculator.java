package App;

import org.openjdk.jol.info.GraphLayout;
import java.util.Vector;

/**
 *
 * @author Andras Tarlos
 * @since 2022.01.12
 * @version 0.1.1
 */
public class MemoryCalculator {

    /**
     *
     * @param arr is the Vector<Integer> array that the memory will be calculated of
     * @return returns the memory-space needed for the Vector<Integer>
     */
    public long getMemorySpace(Vector<Integer> arr) {
        return GraphLayout.parseInstance(arr).getClassSizes().size() * 8;
    }
}
