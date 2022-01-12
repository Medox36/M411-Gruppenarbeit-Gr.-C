package App;

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
    private Vector<long[]> results;

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
    }

    public Vector<long[]> getSortingResults() {
        for (SortingInterface s: sortingTypes) {
            for (int i = 0; i < fileArrays.size(); i++) {
                s.sort(fileArrays.get(i));
                results.add(new long[] {s.getAmountOfComparisons(), s.getStorageSpaceRequired(), s.getTimeForSorting(), s.getWriteChanges()});
            }
        }
        return results;
    }
}
