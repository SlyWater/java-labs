package lab1;

import java.io.Serializable;

public class RecIntegral implements Serializable{
    private double lowLimit;
    private double highLimit;
    private double step;
    private double result;
    
    private static final double MIN_VALUE=0.000001;
    private static final double MAX_VALUE=1000000;
    
    
       public static void validateInput(double lowLimit, double highLimit, double step) throws InvalidInputException{
        if (lowLimit >= highLimit){
            throw new InvalidInputException("High Limit must be greater than Low Limit", highLimit);    
        }
        if (lowLimit <= MIN_VALUE || lowLimit >= MAX_VALUE){
            throw new InvalidInputException("Low Limit must be between 0.000001 and 1000000", lowLimit); 
        }
        if (highLimit <= MIN_VALUE || highLimit >= MAX_VALUE){
            throw new InvalidInputException("High Limit must be between 0.000001 and 1000000", highLimit);      
        }
        double stepLimit = highLimit - lowLimit;
        if (step < MIN_VALUE || step > stepLimit){
            throw new InvalidInputException("Step must be between 0.000001 and " + String.valueOf(stepLimit), step);    
        }
    } 
      
    public RecIntegral(double lowLimit, double highLimit, double step) throws InvalidInputException{
        validateInput(lowLimit, highLimit, step);
        this.highLimit = highLimit;
        this.lowLimit = lowLimit;
        this.step = step;
        this.result = 0.0;
    }
    
    public RecIntegral(double lowLimit, double highLimit, double step, double result) throws InvalidInputException{
        validateInput(lowLimit, highLimit, step);
        this.highLimit = highLimit;
        this.lowLimit = lowLimit;
        this.step = step;
        this.result = result;
    }
     

    public double calculate(){
        double xLast = 0;
        result = 0.;
        for(double x = lowLimit; x < highLimit; x += step){
//            if(x + step <= highLimit){
            result += x + step <= highLimit ?  step * (Math.cos(x * x) + Math.cos((x + step)*(x + step)))/2 : 0;
//            }
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
    @Override
    public String toString() {
        return String.format("%f %f %f %f\n", lowLimit, highLimit, step, result);
    }
    
}
