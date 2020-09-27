package covid19trackingmanager;

public class Sample {
    
    public static void main(String[] args){
        String plus = "A+";
        String normal = "A";
        String minus = "A-";
        String letterB = "D";
        
        System.out.println(plus.compareTo(normal));
        System.out.println(plus.compareTo(minus));
        System.out.println(plus.compareTo(plus));
        System.out.println(plus.compareTo(letterB));
    }   
}
