public interface SortingInterface {

    /***
     * @return Gibt Anzahl von Schreibzugriffen zurück
     */
    public int getWriteChanges();
    /***
     *
     * @return Gibt die Zeit für die Sortierung in ms zurück
     */
    public long getTimeForSorting();
    /***
     *
     * @return Gibt die Anzahl Vergleiche zurück
     */
    public int getAmountOfComparisons();
    /***
     *
     * @return Gibt den Speicherbedarf in bit zurück
     */
    public long getStorageSpaceRequired();
}
