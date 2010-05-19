/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eiPrint.OBJMgmt;

import com.db4o.Db4o;
import java.io.File;
import com.db4o.*;
//import com.db4o.f1.*;
import eiPrint.OBJMgmt.Util;
import com.db4o.query.*;
import com.db4o.ObjectSet;
import com.db4o.ObjectContainer;
import com.db4o.config.EmbeddedConfiguration;
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
    private double x,y,z=0;
    private Point point;
    private double [] pt;
    private double xGes,yGes;

/**
 * Konstruktor
 *
 * @param filename
 * @param pfad
 */
    @SuppressWarnings("empty-statement")
    public Objektfile_einlesen(String pfad)
    {

        db = Db4oEmbedded.openFile(createConfiguration(),"C:/Users/david/Desktop/PREN/DB.yap");

        point = new Point();
        ptmenge = new ArrayList<Point>();
        this.pfad = pfad;
        System.out.println(""+pfad);
        try {
            fileIn = new FileReader(pfad);


        } catch (FileNotFoundException ex) {
            Logger.getLogger(Objektfile_einlesen.class.getName()).log(Level.SEVERE, null, ex);
        }



    }
    /**
     * Das .obj File wird eingelesen und in einen Arraylist abgespeichert.
     * @throws ClassNotFoundException
     */
    @SuppressWarnings("empty-statement")
    public void fileEinlesen() throws ClassNotFoundException, IOException
    {
        
        BufferedReader buff = new BufferedReader(fileIn);
        //Speichert den BufferInhalt temporär
        String zeile = "";
        //Lies bis nix mehr da ist (Rückgabewert = null statt String)
        while ((zeile = buff.readLine()) != null)
        {

            Scanner scanner = new Scanner(zeile);
            if(zeile.startsWith("v ")  ){
            //x ,y,z Koordinate isolieren, zaehler richtig nullen!!!       
            int zaehler = 0;
            while ((scanner.hasNext())) {
                if (scanner.hasNextDouble()) {
                    if (zaehler == 0) {
                        x = scanner.nextDouble();
                        //System.out.println(x);
                        zaehler++;
                    } else if (zaehler == 1) {
                        y = scanner.nextDouble();
                        zaehler++;
                       // System.out.println(y);
                    } else if (zaehler == 2) {
                        z = scanner.nextDouble();
                        zaehler=0;
                        //System.out.println(z);
                    }

                } else {
                    scanner.next();
                   }
            }

         //x,y,z transformieren
         Koordinatentransformation kt = new Koordinatentransformation(x,y,z);
         kt.koordinatensystemDrehung();
        //koord in point schreiben
//        point.setX(x);
//        point.setY(y);
//        point.setZ(z);
            point.setX(kt.getX());
            point.setY(kt.getY());
            point.setZ(kt.getZ());

            ptmenge.add(new Point(x,y,z));
           //System.out.println("ptmenge"+point);
            
            }
            else{
                
                zeile+=buff.readLine();
                System.out.println("Kein v!!");
            }
      
        }
       
        //InputStream schliessen
          buff.close();
          fileIn.close();
          saveToDb();
    }

    public void saveToDb()
    {

        try {
            // do something with db4o
           for(int i =0; i<ptmenge.size();i++){
                db.store(new Point(ptmenge.get(i).getX(),ptmenge.get(i).getY()
                        ,ptmenge.get(i).getZ()));  // Hier wird das Objekt gespeichert 
            System.out.println("Stored " + (ptmenge.get(i).getX()+" "+
                    ptmenge.get(i).getY() +" " + ptmenge.get(i).getZ()));
           }
           //db.commit();
        } finally {
            
        }
    }

    private EmbeddedConfiguration createConfiguration()
    {
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
    public double getOrigin()
    {
//        for(int i = 0; i<ptmenge.size();i++){
//       if(ptmenge.get(i).getX()==0 && ptmenge.get(i)getY()==0){
//
//        return 0.0;
//       }
//        }
        return 0;
    }
    /**
     * Druckt alle punkte aus
     *
     */
    public static void retrievePoints(ObjectContainer db) {
        ObjectSet result = db.get(Point.class);
        listResult(result);
    }

    public List<Point> getZkoordinate(double xGes, double yGes) {
        this.xGes = xGes;
        this.yGes = yGes;
        List<Point> result = db.query(new Predicate<Point>() {
            public boolean match(Point point) {
                return (point.getX() == Objektfile_einlesen.this.xGes &&
                        point.getY() == Objektfile_einlesen.this.yGes);
            }
        });
        return result;
    }

    //    public Point getZballon()
//    {
////        if(ptmenge.get(i).getX()){
////        return z;
////        }
//        List<Point> points = db.query(new Predicate<Point>() {
//
//            public boolean match(Point point) {
//                return ((point.getX() == 100)&& point.getY()==100);
//            }
//        });
//    }

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

   public static void main(String[] args)
   {
//       //C:/Dokumente und Einstellungen/dave/Desktop/PREN/eiPrint/src/eiPrint/OBJMgmt/data
//       Objektfile_einlesen o = new Objektfile_einlesen("C:/Users/david/Desktop/PREN/eiPrint/src/eiPrint/OBJMgmt/data/BallonMaximalkorrigiert.txt");
//       try{
//       o.fileEinlesen();
//       //o.saveToDb();
//       }catch(Exception e){
//           e.printStackTrace();
//       }

      Objektfile_einlesen o = new Objektfile_einlesen("C:/Users/david/Desktop/PREN/eiPrint/src/eiPrint/OBJMgmt/data/BallonMaximalkorrigiert.txt");
       //ObjectContainer db=Db4o.openFile("C:/Users/david/Desktop/PREN/eiPrint/src/eiPrint/OBJMgmt/data/BallonMaximalkorrigiert.txt");
        try {
            //o.fileEinlesen();
           //o.saveToDb();
          List<Point> lst =  o.getZkoordinate(170.233231,354.467102);
           System.out.println(lst.size()); 
           System.out.println(lst.get(0).getZ());
        }
        catch(Exception e){e.printStackTrace();}
        finally {
            
        }


   }

   @Override
   protected void finalize()
   {
       db.close();
   }
}
