package App;

import java.util.Vector;

/**
 *
 *
 * @author Andras Tarlos
 * @since 2022.01.12
 * @version 0.1.3
 */
public interface SortingInterface {
    long writeChanges = 0;
    long timeForSorting = 0;
    long amountOfComparisons = 0;
    long storageSpaceRequired = 0;
    /***
     *
     * @param array is the none-sorted array that has to be sorted
     * @return returns the sorted array
     */
    public Vector<Integer> sort(Vector<Integer> array);
    /***
     * @return Gibt Anzahl von Schreibzugriffen zurück
     */
    public long getWriteChanges();
    /***
     *
     * @return Gibt die Zeit für die Sortierung in ms zurück
     */
    public long getTimeForSorting();
    /***
     *
     * @return Gibt die Anzahl Vergleiche zurück
     */
    public long getAmountOfComparisons();
    /***
     *
     * @return Gibt den Speicherbedarf in bit zurück
     */
    public long getStorageSpaceRequired();
}
