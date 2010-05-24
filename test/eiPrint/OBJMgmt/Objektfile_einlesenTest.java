/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package eiPrint.OBJMgmt;

import java.util.Scanner;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author dave
 */
public class Objektfile_einlesenTest {

    public Objektfile_einlesenTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of fileEinlesen method, of class Objektfile_einlesen.
     */
    @Test
    public void testFileEinlesen() throws Exception {
        System.out.println("fileEinlesen");
        Objektfile_einlesen instance = null;
        instance.fileEinlesen();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getFilename method, of class Objektfile_einlesen.
     */
    @Test
    public void testGetFilename() {
        System.out.println("getFilename");
        Objektfile_einlesen instance = null;
        String expResult = "";
        String result = instance.getFilename();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setFilename method, of class Objektfile_einlesen.
     */
    @Test
    public void testSetFilename() {
        System.out.println("setFilename");
        String filename = "";
        Objektfile_einlesen instance = null;
        instance.setFilename(filename);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of main method, of class Objektfile_einlesen.
     */
    @Test
    public void testMain() {
        System.out.println("main");
        String[] args = null;
        Objektfile_einlesen.main(args);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @SuppressWarnings("static-access")
   public static void main(String[] args)
   {

       //various tests

       //String path = "C:/Dokumente und Einstellungen/dave/Desktop/PREN/eiPrint/src/eiPrint/OBJMgmt/data/BallonMaximalkorrigiert.txt";
//       System.out.println("Start");
       //Objektfile_einlesen file = new Objektfile_einlesen(path);
//       try
//       {
//        file.fileEinlesen();
//       }
//       catch(Exception e){
//        System.out.println("something went wrong!");
//        }
//       file.leseKoordinate();
       //nach leerzeichen abtrennen
//       String savedLine = "v 55.474472 120.473312 228.522141";
//       String[] segs = savedLine.split(Pattern.quote(" "));
//       System.out.println( Arrays.toString(segs) ); // [v, 55.474472, 120.473312, 228.522141]

            Scanner scanner = new Scanner("Bla test /n" +
                    "v 55.474472 120.473312 0/n " +
                    "v 0.0 1.11 2.22 /n" + "# 2d texture coordinates (x,y): /n"
                    +"vt 0.394984 0.815900 " );
            int zaehler = 0;
            while ((scanner.hasNext())) {
                if (scanner.hasNextDouble()) {
                    if (zaehler == 0) {
                        System.out.println( scanner.nextDouble());
                        zaehler++;
                    } else if (zaehler == 1) {
                       System.out.println( scanner.nextDouble());
                        zaehler++;
                    } else if (zaehler == 2) {
                       System.out.println( scanner.nextDouble());
                        zaehler=0;
                    }
                } else {
                    scanner.next();

                }
            }




    }

}