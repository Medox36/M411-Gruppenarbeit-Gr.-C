package App.SortClasses;

import App.MemoryCalculator;

import java.util.Vector;

/**
 *
 *
 * @author Andras Tarlos
 * @since 2022.01.12
 * @version 0.1.3
 */
public interface SortingInterface {
    MemoryCalculator mc = new MemoryCalculator();
    /***
     *
     * @param array is the none-sorted array that has to be sorted
     * @return returns the sorted array
     */
    public void sort(Vector<Integer> array);
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
