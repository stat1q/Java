package edu.school21.numbers;

public class NumberWorker {

    public static boolean isPrime(int number) throws IllegalNumberException {
        if (number < 2) throw new IllegalNumberException("Number must be > 1");
        boolean isPrime = true;
        for (int i = 2; i * i <= number && isPrime; i++) {
            if (number % i == 0) {
                isPrime = false;
            }
        }
        return isPrime;
    }

    public int digitsSum(int number) {
        int sum = 0;
        while (number != 0) {
            sum += number % 10;
            number /= 10;
        }
        return sum;
    }

}

class IllegalNumberException extends IllegalAccessException {
    public IllegalNumberException(String message) {super(message); }
}