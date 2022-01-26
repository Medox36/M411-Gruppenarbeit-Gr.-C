package App.Utils;

import org.openjdk.jol.info.GraphLayout;
import java.util.Vector;

/**
 * <h1>MemoryCalculator</h1>
 * This class uses Java Object Layout as a dependency and uses it to get the memory used by a Vector
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
