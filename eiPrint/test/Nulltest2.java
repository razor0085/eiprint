/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



/**
 *
 * @author padrun
 */
// derived from SUN's examples in the javax.comm package
import java.io.*;
import java.util.*;
//import javax.comm.*; // for SUN's serial/parallel port libraries
import gnu.io.*; // for rxtxSerial library
import java.util.logging.Level;
import java.util.logging.Logger;

public class Nulltest2 implements Runnable, SerialPortEventListener {
   static CommPortIdentifier portId;
   static CommPortIdentifier saveportId;
   static Enumeration        portList;
   InputStream           inputStream;
   SerialPort           serialPort;
   Thread           readThread;

   //static String        messageString = "that's a,test!";
   static byte              print;
   static byte              testByes;
   static OutputStream      outputStream;
   static boolean        outputBufferEmptyFlag = false;

   public static void main(String[] args) {
      boolean           portFound = false;
      String           defaultPort;

      // determine the name of the serial port on several operating systems
      String osname = System.getProperty("os.name","").toLowerCase();
      if ( osname.startsWith("windows") ) {
         // windows
         defaultPort = "COM1";
      } else if (osname.startsWith("linux")) {
         // linux
        defaultPort = "/dev/ttyS0";
      } else if ( osname.startsWith("mac") ) {
         // mac
         defaultPort = "????";
      } else {
         System.out.println("Sorry, your operating system is not supported");
         return;
      }

      if (args.length > 0) {
         defaultPort = args[0];
      }

      System.out.println("Set default port to "+defaultPort);

		// parse ports and if the default port is found, initialized the reader
      portList = CommPortIdentifier.getPortIdentifiers();
      while (portList.hasMoreElements()) {
         portId = (CommPortIdentifier) portList.nextElement();
         if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
            if (portId.getName().equals(defaultPort)) {
               System.out.println("Found port: "+defaultPort);
               portFound = true;
               // init reader thread
                Nulltest2 reader = new Nulltest2();

            }
         }

      }
      if (!portFound) {
         System.out.println("port " + defaultPort + " not found.");
      }

   }

   public void initwritetoport() {
      // initwritetoport() assumes that the port has already been opened and
      //    initialized by "public nulltest()"

      try {
         // get the outputstream
         outputStream = serialPort.getOutputStream();
      } catch (IOException e) {}

      try {
         // activate the OUTPUT_BUFFER_EMPTY notifier
         serialPort.notifyOnOutputEmpty(true);
      } catch (Exception e) {
         System.out.println("Error setting event notification");
         System.out.println(e.toString());
         System.exit(-1);
      }

   }

   public void neutralPos() throws IOException
   {
       byte[] neutral = new byte[1];
       neutral[0] = 40;

             outputStream.write(neutral);

             System.out.println("neutral Position:" + neutral[0]);
   }

   public boolean readACK() {

        // we get here if data has been received
         byte[] readBuffer = new byte[2];
         try {
            // read data
            while (inputStream.available() > 0) {
               int numBytes = inputStream.read(readBuffer);
            }

            // read one byte. Max number
            int result = (int)readBuffer[0];

            System.out.println("Read: "+ result);
            if ( result == 31 )
            {
                return true;
            }
            else
            {
                return false;
            }

         } catch (IOException e) {
            System.out.println(e);
         }

         return false;
   }


   public void writetoport() throws IOException {

       byte[][] printArray = new byte[23][5];

       neutralPos();

             // Alle drei Motoren gleichzeitig 10 Steps mit z-Achse runter
             printArray[0][0] = 1;
             printArray[0][1] = 1;
             printArray[0][2] = 1;
             printArray[0][3] = 0;
             printArray[0][4] = 0;

             printArray[1][0] = 1;
             printArray[1][1] = 1;
             printArray[1][2] = 1;
             printArray[1][3] = 0;
             printArray[1][4] = 0;

             printArray[2][0] = 1;
             printArray[2][1] = 1;
             printArray[2][2] = 1;
             printArray[2][3] = 0;
             printArray[2][4] = 0;

             printArray[3][0] = 1;
             printArray[3][1] = 1;
             printArray[3][2] = 1;
             printArray[3][3] = 0;
             printArray[3][4] = 0;

             printArray[4][0] = 1;
             printArray[4][1] = 1;
             printArray[4][2] = 1;
             printArray[4][3] = 0;
             printArray[4][4] = 0;

             printArray[5][0] = 1;
             printArray[5][1] = 1;
             printArray[5][2] = 1;
             printArray[5][3] = 0;
             printArray[5][4] = 0;

             printArray[6][0] = 1;
             printArray[6][1] = 1;
             printArray[6][2] = 1;
             printArray[6][3] = 0;
             printArray[6][4] = 0;

             printArray[7][0] = 1;
             printArray[7][1] = 1;
             printArray[7][2] = 1;
             printArray[7][3] = 0;
             printArray[7][4] = 0;

             printArray[8][0] = 1;
             printArray[8][1] = 1;
             printArray[8][2] = 1;
             printArray[8][3] = 0;
             printArray[8][4] = 0;

             printArray[9][0] = 1;
             printArray[9][1] = 1;
             printArray[9][2] = 1;
             printArray[9][3] = 0;
             printArray[9][4] = 0;

             // Motor 1, 3 Schritte zuzrück

             printArray[10][0] = -1;
             printArray[10][1] = 0;
             printArray[10][2] = 0;
             printArray[10][3] = 0;
             printArray[10][4] = 0;

             printArray[11][0] = -1;
             printArray[11][1] = 0;
             printArray[11][2] = 0;
             printArray[11][3] = 0;
             printArray[11][4] = 0;

             printArray[12][0] = -1;
             printArray[12][1] = 0;
             printArray[12][2] = 0;
             printArray[12][3] = 0;
             printArray[12][4] = 0;
            //--------------------------------------------//

             // Motor 2, 3 Steps zurück
             //--------------------------------------------//
             printArray[13][0] = 0;
             printArray[13][1] = -1;
             printArray[13][2] = 0;
             printArray[13][3] = 0;
             printArray[13][4] = 0;

             printArray[14][0] = 0;
             printArray[14][1] = -1;
             printArray[14][2] = 0;
             printArray[14][3] = 0;
             printArray[14][4] = 0;

             printArray[15][0] = 0;
             printArray[15][1] = -1;
             printArray[15][2] = 0;
             printArray[15][3] = 0;
             printArray[15][4] = 0;

             //-------------------------------------------//

             // Motor 3, 3 Steps zurück
             //--------------------------------------------//

             printArray[16][0] = 0;
             printArray[16][1] = 0;
             printArray[16][2] = -1;
             printArray[16][3] = 0;
             printArray[16][4] = 0;

             printArray[16][0] = 0;
             printArray[16][1] = 0;
             printArray[16][2] = -1;
             printArray[16][3] = 0;
             printArray[16][4] = 0;

             printArray[17][0] = 0;
             printArray[17][1] = 0;
             printArray[17][2] = -1;
             printArray[17][3] = 0;
             printArray[17][4] = 0;

             //-------------------------------------------//

             // Color wechseln
             //--------------------------------------------//

             printArray[18][0] = 0;
             printArray[18][1] = 0;
             printArray[18][2] = 0;
             printArray[18][3] = 1;
             printArray[18][4] = 0;

             printArray[19][0] = 0;
             printArray[19][1] = 0;
             printArray[19][2] = 0;
             printArray[19][3] = 2;
             printArray[19][4] = 0;

             printArray[20][0] = 0;
             printArray[20][1] = 0;
             printArray[20][2] = 0;
             printArray[20][3] = 0;
             printArray[20][4] = 0;

             //-------------------------------------------//

             // Arm schreiben
             //--------------------------------------------//

             printArray[21][0] = 0;
             printArray[21][1] = 0;
             printArray[21][2] = 0;
             printArray[21][3] = 0;
             printArray[21][4] = 1;

             printArray[22][0] = 0;
             printArray[22][1] = 0;
             printArray[22][2] = 0;
             printArray[22][3] = 0;
             printArray[22][4] = 0;

            // byte[] start = new byte[1];
             //start[0] = 50;


             //System.out.println("start:" + start[0]);
             //System.out.println("Print:" + start);

             for (int i=0; i < printArray.length; i++) {
                 for ( int j = 0; j< printArray[i].length; j++) {
                     try {
                         // write string to serial port
                         //outputStream.write(x);
                         //outputStream.write(testBytes);
                         //outputStream.write(print);
                         
                         outputStream.write(printArray[i][j]);
                         
                         System.out.println("Writing " + printArray[i][j] + " to " + serialPort.getName());

                   } catch (IOException e) {
                     System.out.println(e);
                   }
                }
               System.out.println("-----------------------------------");
                 if ( !readACK() )
                 {
                     System.out.println("no");
                     //i--;
                     break;
                 }
             }
             
      //byte[] testBytes = new byte[6];
      //testBytes[0] = 90;
     /* byte[] x = new byte[6];
             x[0] = 1;
             x[1] = (byte) 100;
             x[2] = 0;
             x[3] = (byte) 80;
*/
     // System.out.println("Writing " + testBytes + "to " + serialPort.getName());
             

     
   }

   public Nulltest2() {
      // initalize serial port
      try {
         serialPort = (SerialPort) portId.open("SimpleReadApp", 2000);
      } catch (PortInUseException e) {
        System.out.println(e);
      }

      try {
          inputStream = new InputStream() {

                @Override
                public int read() throws IOException {
                    throw new UnsupportedOperationException("Not supported yet.");
                }
          };
         inputStream = serialPort.getInputStream();
      } catch (IOException e) {
        System.out.println(e);
      }

      try {
         serialPort.addEventListener(this);
      } catch (TooManyListenersException e) {}

      // activate the DATA_AVAILABLE notifier
      serialPort.notifyOnDataAvailable(true);

      try {
         // set port parameters

          //serialPort.setSerialPortParams(9600, SerialPort.DATABITS_8,
          serialPort.setSerialPortParams(62500, SerialPort.DATABITS_8,
          SerialPort.STOPBITS_1,
                     SerialPort.PARITY_NONE);
      } catch (UnsupportedCommOperationException e) {}

      // start the read thread
      readThread = new Thread(this);
      readThread.start();

   }

   public void run() {
      // first thing in the thread, we initialize the write operation
      initwritetoport();
      System.out.println("Start Write Test");
        try {
            writetoport();
//      for (int i=0; i < 2; i++) {
//
//          writetoport();
//      }
//      try {
//         while (true) {
//            // write string to port, the serialEvent will read it
//            writetoport();
//            Thread.sleep(1000);
//         }
//      } catch (InterruptedException e) {}
        } catch (IOException ex) {
            Logger.getLogger(Nulltest2.class.getName()).log(Level.SEVERE, null, ex);
        }
//      for (int i=0; i < 2; i++) {
//
//          writetoport();
//      }
      
//      try {
//         while (true) {
//            // write string to port, the serialEvent will read it
//            writetoport();
//            Thread.sleep(1000);
//         }
//      } catch (InterruptedException e) {}

    }

   public void serialEvent(SerialPortEvent event) {
      switch (event.getEventType()) {
      case SerialPortEvent.BI:
      case SerialPortEvent.OE:
      case SerialPortEvent.FE:
      case SerialPortEvent.PE:
      case SerialPortEvent.CD:
      case SerialPortEvent.CTS:
      case SerialPortEvent.DSR:
      case SerialPortEvent.RI:
      case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
         break;
      case SerialPortEvent.DATA_AVAILABLE:
         // we get here if data has been received
         byte[] readBuffer = new byte[2];
         try {
            // read data
            while (inputStream.available() > 0) {
               int numBytes = inputStream.read(readBuffer);
            }
    
            // read one byte. Max number
            int result = (int)readBuffer[0];

            System.out.println("Read: "+result);
         } catch (IOException e) {}

         break;
      }
   }

}

