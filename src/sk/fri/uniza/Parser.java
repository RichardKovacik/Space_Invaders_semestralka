package sk.fri.uniza;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 27. 2. 2022 - 15:54
 * Treida Parser sluzi na citanie a zapisovanie hracov do/z textoveho suboru
 * @author Richard Kovacik
 */
public class Parser {
    private ZoznamHracov zoznamHracov;
    private static final String PATH_TO_DATA = "/src/data/hraci.txt";
    private static final String PARENT_DIR = System.getProperty("user.dir");
    private static Parser singleton = null;
    private boolean suDataPrazdne;

    /**
     * singleton dovoli vrati prave jednu a tu istu instanciu za cely beh programu
     * @return prave aktualnu instanciu triedy Parser
     */
    public static Parser getInstance() {
        if (singleton == null) {
            singleton = new Parser();
        }
        return singleton;
    }

    /**
     * Konstruktor vytvri novy Parser a inicializuje zoznam hracov
     */
    public Parser() {
        this.zoznamHracov = new ZoznamHracov();
        Path path = Paths.get(PARENT_DIR + PATH_TO_DATA);
        try {
            //vytvorim priecinok pokial neexistuje
            Files.createDirectories(Paths.get(PARENT_DIR + "/src/data"));
            //pokial subor neexistuje vytvorim novy
            if (!Files.exists(path)) {
                Files.createFile(path);
                System.out.println("CREATED: " + PARENT_DIR + PATH_TO_DATA);
                this.suDataPrazdne = true;

            } else {
                //skotrolujem ci sa nachdza nieco v textovom subore
                BufferedReader br = new BufferedReader(new FileReader(PARENT_DIR + PATH_TO_DATA));
                if (br.readLine() != null) {
                    this.suDataPrazdne = false;
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * metoda ulozi aktualneho hraca do textoveho suboru
     * @param hrac aktualny ktory hra hru
     */
    public void ulozHracaDoSub(Hrac hrac) {
        this.zoznamHracov.pridajHraca(hrac);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(new File(PARENT_DIR, PATH_TO_DATA));
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(this.zoznamHracov);
            objectOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * metoda nacita hracov zo suboru ako objekt typu ZoznamHracov a priradi ich do atributu zoznamHracov
     */
    public void nacitajHracovZoSuboru() {
        //ak sa v subori nenechadzaju ziadne data nema zmysel nacitavat z neho
        if (this.suDataPrazdne) {
            return;
        }
        ObjectInputStream objectInputStream = null;
        try (FileInputStream in = new FileInputStream(PARENT_DIR + PATH_TO_DATA)) {
            objectInputStream = new ObjectInputStream(in);
            Object o = objectInputStream.readObject();
            if (o instanceof ZoznamHracov) {
                this.zoznamHracov = (ZoznamHracov)o;
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                assert objectInputStream != null;
                objectInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * meotoda vrati atribut zoznamHracov
     * @return atribut zoznamHracov
     */
    public ZoznamHracov getZoznamHracov() {
        return this.zoznamHracov;
    }

}
