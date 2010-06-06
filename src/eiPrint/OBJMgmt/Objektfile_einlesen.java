/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eiPrint.OBJMgmt;

import com.db4o.Db4o;
import java.io.File;
import com.db4o.*;
//import com.db4o.f1.*;
//import eiPrint.OBJMgmt.Util;
import com.db4o.query.*;
import com.db4o.ObjectSet;
import com.db4o.ObjectContainer;
import com.db4o.config.EmbeddedConfiguration;
import eiPrint.OBJMgmt.KinematikDpod.Kinematik;
import eiPrint.OBJMgmt.DateiUmbenennen;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dave
 */
public class Objektfile_einlesen extends Util {

    //Attribute
    private ObjectContainer db;
    //private Koordinatentransformation kt;
    private String pfad;
    private String filename;
    private FileReader fileIn;
    private ArrayList<Point> ptmenge; //Hällt alle Punkte des Druck Objekts
    private double xTmp, yTmp, zTmp;
    private Point point;
    private double xGes, yGes;
    private Kinematik DPOD;
    private int OK = 0;
    private ArrayList steps;
    //from aufbereitung
    private ArrayList<Double> Xsujet;
    private ArrayList<Double> Ysujet;
    private ArrayList<Integer> colorPrint;
    private ArrayList<Integer> write;
    //private String sujet;
    private FileReader fileInSujet;
    ArrayList<Double> xTemp ;
    ArrayList<Double> yTemp ;
    /**
     * Konstruktor
     *
     * @param filename
     * @param pfad
     */
    @SuppressWarnings("empty-statement")
    public Objektfile_einlesen(String pfad,String sujet) {
        db = Db4oEmbedded.openFile(createConfiguration(), "C:/Users/david/Desktop/PREN/DB.yap");
        DPOD = new Kinematik();
        point = new Point();
        ptmenge = new ArrayList<Point>();
        this.pfad = pfad;
        System.out.println("" + pfad);
//        DPOD.xx0 = 0;
//        DPOD.yy0 = 0;
//        DPOD.zz0 = 0;
        // int OK = 0;
        DPOD.theta1 = DPOD.theta2 = DPOD.theta3 = 0.0;
        steps = new ArrayList();

        Xsujet = new ArrayList<Double>();
        Ysujet = new ArrayList<Double>();
        colorPrint = new ArrayList<Integer>();
        write = new ArrayList<Integer>();
        xTemp = new ArrayList<Double>();
        yTemp = new ArrayList<Double>();

        try {
            fileIn = new FileReader(pfad);
            fileInSujet = new FileReader(sujet);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Objektfile_einlesen.class.getName()).log(Level.SEVERE, null, ex);
        }


    }

    /**
     * Das .obj File wird eingelesen und in einen Arraylist abgespeichert.
     * @throws ClassNotFoundException
     */
    @SuppressWarnings("empty-statement")
    public void fileEinlesen() throws ClassNotFoundException, IOException {
        BufferedReader buff = new BufferedReader(fileIn);
        //Speichert den BufferInhalt temporär
        String zeile = "";
        //Lies bis nix mehr da ist (Rückgabewert = null statt String)
        while ((zeile = buff.readLine()) != null) {
            Scanner scanner = new Scanner(zeile);
            if (zeile.startsWith("v ")) {
                //x ,y,z Koordinate isolieren, zaehler richtig nullen!!!
                int zaehler = 0;
                while ((scanner.hasNext())) {
                    if (scanner.hasNextDouble()) {
                        if (zaehler == 0) {
                            xTmp = scanner.nextDouble();
                            //System.out.println(x);
                            zaehler++;
                        } else if (zaehler == 1) {
                            yTmp = scanner.nextDouble();
                            zaehler++;
                            // System.out.println(y);
                        } else if (zaehler == 2) {
                            zTmp = scanner.nextDouble();
                            zaehler = 0;
                            //System.out.println(z);
                        }
                    } else {
                        scanner.next();
                    }
                }
                //x,y,z transformieren
                Koordinatentransformation kt = new Koordinatentransformation(xTmp, yTmp, zTmp);
                kt.koordinatensystemDrehung();
                //koord in point schreiben, wenn kt.getX() übergeben wird werden die
                //Werte unverändert in Db geschrieben, d.h. ohne Koordinatesystemdrehg.
                ptmenge.add(new Point(kt.getXrobot(), kt.getYrobot(), kt.getZrobot()));
                //System.out.println("ptmenge"+point);
            } else {
                zeile += buff.readLine();
                System.out.println("Kein v!!");
            }
        }
        //InputStream schliessen
        buff.close();
        fileIn.close();
        saveToDb();
    }

    public void saveToDb() {
        for(Point p : ptmenge)
        {
            db.store(p);
            System.out.println("Point stored: " +p);
        }
    }

    private EmbeddedConfiguration createConfiguration() {
        EmbeddedConfiguration config = Db4oEmbedded.newConfiguration();
        config.common().objectClass(Point.class).indexed(true);
        config.common().objectClass(Point.class).objectField("x").indexed(true);
        config.common().objectClass(Point.class).objectField("y").indexed(true);
        config.common().objectClass(Point.class).objectField("z").indexed(true);
        return config;
    }

    /**
     * Sucht in der Db nach einem Punkt welcher die x,y-Koordinate möglichst
     * nahe bei null ist.
     * @return
     */
    public List<Point> getOrigin() {
        List<Point> result = db.query(new Predicate<Point>() {

            public boolean match(Point point) {
                return (point.getX() < (0 + 0.1) &&
                        point.getX() > (0 - 0.1) &&
                        point.getY() < (0 + 0.1) &&
                        point.getY() > (0 - 0.1));
            }
        });
        return result;
    }

    /**
     * Druckt alle punkte aus
     *
     */
    public static void retrievePoints(ObjectContainer db) {
        ObjectSet result = db.get(Point.class);
        listResult(result);
    }

    /**
     * Sucht in der DB nach Punkten welche mit den übergebenen x und y
     * Koordinate übereinstimmen
     * @param xGes
     * @param yGes
     * @return Liste aller übereinstimmenden Punkte mit 0.2mm Tolleranz
     */
    public List<Point> getZkoordinate(double xGes, double yGes) {
        this.xGes = xGes;
        this.yGes = yGes;
        List<Point> result = db.query(new Predicate<Point>() {

            public boolean match(Point point) {
                return (point.getX() < (Objektfile_einlesen.this.xGes + 0.1) &&
                        point.getX() > (Objektfile_einlesen.this.xGes - 0.1) &&
                        point.getY() < (Objektfile_einlesen.this.yGes + 0.1) &&
                        point.getY() > (Objektfile_einlesen.this.yGes - 0.1));
            }
        });
        return result;
    }

    /**
     * Diese Methode erwartet die Koordinaten eines Punktes im Raum und
     * berechnet wieviele Steps welcher Motor drehen muss und speichert die
     * Steps in eine ArrayList namens steps
     * @param x
     * @param y
     * @param z
     */
    public void getSteps(double x, double y, double z,int color) {
        int stepsMotor1, stepsMotor2, stepsMotor3;
        int write = 1 ;
        //Dpod erwarted floats
        DPOD.x0 = x;
        DPOD.y0 = y;
        DPOD.z0 = z;
        OK = DPOD.delta_calcInverse(DPOD);
        stepsMotor1 = (int) ((DPOD.theta1) / 0.9);
        stepsMotor2 = (int) ((DPOD.theta2) / 0.9);
        stepsMotor3 = (int) ((DPOD.theta3) / 0.9);
        steps.add(stepsMotor1);
        steps.add(stepsMotor2);
        steps.add(stepsMotor3);
        steps.add(color);
        steps.add(write);
        System.out.println(stepsMotor1);
        System.out.println(stepsMotor2);
        System.out.println(stepsMotor3);
    }


    /**
     * @return the filename
     */
    public String getFilename() {
        return filename;
    }

    /**
     * @param filename the filename to set
     */
    public void setFilename(String filename) {
        this.filename = filename;
    }

    @Override
    protected void finalize() {
        db.close();
    }
        /**
     *
     * @throws IOException
     */
    public void sujetEinlesen() throws IOException {
//        ArrayList<Double> xTemp = new ArrayList<Double>();
//        ArrayList<Double> yTemp = new ArrayList<Double>();
        BufferedReader buff = new BufferedReader(fileInSujet);
        //Speichert den BufferInhalt temporär
        String zeile = "";
//        int i = 0;
        //Lies bis nix mehr da ist (Rückgabewert = null statt String)
        while ((zeile = buff.readLine()) != null) {
            Scanner scanner = new Scanner(zeile);
            if (!zeile.isEmpty()) {
                int count = 0;
               while ((scanner.hasNext())) {
                if (scanner.hasNextDouble()) {

                    if (count == 0) {
                        //speichere x-Koord
                        xTemp.add(scanner.nextDouble());
                        count++;
//                        System.out.println(xTemp.get(i));
                    }
                    if (count == 1) {
                        yTemp.add(scanner.nextDouble());
                        count++;
//                        System.out.println(yTemp.get(i));
                    }
                    if (count == 2) {
                        colorPrint.add(scanner.nextInt());
                        write.add(scanner.nextInt());
//                        System.out.println(colorPrint.get(i));
//                        System.out.println(colorPrint.get(i+1));
//                       ++i;
                    } else {
                        //scanner.next();
                    }
                } else {
                    zeile += buff.readLine();
                    count = 0;
                }
            }
            } else {
                zeile += buff.readLine();
                //System.out.println("Neue Zeile");
            }
        }
        for(int j=0;j<xTemp.size()-1;j++){
            Xsujet.add((xTemp.get(j+1)-xTemp.get(j)));
            Ysujet.add((yTemp.get(j+1)-yTemp.get(j)));
            System.out.print(Xsujet.get(j));
            System.out.print(Ysujet.get(j));
            System.out.println();
        }
    }
    
     public void generiereDruckdaten()
    {
        double xStart = 0.0;
        double yStart = 0.0;
        double zStart = 0.0;
         
        for(int i=0; i<Xsujet.size();i++)
        {
             getSteps(xStart+Xsujet.get(i), yStart+Ysujet.get(i), zStart+(getZkoordinate(Xsujet.get(i),Ysujet.get(i)).get(0).getZ()),1);
             xStart = xStart+Xsujet.get(i);
             yStart = yStart+Ysujet.get(i);
             zStart = zStart+(getZkoordinate(Xsujet.get(i),Ysujet.get(i)).get(0).getZ());
        }
    }

    /**
     *  simple Methode um zu überprüfen wie ArrayLists abgefüllt wurden
     */
    public void printArrayLists()
     {
         for(int i=0;i<Xsujet.size();i++){
            System.out.print("X: ");
            System.out.print(Xsujet.get(i));
            System.out.print(" ");
            System.out.print("Y: ");
            System.out.print(Ysujet.get(i));
            System.out.print(" ");

         }
         for(int i=0; i<colorPrint.size();i++){
            System.out.print("Color an Write: ");
            System.out.print(colorPrint.get(i));
            System.out.print(" ");
            System.out.print(write.get(i));
            System.out.println();
         }
     }

    public static void main(String[] args) {
//C:\Users\david\Desktop\PREN\eiPrint\src\eiPrint\OBJMgmt\data
//C:\Users\david\Desktop\PREN\eiPrint\src\eiPrint\OBJMgmt\data
        //C:/Users/david/Desktop/PREN/eiPrint/src/eiPrint/OBJMgmt/data/sujet.txt
      // DateiUmbenennen rename = new DateiUmbenennen();
       //rename.RenameFile("C:/Users/david/Desktop/PREN/eiPrint/src/eiPrint/OBJMgmt/data/");//Pfad ohne dateiname
      Objektfile_einlesen o = new Objektfile_einlesen("C:/Users/david/Desktop/PREN/eiPrint/src/eiPrint/OBJMgmt/data/BallonMaxV1.txt","C:/Users/david/Desktop/PREN/eiPrint/src/eiPrint/OBJMgmt/data/sujet.txt");
//        ObjectContainer db=Db4o.openFile("C:/Users/david/Desktop/PREN/eiPrint/src/eiPrint/OBJMgmt/data/BallonMaximalkorrigiert.txt");
//147.253174 113.367676 174.822464
     // try {
            //o.fileEinlesen();
            //o.saveToDb();
           // Startpunkt suchen
           //System.out.println( o.getOrigin().get(0));
           //Point: 0.0034228569986112234 0.001378 291.4954994853929

            //Startpunkt übergeben
             //List<Point> lst = o.getZkoordinate(-182.9859, 0.2033183);
            //System.out.println(lst.size());
            //System.out.println(lst.get(0).getZ());
            //Umrechnung in Schritte
            //Kinematik DPOD = new Kinematik();
           //  o.getSteps(0.0034228569986112234, 0.001378, 291.4954994853929,1);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//        }
       //Druckbefehle_aufbereitung d = new Druckbefehle_aufbereitung("C:/Users/david/Desktop/PREN/eiPrint/src/eiPrint/OBJMgmt/data/sujet.txt");
        try {
            System.out.println( o.getOrigin().get(0));
            Kinematik DPOD = new Kinematik();
            o.getSteps(0.0034228569986112234, 0.001378, 291.4954994853929,1);
//            o.sujetEinlesen();
//            System.out.println("success1!!!");
//            o.printArrayLists();
//            System.out.println("success2!!!");
//            o.generiereDruckdaten();
//            System.out.println("success3!!!");
//        } catch (IOException ex) {
//            Logger.getLogger(Druckbefehle_aufbereitung.class.getName()).log(Level.SEVERE, null, ex);
//        }
        }catch (Exception e){
          e.printStackTrace();
      }

    }
}
