package App.Utils;

import java.io.*;
import java.util.*;

/**
 * <h1>FileReader</h1>
 *
 * @author Andras Tarlos
 * @author Lorenzo Giuntini (Medox36)
 * @since 2022.01.12
 * @version 0.1.5
 */
public class FileReader {
    private final Vector<Vector<Integer>> fileArrays;
    private final String[] fileNames;

    /***
     * Das ist der Constructor dieser Klasse. Hier werden die benötigte Vectors initialisiert und
     * die gebrauchte Methoden ausgeführt
     */
    public FileReader() {
        fileNames = new String[]{"InversTeilsortiert1000.dat.txt", "InversTeilsortiert10000.dat.txt", "InversTeilsortiert100000.dat.txt",
                "Random1000.dat.txt", "Random10000.dat.txt", "Random100000.dat.txt",
                "Teilsortiert1000.dat.txt", "Teilsortiert10000.dat.txt", "Teilsortiert100000.dat.txt"};
        fileArrays = new Vector<>();

        try {
            addFilesToList();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     *
     * @throws IOException
     */
    public void addFilesToList() throws IOException {
        for (int i = 0; i < 9; i++) {
            readFile(new BufferedReader(new InputStreamReader(Objects.requireNonNull(getClass().getResourceAsStream("/files/" + fileNames[i])))));
        }
    }

    /**
     * Reads all files and puts them in fileArrays
     *
     * @param br is a variable of BufferedReader
     */
    public void readFile(BufferedReader br) {
        Vector<Integer> array = new Vector<>();
        String str;
        while (true) {
            try {
                if ((str = br.readLine()) != null) {
                    array.add(Integer.valueOf(str));
                } else {
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        fileArrays.add(array);
    }

    public Vector<Vector<Integer>> getFileArrays() {
        return fileArrays;
    }

    public String[] getFileNames() {
        return fileNames;
    }
}