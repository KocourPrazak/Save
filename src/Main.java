import Game.GameProgress;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    static final String INSTALLPATH = "D:\\JGame";
    static final String SAVEGAMES = INSTALLPATH + "\\savegames";

    public static void main(String[] args) {
        GameProgress gameProgress1 = new GameProgress(100, 3, 8, 12.3);
        GameProgress gameProgress2 = new GameProgress(90, 5, 9, 18.2);
        GameProgress gameProgress3 = new GameProgress(75, 2, 11, 32.8);

        saveGame(gameProgress1, SAVEGAMES + "\\save1.dat");
        saveGame(gameProgress2, SAVEGAMES + "\\save2.dat");
        saveGame(gameProgress3, SAVEGAMES + "\\save3.dat");

        String[] fileList = {SAVEGAMES + "\\save1.dat", SAVEGAMES + "\\save3.dat"};
        zipFiles(fileList, SAVEGAMES + "\\savegame.zip");

        for (String name : fileList) {
            System.out.print("Delete " + name);
            File file = new File(name);
            if (file.delete()) {
                System.out.println(" OK");
            } else System.out.println(" Fail!");
        }

        // write your code here
    }

    public static void saveGame(GameProgress gameProgress, String fileName) {

        try (FileOutputStream fos = new FileOutputStream(fileName);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            System.out.println("Saving game progress to " + fileName);
            oos.writeObject(gameProgress);
        } catch (Exception ex) {
            System.out.println(ex.getLocalizedMessage());
        }
    }

    public static void zipFiles(String[] fileList, String archiveName) {
        System.out.println("Adding files to archive " + archiveName);

        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(archiveName))) {
            for (String name : fileList) {

                int namePos = name.lastIndexOf("\\");
                String sname = "";

                if (namePos > 0) {
                    sname = name.substring(namePos + 1);
                }

                System.out.println("adding " + name);

                try (FileInputStream fis = new FileInputStream(name)) {
                    ZipEntry zen = new ZipEntry(sname);
                    zout.putNextEntry(zen);
                    byte[] buffer = new byte[fis.available()];
                    fis.read(buffer);
                    zout.write(buffer);
                    zout.closeEntry();
                } catch (Exception ex) {
                    System.out.println(ex.getLocalizedMessage());
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getLocalizedMessage());
        }
    }
}
