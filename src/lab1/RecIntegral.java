/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab1;

/**
 *
 * @author slywater
 */
public class RecIntegral{
    private double lowLimit;
    private double highLimit;
    private double step;
    private double result = 0.0;
    public RecIntegral(double lowLimit, double highLimit, double step){
        this.highLimit=highLimit;
        this.lowLimit = lowLimit;
        this.step = step;
    }
    public double calculate(){
        double xLast = 0;
        result = 0.;
        for(double x = lowLimit; x < highLimit; x+=step){
            if(x+step<=highLimit){
                result += step * (Math.cos(x*x) + Math.cos((x+step)*(x+step)))/2;
            }
            xLast = x;
        }
        result += (highLimit - xLast) * (Math.cos(xLast * xLast) + Math.cos(highLimit * highLimit)) / 2;
        return result;
    }
    public void setLowLimit(double ll){
        lowLimit = ll;
    }
    public void setHighLimit(double hl){
        highLimit = hl;
    }
    public void setStep(double s){
        step = s;
    }
    
    public double getLowLimit(){
        return lowLimit;
    }
    public double getHighLimit(){
        return highLimit;
    }
    public double getStep(){
        return step;
    }
    public double getResult(){
        return result;
    }
    
    
}
