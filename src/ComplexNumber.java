public class ComplexNumber {
    double real;
    double imaginary;

    ComplexNumber(double a, double b) {
        real = a;
        imaginary = b;
    }

    void square() {
        double new_real = Math.pow(real,2) - Math.pow(imaginary,2);
        double new_imaginary = 2*real*imaginary;
        this.real = new_real;
        this.imaginary = new_imaginary;
    }

    double mod() {
        return Math.sqrt(Math.pow(real,2) + Math.pow(imaginary,2));
    }

    void add(ComplexNumber c) {
        this.real += c.real;
        this.imaginary += c.imaginary;
    }
}
