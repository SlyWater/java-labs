package lab1;

public class RecIntegral{
    private double lowLimit;
    private double highLimit;
    private double step;
    private double result;
    
    //errors:
    //1. step>HighLimit-lowLimit
    //2. incorrect input(ttt,NULL)
    //3. lowLimit>highLimit
    //4. limits
    
    
      
    // otchet v pdf!!!
    public RecIntegral(double lowLimit, double highLimit, double step){
        this.highLimit = highLimit;
        this.lowLimit = lowLimit;
        this.step = step;
        this.result = 0.0;
    }
    public double calculate(){
        double xLast = 0;
        result = 0.;
        for(double x = lowLimit; x < highLimit; x += step){
            if(x + step <= highLimit){
                result += step * (Math.cos(x * x) + Math.cos((x + step)*(x + step)))/2;
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
