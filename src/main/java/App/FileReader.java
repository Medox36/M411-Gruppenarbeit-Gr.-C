package App;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Vector;

/**
 *
 *
 * @author Andras Tarlos
 * @since 2022.01.12
 * @version 0.1.3
 */
public class FileReader {
    private final Vector<File> files;
    private final Vector<Vector<Integer>> fileArrays;
    private String[] fileNames;

    /***
     * Das ist der Constructor dieser Klasse. Hier werden die benötigte Vectors initialisiert und
     * die gebrauchte Methoden ausgeführt
     */
    public FileReader() {
        fileNames = new String[9];
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
        File folder = new File(new File("./src/main/resources/Files/").getCanonicalPath());
        File[] listOfFiles = folder.listFiles();
        //FileReader.class.getResource("");

        assert listOfFiles != null;
        for (File file : listOfFiles) {
            if (file.isFile()) {
                files.add(file);
            }
        }

        for (int i = 0; i < files.size(); i++) {
            fileNames[i] = files.get(i).getName();
        }
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