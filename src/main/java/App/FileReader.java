package App;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;

/**
 *
 *
 * @author Andras Tarlos
 * @since 2022.01.12
 * @version 0.1.3
 */
public class FileReader {
    private Vector<File> files;
    private final Vector<Vector<Integer>> fileArrays;
    private final String[] fileNames;

    /***
     * Das ist der Constructor dieser Klasse. Hier werden die benötigte Vectors initialisiert und
     * die gebrauchte Methoden ausgeführt
     */
    public FileReader() {
        fileNames = new String[]{"InversTeilsortiert1000.txt", "InversTeilsortiert10000.txt", "InversTeilsortiert100000.txt",
                "Random1000.txt", "Random10000.txt", "Random100000.txt",
                "Teilsortiert1000.txt", "Teilsortiert10000.txt", "Teilsortiert100000.txt"};
        files = new Vector<>();
        fileArrays = new Vector<>();

        try {
            addFilesToList();
            readFiles();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addFilesToList() throws IOException {
        File[] listOfFiles = new File[9];
        for (int i = 0; i < 9; i++) {
            System.out.println(FileReader.class.getResource("/Files/" + fileNames[i]));
            try {
                listOfFiles[i] = new File(Objects.requireNonNull(FileReader.class.getResource("/Files/" + fileNames[i])).toURI());
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            System.out.println(Objects.requireNonNull(listOfFiles[i]).isFile() + " " + listOfFiles[i].canRead() + " " + listOfFiles[i].isDirectory());
        }

        Collections.addAll(files, listOfFiles);


    }

    public void readFiles() throws FileNotFoundException {
        Vector<Integer> array;
        BufferedReader br;
        for (File f: files) {
            array = new Vector<>();
            br = new BufferedReader(new java.io.FileReader(f));
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
    }

    public Vector<Vector<Integer>> getFileArrays() {
        return fileArrays;
    }

    public String[] getFileNames() {
        return fileNames;
    }
}