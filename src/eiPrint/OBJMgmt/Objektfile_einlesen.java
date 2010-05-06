/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package eiPrint.OBJMgmt;
import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author dave
 */
public class Objektfile_einlesen {
    
    //Attribute
    private String pfad;
    private String filename;
    private FileReader fileIn;
    private ArrayList<String> list;
  
/**
 * Konstruktor
 * @param filename
 * @param pfad
 */
    public Objektfile_einlesen(String pfad)
    {
        list = new ArrayList<String>();
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
    public void fileEinlesen() throws ClassNotFoundException, IOException
    {
        String suchStr = "v";
//		//braucht es try catch wirklich?
//        try {
//             fileIn = new FileReader(pfad);
//        } catch (FileNotFoundException ex) {
//            Logger.getLogger(Objektfile_einlesen.class.getName()).log(Level.SEVERE, null, ex);
//        }
        BufferedReader buff = new BufferedReader(fileIn);

        //Speichert den BufferInhalt temporär
        String tmp = "";

        //Lies bis nix mehr da ist (Rückgabewert = null statt String)
        while ((tmp = buff.readLine()) != null)
        {
        //Mach irgendwas mit der eingelesenen Zeile (speichern, splitten o.ä.)
            list.add(tmp);
        }

        //InputStream schliessen
        buff.close();
        fileIn.close();
    }

    /**
     * Suche nach der nächsten Koordinate ev. in fileEinlesen drin
     */
    public void leseKoordinate()
    {
        int count = 0;
        String suchStr = "v";
        while (count < 50) {
            for (int i = 0; i < list.size(); i++)
            {
                count++;

                if(list.get(i).equalsIgnoreCase(suchStr))
                {
                    System.out.println(list.get(i));
                }
                


            }
        }
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

    @SuppressWarnings("static-access")
   public static void main(String[] args)
   {
       String path = "C:/Dokumente und Einstellungen/MrCrow/Eigene Dateien/My Dropbox/scoula/PREN2/Pojekt/eiPrint/src/eiPrint/OBJMgmt/data/BallonMaximalkorrigiert.txt";
       System.out.println("Start");
       Objektfile_einlesen file = new Objektfile_einlesen(path);
       try
       {
        file.fileEinlesen();
       }
       catch(Exception e){
        System.out.println("something went wrong!");
        }
       file.leseKoordinate();
    }
}
