package mainPackage;

/**
 * Created by jacobmenke on 4/2/17.
 */


class my{
    String name;
    Integer age;

    public my(String name) {
        this.name = name;
    }

    public my(Integer age) {
        this.age = age;
    }

    public my(String name, Integer age) {
        this.name = name;
        this.age = age;
    }


}
public class tester {
    public static void main(String[] args) {


        String x = "    var name:String=\"dogs\"";
        x = "    var age : Int = 55";

        System.out.println(x.substring(x.indexOf(":")+1, x.indexOf("=")).trim());
        System.out.println(x.substring(x.indexOf("=")+1).trim());
    }
}
