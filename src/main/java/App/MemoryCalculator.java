package App;

import org.openjdk.jol.info.GraphLayout;

import java.util.Vector;

public class MemoryCalculator {
    public MemoryCalculator() {}

    public long calcMem(Vector<Integer> arr) {
        System.out.println(GraphLayout.parseInstance(arr).toPrintable());
        return 0;
    }
}
