/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author mayte
 */

public class ComplexNumber implements Comparable<ComplexNumber>{
    private double real;
    private double imaginary;
    
    /**
     * Constructor of the ComplexNumber
     * @param a real part
     * @param b imaginary part
     */
    ComplexNumber(double a, double b){
        real = a;
        imaginary = b;
    }
    
    /**
     * 
     * @return the real part of the ComplexNumber
     */
    public double realPart(){
        return real;
    }
    
    /**
     * 
     * @return the imaginary part of the ComplexNumber
     */
    public double imaginaryPart(){
        return imaginary;
    }
    
    /**
     * Adds c to the ComplexNumber
     * 
     * @param c
     *        number to add
     * @return 
     *        this + c
     */
    public ComplexNumber add (ComplexNumber c){
        double real = this.realPart() + c.realPart();
        double imag = this.imaginaryPart() + c.imaginaryPart();
        return new ComplexNumber(real, imag);
    }
    
    /**
     * Returns the result of subtracting c from the ComplexNumber
     * 
     * @param c
     *        subtracting
     * @return 
     *        this - c
     */
    public ComplexNumber subtract (ComplexNumber c){
        double real = this.realPart() - c.realPart();
        double imag = this.imaginaryPart() - c.imaginaryPart();
        return new ComplexNumber(real, imag);
    }
    
    /**
     * Returns multiplication of c and the ComplexNumber
     * 
     * @param c
     *        multiplying
     * @return 
     *          this * c2
     */
    public ComplexNumber multiply (ComplexNumber c){
        ComplexNumber result = null;
        
        double real = this.realPart() * c.realPart();
        real -= this.imaginaryPart() * c.imaginaryPart();
        double imag = this.imaginaryPart() * c.realPart();
        imag += this.realPart() * c.imaginaryPart();      
        
        result = new ComplexNumber(real,imag);
        return result;
    }
    
   /**
     * Returns the division of the ComplexNumber by c 
     *
     * @param c
     *        divider
     * @return 
     *        this / c
     */
    public ComplexNumber division (ComplexNumber c){
        ComplexNumber result = null;
        
        ComplexNumber conjC = c.conjugate();
        ComplexNumber number = this.multiply(conjC);
        ComplexNumber divisor = c.multiply(conjC);
        
        double real = number.realPart() / divisor.realPart();
        double imag = number.imaginaryPart() / divisor.imaginaryPart();
        
        result = new ComplexNumber(real, imag);
        
        return result;
    } 
    
    /**
     * Returns the conjugate of the ComplexNumber
     * 
     * @return 
     *        a - ib
     */
    public ComplexNumber conjugate (){
       return new ComplexNumber( this.realPart() ,- this.imaginaryPart());
    } 
    
    /**
     * Returns the module of the ComplexNumber
     * 
     * @return 
     *      sqrt (a² + b²)
     */
    public double module (){
        double real = Math.pow(this.realPart(), 2);
        double imag = Math.pow(this.imaginaryPart(),2);
        return Math.sqrt(real+imag);
    } 

    @Override
    public int compareTo(ComplexNumber o) {
        double real,imag;
        real = this.realPart() - o.realPart();
        imag = this.imaginaryPart() - o.imaginaryPart();
        if(real != 0.00) return (int)real;
        else return (int)imag;
    }
    
    @Override
    public boolean equals(Object o){
        ComplexNumber c = (ComplexNumber) o;
        boolean real = this.realPart() == c.realPart();
        boolean imag = this.imaginaryPart() == c.imaginaryPart();
        return real && imag;
    }
}
