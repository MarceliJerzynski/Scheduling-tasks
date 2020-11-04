package com.algorithm;

public class Task implements Comparable<Task> {
    int index;
    int p;
    int r;
    int d;
    int w;

    @Override
    public int compareTo(Task other) {
        if (this.d < other.d) {
            return -1;
        }
        if (this.d == other.d) {
            double a = (float) this.w / this.p;
            double b = (float) other.w / other.p;
            return Double.compare(b, a);
        }
        return 1;
    }

    public int getR() {
        return r;
    }
}
