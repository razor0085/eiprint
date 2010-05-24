/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eiPrint.OBJMgmt;

import eiPrint.OBJMgmt.KinematikDpod.Kinematik;

/**
 *
 * @author david
 */
public class DpodcalcTests {

    /**
     * Einen beliebigen PUnkt aus der Db nehmen und die Steps für den
     * jeweiligen Motoren berechnen
     * @param args
     */

    public static void main(String[] args) {
        Objektfile_einlesen o = new Objektfile_einlesen("C:/Users/david/Desktop/PREN/eiPrint/src/eiPrint/OBJMgmt/data/max.txt");
//v 67.960793 0.045185 244.515381 Testpunkt für Origin Auf Ballon
        Koordinatentransformation kt = new Koordinatentransformation(67.960793, 0.045185, 244.515381);
        kt.koordinatensystemDrehung();
        System.out.println("1: " + kt.getXrobot() + "2: " + kt.getYrobot() + "3: " + kt.getZrobot());
//Nach Umrechnung 1: 243.760298436092852: 0.0451853: 794.5153809999999
//Nach korrekturn -172.357700555312762: 0.0451853: 305.484619
        kt = new Koordinatentransformation(60.699474, 0.203318, 252.523010);
        kt.koordinatensystemDrehung();
        System.out.println("1: " + kt.getXrobot() + "2: " + kt.getYrobot() + "3: " + kt.getZrobot());
        //-182.985950833094342: 0.2033183: 297.47699
        kt = new Koordinatentransformation(-182.985950833094342, 0.2033183, 297.47699);
        kt.koordinatensystemDrehung();
        System.out.println("1: " + kt.getXrobot() + "2: " + kt.getYrobot() + "3: " + kt.getZrobot());
        Kinematik DPOD = new Kinematik();
        DPOD.x0 = kt.getXrobot();
        DPOD.y0 = kt.getYrobot();
        DPOD.z0 = kt.getZrobot();
        int OK = 0;
        // o.DPOD.delta_calcInverse(o.DPOD);
        System.out.println(DPOD.delta_calcInverse(DPOD));
        System.out.println("w1: " + DPOD.theta1);
        System.out.println("w2: " + DPOD.theta2);
        System.out.println("w3: " + DPOD.theta3);
    }
}
