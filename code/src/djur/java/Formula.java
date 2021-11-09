package djur.java;

interface InterfaceTest {
    double calculate(int a);

    default double sqrt(int a) {
        return Math.sqrt(a);
    }
}
