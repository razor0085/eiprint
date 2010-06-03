
package eiPrint.OBJMgmt;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dave
 */
public class Druckbefehle_aufbereitung {
    private FileReader fileIn;
    private String pfad;
    private ArrayList Xsujet;
    private ArrayList Ysujet;
    private ArrayList colorPrint;

    /**
     *
     * @param pfad
     */
    public Druckbefehle_aufbereitung(String pfad)
    {
        Xsujet = new ArrayList();
        Ysujet = new ArrayList();
        colorPrint = new ArrayList();
        this.pfad = pfad ;
        try {
            fileIn = new FileReader(pfad);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Druckbefehle_aufbereitung.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


  
    /**
     *
     * @throws IOException
     */
    public void sujetEinlesen() throws IOException {
        BufferedReader buff = new BufferedReader(fileIn);
        //Speichert den BufferInhalt temporär
        String zeile = "";
        //int i = 0;
        //Lies bis nix mehr da ist (Rückgabewert = null statt String)
        while ((zeile = buff.readLine()) != null) {
            Scanner scanner = new Scanner(zeile);
            if (!zeile.isEmpty()) {
                //x ,y,z Koordinate isolieren, zaehler richtig nullen!!!
                int count = 0;
                
               while ((scanner.hasNext())) {
                if (scanner.hasNextInt()) {

                    if (count == 0) {
                        //speichere x-Koord
                        Xsujet.add(scanner.nextInt());
                        count++;
                        //System.out.println(Xsujet.get(i));
                    }
                    if (count == 1) {
                        Ysujet.add(scanner.nextInt());
                        count++;
                        //System.out.println(Ysujet.get(i));
                    }
                    if (count == 2) {
                        colorPrint.add(scanner.nextInt());
                        colorPrint.add(scanner.nextInt());
//                        System.out.println(colorPrint.get(i));
//                        System.out.println(colorPrint.get(i+1));
//                        ++i;

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
                System.out.println("Neue Zeile");
            }
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
            System.out.print("Color an Write: ");
            System.out.print(colorPrint.get(i));
            System.out.print(" ");
            System.out.print(colorPrint.get(i+1));
            System.out.println();
         }
     }

     


     /**
      *
      * @param args
      * @throws IOException
      * @throws ClassNotFoundException
      */
     public static void main(String[] args) throws IOException, ClassNotFoundException
{

    Druckbefehle_aufbereitung d = new Druckbefehle_aufbereitung("C:/Users/david/Desktop/PREN/eiPrint/src/eiPrint/OBJMgmt/data/sujet.txt");
    d.sujetEinlesen();
    d.printArrayLists();
}


}
