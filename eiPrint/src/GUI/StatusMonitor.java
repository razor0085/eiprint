
package GUI;

import java.util.HashMap;
import java.util.Set;

/**
 *
 * @author Ruedi Odermatt
 * @version 0.0.1
 * @comment dfsöadsflkjfds fsdöl kfsdjsdf
 *
 */
public class StatusMonitor {

    private HashMap<Integer,String> errorMSGMap;

    public StatusMonitor()
    {

        errorMSGMap = new HashMap<Integer,String>();
    }



    /**
     *
     * Ruedi: Liest eine Auflistung zu erwartender Fehlermeldungen
     * aus der Konfigurationsdate eiPrintFMs.ini und speichert
     * diese in die Map errorMSGMap
     *
     */
    private void setMessageList()
    {


    }



    /**
     *
     * Ruedi: Ruft die Methode getState() von MCCmdRsp auf
     * und verbindet den erhaltenen int-Wert mit einer Liste,
     * die innerhalb der Klasse StatusMonitor gehalten wird
     *
     */
    public String showState()
    {
        return ("Muss noch programmiert werden");
    }




    /**
     *
     * Gion: Was soll diese Funktion machen?
     *
     */
    public void dataLogger()
    {

    }


    /**
     *
     * Ruedi: Die drei folgenden Funktionen
     * - insertValueErrorMSGMap
     * - printValueErrorMSGMap
     * - printAllMSGNumbers
     * sind für die Verwaltung der errorMSGMap notwendig
     *
     */

    private void insertValueErrorMSGMap(Integer key, String value)
    {
        errorMSGMap.put(key, value);
    }

    private void printValueErrorMSGMap(Integer key)
    {
        System.out.println(errorMSGMap.get(key));
    }


    // Dient für Wartungsarbeiten.
    private void printAllMSGNumbers()
    {
        Set<Integer> setMap = errorMSGMap.keySet();
        for (Integer key : setMap) {
            System.out.println(key);
        }
    }



}
