package lab1;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class RecIntegral implements Externalizable, Callable<Double>{
    private double lowLimit;
    private double highLimit;
    private double step;
    private double result;
    
    private static final int THREAD_COUNT = 9;
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
    public RecIntegral(){
        
    }
    public RecIntegral(double lowLimit, double highLimit, double step) throws InvalidInputException{
        validateInput(lowLimit, highLimit, step);
        setValues(lowLimit, highLimit, step, 0.0);
    }
    
    private RecIntegral(double lowLimit, double highLimit, double step, boolean validate) throws InvalidInputException{
        if (validate) {
            validateInput(lowLimit, highLimit, step);
        }
        setValues(lowLimit, highLimit, step, 0.0);
    }
    
    public static RecIntegral createCalculationPart(double lowLimit, double highLimit, double step) throws InvalidInputException {
        return new RecIntegral(lowLimit, highLimit, step, false);
    }
    
    private void setValues(double lowLimit, double highLimit, double step, double result) {
        this.highLimit = highLimit;
        this.lowLimit = lowLimit;
        this.step = step;
        this.result = result;
    }
    
    public RecIntegral(double lowLimit, double highLimit, double step, double result) throws InvalidInputException{
        validateInput(lowLimit, highLimit, step);
        setValues(lowLimit, highLimit, step, result);
    }
     

    public Double calculate(){
        result = 0.0;
        for (double x = lowLimit; x < highLimit; x += step) {
            double nextX = Math.min(x + step, highLimit);

            result += (nextX - x) * (Math.cos(x * x) + Math.cos(nextX * nextX)) / 2.0;
        }
        
        System.out.println(result);
        return result;
    }
    
    
    public void calculateMultiThread() throws InterruptedException, InvalidInputException, ExecutionException {
        Thread[] threads = new Thread[THREAD_COUNT];
        RecIntegral[] parts = new RecIntegral[THREAD_COUNT];
        double length = (highLimit - lowLimit) / THREAD_COUNT;
        FutureTask<Double>[] tasks = new FutureTask[THREAD_COUNT];


        for (int i = 0; i < THREAD_COUNT; i++) {
            double partLow = lowLimit + i * length;
            double partHigh = lowLimit + (i + 1) * length;

            parts[i] = RecIntegral.createCalculationPart(partLow, partHigh, step);

            tasks[i] = new FutureTask<>(parts[i]);
            threads[i] = new Thread(tasks[i]);
            threads[i].start();
        }  
        
        result = 0.0;

        for (int i = 0; i < THREAD_COUNT; i++) {
              result += tasks[i].get();
//            threads[i].join();
//            result += parts[i].getResult();
        }
    }
       
    @Override
    public Double call() throws Exception{
        return calculate();
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
    
    @Override
    public void writeExternal(ObjectOutput out) throws IOException{
        out.writeObject(this.getLowLimit());     
        out.writeObject(this.getHighLimit());
        out.writeObject(this.getStep());
        out.writeObject(this.getResult());
    }
    
    @Override
    public void readExternal(ObjectInput in) throws ClassNotFoundException, IOException{
        lowLimit = (Double) in.readObject();
        highLimit = (Double) in.readObject();
        step = (Double) in.readObject();
        result = (Double) in.readObject();
    }
}
