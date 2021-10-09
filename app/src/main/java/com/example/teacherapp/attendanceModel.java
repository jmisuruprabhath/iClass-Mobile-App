package com.example.teacherapp;

import com.google.firebase.database.DatabaseReference;

import java.util.TreeMap;

public class attendanceModel {

    private int Jan;
    private int Feb;
    private int Mar;
    private int Apr;
    private int May;
    private int Jun;
    private int Jul;
    private int Aug;
    private int Sep;
    private int Oct;
    private int Nov;
    private int Dec;



    public attendanceModel() {
        Jan = 0;
        Feb = 0;
        Mar = 0;
        Apr = 0;
        May = 0;
        Jun = 0;
        Jul = 0;
        Aug = 0;
        Sep = 0;
        Oct = 0;
        Nov = 0;
        Dec = 0;
    }





    public int getJan() {
        return Jan;
    }

    public void setJan(int jan) {
        Jan = jan;
    }

    public int getFeb() {
        return Feb;
    }

    public void setFeb(int feb) {
        Feb = feb;
    }

    public int getMar() {
        return Mar;
    }

    public void setMar(int mar) {
        Mar = mar;
    }

    public int getApr() {
        return Apr;
    }

    public void setApr(int apr) {
        Apr = apr;
    }

    public int getMay() {
        return May;
    }

    public void setMay(int may) {
        May = may;
    }

    public int getJun() {
        return Jun;
    }

    public void setJun(int jun) {
        Jun = jun;
    }

    public int getJul() {
        return Jul;
    }

    public void setJul(int jul) {
        Jul = jul;
    }

    public int getAug() {
        return Aug;
    }

    public void setAug(int aug) {
        Aug = aug;
    }

    public int getSep() {
        return Sep;
    }

    public void setSep(int sep) {
        Sep = sep;
    }

    public int getOct() {
        return Oct;
    }

    public void setOct(int oct) {
        Oct = oct;
    }

    public int getNov() {
        return Nov;
    }

    public void setNov(int nov) {
        Nov = nov;
    }

    public int getDec() {
        return Dec;
    }

    public void setDec(int dec) {
        Dec = dec;
    }
}




class plotModel{

    //{12, 20, 35, 40, 10, 20, 15, 55, 37, 70, 50, 42,0}
    private static int[] plots = new int[12];

    public plotModel(attendanceModel model){
        plots[0] = model.getJan();
        plots[1] = model.getFeb();
        plots[2] = model.getMar();
        plots[3] = model.getApr();
        plots[4] = model.getMay();
        plots[5] = model.getJun();
        plots[6] = model.getJul();
        plots[7] = model.getAug();
        plots[8] = model.getSep();
        plots[9] = model.getOct();
        plots[10] = model.getNov();
        plots[11] = model.getDec();
    }

    public plotModel() {

    }

    public static int[] getPlots() {
        return plots;
    }
}
