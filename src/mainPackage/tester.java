package mainPackage;

/**
 * Created by jacobmenke on 4/2/17.
 */
public class tester {
    public static void main(String[] args) {


        String x = "    var name:String=\"dogs\"";
        x = "    var age : Int = 55";

        System.out.println(x.substring(x.indexOf(":")+1, x.indexOf("=")).trim());
        System.out.println(x.substring(x.indexOf("=")+1).trim());
    }
}
