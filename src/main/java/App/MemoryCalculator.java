package App;

import org.openjdk.jol.info.GraphLayout;
import java.util.Vector;

public class MemoryCalculator {
    public MemoryCalculator() {}
    /**
     *
     * @param arr is the Vector<Integer> array that the memory will be calculated of
     * @return returns the memory-space needed for the Vector<Integer>
     */
    public long getMemorySpace(Vector<Integer> arr) {
        return GraphLayout.parseInstance(arr).getClassSizes().size() * 8;
    }
}
