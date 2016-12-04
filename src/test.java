/**
 * Created by jacobmenke on 12/3/16.
 */

interface NumberOne{
    int getNumber();

}

interface NumberTwo extends NumberOne{
    int getNumber();

}

public class test implements NumberTwo{

    @Override
    public int getNumber() {
        return 5;
    }

    int dog;
    static  int figure;

    public int getDog() {
        return dog;
    }

    public void setDog(int dog) {
        this.dog = dog;
    }


    public static int getFigure() {
        return figure;
    }

    public static void setFigure(int figure) {
        test.figure = figure;
    }

    public static void main(String[] args) {

        test testCode = new test();

        System.out.println(testCode.getNumber());
    }
}
