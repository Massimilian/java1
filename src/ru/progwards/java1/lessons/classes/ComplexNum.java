package ru.progwards.java1.lessons.classes;

public class ComplexNum {
    private final int a;
    private final int b;

    public ComplexNum(int a, int b) {
        this.a = a;
        this.b = b;
    }

    public int getA() {
        return a;
    }

    public int getB() {
        return b;
    }

    @Override
    public String toString() {
        char i = 'i';
        return String.format("%d+%d%c", a, b, i);
    }

    public ComplexNum add(ComplexNum num) {
        return new ComplexNum(this.a + num.getA(), this.b + num.getB());
    }

    public ComplexNum sub(ComplexNum num) {
        return new ComplexNum(this.a - num.getA(), this.b - num.getB());
    }

    public ComplexNum mul(ComplexNum num) {
        return new ComplexNum(this.a * num.getA() - this.b * num.getB(), this.b * num.getA() + this.a * num.getB());
    }

    public ComplexNum div(ComplexNum num) {
        int counter = num.getA()*num.getA()+num.getB()*num.getB();
        ComplexNum result = null;
        if (counter != 0) {
            result = new ComplexNum((this.a * num.getA() + this.b * num.getB()) / counter,
                    (this.b * num.getA() - this.a * num.getB()) / counter);
        }
        return result;
    }
}
