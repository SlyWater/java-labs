package lab1;


public class InvalidInputException extends Exception {
    double _invalidField;
    public double getInvalidField(){
        return _invalidField;
    }
    public InvalidInputException(String message, double invalidField){
        super(message);
        _invalidField = invalidField;
    }
    public InvalidInputException(String message){
        super(message);
    }
    
}
