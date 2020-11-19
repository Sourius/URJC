/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tablashash;

/**
 *
 * @author Sourius
 */
public class Nota {
    double num;
    String cal;

    public Nota(double num, String cal) {
        this.num = num;
        this.cal = cal;
    }
    
    public double getNum() {
        return num;
    }

    public void setNum(double num) {
        this.num = num;
    }

    public String getCal() {
        return cal;
    }

    public void setCal(String cal) {
        this.cal = cal;
    }

    @Override
    public String toString() {
        return "Nota{" + "num=" + num + ", cal=" + cal + '}';
    }
    
}
