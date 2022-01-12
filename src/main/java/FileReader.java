import java.io.File;
import java.io.IOException;
import java.util.Vector;

public class FileReader {
    private final Vector<File> files;
    public FileReader() {

        files = new Vector<>();
        try {
            addFilesToList();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (File file: files) {
            System.out.println(file.getName());
        }
    }

    public Vector<Integer[]> getList() {

        return
    }

    public void addFilesToList() throws IOException{
        File folder = new File(new File("./src/main/resources/Files/").getCanonicalPath());
        File[] listOfFiles = folder.listFiles();

        assert listOfFiles != null;
        for (File file : listOfFiles) {
            if (file.isFile()) {
                files.add(file);
            }
        }
    }
}