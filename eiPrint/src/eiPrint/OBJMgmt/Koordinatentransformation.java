/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package eiPrint.OBJMgmt;

/**
 *
 * @author dave
 */
public class Koordinatentransformation
{

    //Attribute
    private double x;
    private double y;
    private double z;
    private double xrobot;
    private double yrobot;
    private double zrobot;
    private double winkel = 45.0D;
    // höhe von Nullpos entsprichte tz
    private double tz = 550.0D;
    //Ansicht von Vorne Laser auf meiner Seite, 70/2
    private double ty = 35.0D; //entspricht ty
    
    public Koordinatentransformation(int x, int y, int z)
    {
        this.x=x;
        this.y=y;
        this.z=z;
        //setHöhe(550);
        
    }

    /**
     * Umrechnung für die drehung des Koordinatensystems um 45°
     * y Koordinate bleibt unverändert da sie in beiden Systemen gleich ist.
     * Dann wird das Koordinatensystem wird um den Vektor p in y und in
     * z-Richtung verschoben.
     */
    public void koordinatensystemDrehung()
    {
        //Zuerst wird das Koordinatensystem um 45° gedreht
        xrobot = x * Math.cos(winkel) + z * Math.sin(winkel);
        zrobot = (-1) * x *Math.sin(winkel) + z * Math.cos(winkel);
        //Dann wird es um Vektor t(0,ty,tz) veschoben, x bleibt unberändert
        yrobot = y + ty;
        zrobot = z + tz;

    }

 
    /**
     * @return the x
     */
    public double getX() {
        return x;
    }

    /**
     * @param x the x to set
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * @return the y
     */
    public double getY() {
        return y;
    }

    /**
     * @param y the y to set
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * @return the z
     */
    public double getZ() {
        return z;
    }

    /**
     * @param z the z to set
     */
    public void setZ(double z) {
        this.z = z;
    }

    /**
     * @return the xrobot
     */
    public double getXrobot() {
        return xrobot;
    }

    /**
     * @param xrobot the xrobot to set
     */
    public void setXrobot(double xrobot) {
        this.xrobot = xrobot;
    }

    /**
     * @return the yrobot
     */
    public double getYrobot() {
        return yrobot;
    }

    /**
     * @param yrobot the yrobot to set
     */
    public void setYrobot(double yrobot) {
        this.yrobot = yrobot;
    }

    /**
     * @return the zrobot
     */
    public double getZrobot() {
        return zrobot;
    }

    /**
     * @param zrobot the zrobot to set
     */
    public void setZrobot(double zrobot) {
        this.zrobot = zrobot;
    }

    /**
     * @return the winkel
     */
    public double getWinkel() {
        return winkel;
    }

    /**
     * @param winkel the winkel to set
     */
    public void setWinkel(double winkel) {
        this.winkel = winkel;
    }

    /**
     * @return the höhe
     */
    public double getHöhe() {
        return tz;
    }

    /**
     * @param höhe the höhe to set
     */
    public void setHöhe(double höhe) {
        this.tz = höhe;
    }
}
