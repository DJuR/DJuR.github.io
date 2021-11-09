package djur.java;

public class InterfaceObjectTest implements InterfaceTest {

    @Override
    public double calculate(int a) {
        return sqrt(a);
    }
}
