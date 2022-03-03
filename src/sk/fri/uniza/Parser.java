package sk.fri.uniza;

import sk.fri.uniza.gui.Hra;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;

/**
 * 27. 2. 2022 - 15:54
 *
 * @author richard
 */
public class Parser {
    private Profil profil;
    private static final String PATH_TO_DATA = "/src/data/hraci.txt";
    private static final String PARENT_DIR = System.getProperty("user.dir");
    private static Parser singleton = null;
    private boolean suDataPrazdne;

    public static Parser getInstance() {
        if (singleton == null) {
            singleton = new Parser();
        }
        return singleton;
    }

    public Parser() {
        this.profil = new Profil();
        Path path = Paths.get(PARENT_DIR + PATH_TO_DATA);
        try {
            Files.createDirectories(Paths.get(PARENT_DIR + "/src/data"));
            //pokial subor neexistuje vytvorim novy
            if (!Files.exists(path)) {
                Files.createFile(path);
                System.out.println("CREATED: " + PARENT_DIR + PATH_TO_DATA);
                this.suDataPrazdne = true;

            } else {
                BufferedReader br = new BufferedReader(new FileReader(PARENT_DIR + PATH_TO_DATA));
                if (br.readLine() != null) {
                    this.suDataPrazdne = false;

                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void ulozHracaDoSub(Hrac hrac) {
        this.profil.pridajHraca(hrac);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(new File(PARENT_DIR, PATH_TO_DATA));
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(this.profil);
            objectOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void nacitajHracovZoSuboru() {
        //ak sa v subori nenechadzaju ziadne data nema zmysel nacitavat z neho
        if (this.suDataPrazdne) {
            return;
        }
        ObjectInputStream objectInputStream = null;
        try (FileInputStream in = new FileInputStream(PARENT_DIR + PATH_TO_DATA)) {
            objectInputStream = new ObjectInputStream(in);
            Object o = objectInputStream.readObject();
            if (o instanceof Profil) {
                this.setProfil((Profil)o);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            objectInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.utriedHracovPodlaSkore();

    }
    //utried hracov podla skore od najvacsieho po najmensi
    private void utriedHracovPodlaSkore() {
        this.profil.getZoznamHracov().sort(Comparator.comparingInt(Hrac::getScore).reversed());
    }

    public Profil getProfil() {
        return this.profil;
    }

    public void setProfil(Profil profil) {
        this.profil = profil;
    }
}
