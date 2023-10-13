import java.util.Arrays;
import java.util.Optional;
import java.util.stream.LongStream;


public class Main {
    public static void main(String[] args) throws InterruptedException {
        long[] arr = LongStream.iterate(1, i -> ++i).limit(50_000_000).toArray();


        int multiplier = 4;
        int threadCount = 4;
        int chunkSize = arr.length / threadCount;

        long startTime = System.currentTimeMillis();
        Thread[] threads = new Thread[threadCount];

        for (int i = 0; i < threadCount; i++) {
            final int start = i * chunkSize;
            final int end =
                    i == threadCount - 1 ?
                            arr.length : (i + 1) * chunkSize;

            threads[i] = new Thread(() -> {
                for (int j = start; j < end; j++) {
                    arr[j] *= multiplier;
                }
            });
        }

        for (var thread : threads) {
            thread.start();
        }

        for (var thread : threads) {
            thread.join();
        }

//        for (int i = 0; i < arr.length; i++) {
//            arr[i] *= multiplier;
//        }

        long end = System.currentTimeMillis();

        System.out.println(end - startTime);
        System.out.println("Лабораторная 0");
        task1();
        task2();
        task3();
        task4();
        task5();

    }

    public static void task1() {
        System.out.println("Задание 1");
// 66. Plus One
// You are given a large integer represented as an integer array digits,
//where each digits[i] is the ith digit of the integer.
//The digits are ordered from most significant to least significant in left-to-right order.
//The large integer does not contain any leading 0's.
//Increment the large integer by one and return the resulting array of digits.
        int[] digits;
//        digits = new int[]{9};
//        digits = new int[]{1,2,3};
        digits = new int[]{4, 3, 2, 1};
        int leng = digits.length;
        int number;
        int addNumber = 1;
        for (int i = leng - 1; i >= 0; i--) {
            number = digits[i] + addNumber;
            if (number == 10) {
                digits[i] = 0;
                int[] newDigits = new int[digits.length + 1];
                newDigits[0] = 1;
                System.out.println(Arrays.toString(newDigits));
            } else {
                digits[i] = number;
                System.out.println(Arrays.toString(digits));
                break;
            }
        }
    }

    public static void task2() {
        System.out.println("Задание 2");
//125. Valid Palindrome
//        String s = "A man, a plan, a canal: Panama";
//        String s = "race a car";
        String s = " ";
        String s1 = s.replaceAll("[!\"#$%&'()*+,-./:;<=>?@\\[\\]^_`{|}~\\s]", "");
        if (s1.equalsIgnoreCase(new StringBuffer().append(s1).reverse().toString())) {
            System.out.println(true);
        } else {
            System.out.println(false);
        }
    }

    public static void task3() {
        System.out.println("Задание 3");
        char[] s = {'H', 'a', 'n', 'n', 'a', 'h'};
        int length = s.length;
        for (int i = 0; i < (length / 2); i++) {
            char l = s[i];
            s[i] = s[length - i - 1];
            s[length - i - 1] = l;
        }
        System.out.println(s);
    }

    public static void task4() {
        System.out.println("Задание 4");
        int[] nums = {2, 0, 1};
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                int tmp = 0;
                if (nums[i] > nums[j]) {
                    tmp = nums[i];
                    nums[i] = nums[j];
                    nums[j] = tmp;
                }
            }
        }
        System.out.println(Arrays.toString(nums));
    }

    public static void task5() {
        System.out.println("Задание 5");
        int target = -1;
        int[] numbers = {-1,0};
        int val = 0;
        int val2 = numbers.length - 1;
        int sum;
        while (val < val2) {
            sum = numbers[val] + numbers[val2];
            if (sum == target)
                System.out.println(Arrays.toString(new int[]{val + 1, val2 + 1}));
            else if (sum < target)
                val++;
                val2--;
        }
    }
}


