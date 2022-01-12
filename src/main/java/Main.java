import java.util.Vector;

/**
 *
 *
 * @author Andras Tarlos
 * @since 2022.01.12
 * @version 0.1.3
 */
public class Main {
    private SortingInterface[] sortingTypes;
    private Vector<Vector<Integer>> fileArrays;
    private FileReader reader;
    private Vector<Vector<Long>> results;

    public static void main(String[] args) {
        new Main().startApp();
    }

    public void startApp() {
        reader = new FileReader();
        fileArrays = reader.getFileArrays();

        sortingTypes = new SortingInterface[fileArrays.size()];
        /*
        sortingTypes[0] = new ....
        sortingTypes[1] = new ....
        sortingTypes[2] = new ....
        sortingTypes[3] = new ....
        sortingTypes[4] = new ....
        sortingTypes[5] = new ....
        sortingTypes[6] = new ....
        sortingTypes[7] = new ....
        sortingTypes[8] = new ....

         */

        for (SortingInterface s: sortingTypes) {
            for (Vector<Integer> v: fileArrays) {
                s.sort(v);
                s.getAmountOfComparisons();
                s.getStorageSpaceRequired();
                s.getTimeForSorting();
                s.getWriteChanges();
            }
        }
    }

    public Vector<Vector<Long>> getSortingResults() {
        return results;
    }
}
