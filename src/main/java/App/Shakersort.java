public class Shakersort {

    public int[] intArr = { 16, 23, 14, 7, 21, 20, 6, 1, 17, 13, 12, 9, 3, 19 };
    int k;

    public int[] sort() {

        int i = 0, l = intArr.length;
        while (i < l) {
            shaker1(i, l);
            l--;
            shaker2(i, l);
            i++;
        }
        return intArr;
    }

    private void shaker1(int i, int l) {
        for (int j = i; j < l - 1; j++) {
            if (intArr[j] > intArr[j + 1]) {
                k = intArr[j];
                intArr[j] = intArr[j + 1];
                intArr[j + 1] = k;
            }
        }
    }

    private void shaker2(int i, int l) {
        for (int j = l - 1; j >= i; j--) {
            if (intArr[j] > intArr[j + 1]) {
                k = intArr[j];
                intArr[j] = intArr[j + 1];
                intArr[j + 1] = k;
            }
        }
    }

    public static void main(String[] args) {
        Shakersort ss = new Shakersort();
        int[] arr = ss.sort();
        for (int i = 0; i < arr.length; i++) {
            System.out.println(i + 1 + ": " + arr[i]);
        }
    }

    @Override
    public Vector<Integer> sort(Vector<Integer> array) {
        return null;
    }

    @Override
    public long getWriteChanges() {
        return 0;
    }

    @Override
    public long getTimeForSorting() {
        return 0;
    }

    @Override
    public long getAmountOfComparisons() {
        return 0;
    }

    @Override
    public long getStorageSpaceRequired() {
        return 0;
    }
}