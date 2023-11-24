import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.DoubleStream;


public class Main {
    public static void main(String[] args) throws InterruptedException {
        int N = 1000000; // Число элементов
        int M = 4; // Число потоков

        // Генерация файла с натуральными числами от 1 до N
        generateFile(N, "input.txt");
        long start = System.currentTimeMillis();

        // Последовательная обработка элементов файла
//        sequentialProcessing("input.txt");

        // Многопоточная обработка элементов файла
        multithreadedProcessing("input.txt", M);
        long end = System.currentTimeMillis();

        System.out.println(end - start);
        System.out.println("Готово");
    }

    private static void generateFile(int N, String filename) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
            for (int i = 1; i <= N; i++) {
                writer.write(Integer.toString(i));
                writer.newLine();
            }
            writer.close();
            System.out.println("Файл сгенерирован");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


//    private static void sequentialProcessing(String filename) {
//        try {
//            // Открытие файла для чтения
//            BufferedReader reader = new BufferedReader(new FileReader(filename));
//
//            // Чтение элементов файла и их обработка
//            String line;
//            while ((line = reader.readLine()) != null) {
//                int number = Integer.parseInt(line);
//                // Обработка элемента, например, умножение на 2
//                int result = number * 4;
////                System.out.println(result);
//            }
//
//            // Закрытие файла
//            reader.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    private static void multithreadedProcessing(String filename, int numThreads) {
        try {
            // Открытие файла для чтения
            BufferedReader reader = new BufferedReader(new FileReader(filename));

            // Вычисление общего числа элементов
            int numElements = countLines(filename);

            // Вычисление числа элементов для каждого потока
            int elementsPerThread[] = {10, 20, 35, 35};

            // Создание и запуск потоков
            List<Thread> threads = new ArrayList<>();
            for (int i = 0; i < numThreads; i++) {
                int startElement = i * elementsPerThread[i] + 1;
                int endElement = startElement + elementsPerThread[i] - 1;

                Thread thread = new Thread(new ProcessElementTask(filename, startElement, endElement));
                thread.start();

                threads.add(thread);
            }

            // Ожидание завершения всех потоков
            for (Thread thread : threads) {
                thread.join();
            }

            // Закрытие файла
            reader.close();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

//    private static void multithreadedProcessing(String filename, int numThreads) {
//        try {
//            // Открытие файла для чтения
//            BufferedReader reader = new BufferedReader(new FileReader(filename));
//
//            // Вычисление равного числа элементов для каждого потока
//            int numElements = countLines(filename);
//            int elementsPerThread = numElements / numThreads;
//
//            // Создание и запуск потоков
//            List<Thread> threads = new ArrayList<>();
//            for (int i = 0; i < numThreads; i++) {
//                int startElement = i * elementsPerThread + 1;
//                int endElement = (i + 1) * elementsPerThread;
//                if (i == numThreads - 1) {
//                    endElement = numElements;
//                }
//                Thread thread = new Thread(new ProcessElementTask(filename, startElement, endElement));
//                thread.start();
//                threads.add(thread);
//            }
//
//            // Ожидание завершения всех потоков
//            for (Thread thread : threads) {
//                thread.join();
//            }
//
//            // Закрытие файла
//            reader.close();
//        } catch (IOException | InterruptedException e) {
//            e.printStackTrace();
//        }
//    }

    private static int countLines(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        int numLines = 0;
        while (reader.readLine() != null) {
            numLines++;
        }
        reader.close();
        return numLines;
    }

    private static class ProcessElementTask implements Runnable {
        private String filename;
        private int startElement;
        private int endElement;

        public ProcessElementTask(String filename, int startElement, int endElement) {
            this.filename = filename;
            this.startElement = startElement;
            this.endElement = endElement;
        }
        @Override
        public void run() {
            try {
                // Открытие файла для чтения
                BufferedReader reader = new BufferedReader(new FileReader(filename));

                // Чтение элементов файла и их обработка
                String line;
                for (int i = 0; i < startElement-1; i++) {
                    reader.readLine(); // пропускаем элементы перед startElement
                }
                for (int i = startElement; i <= endElement; i++) {
                    line = reader.readLine();
                    int number = Integer.parseInt(line);
                    // Обработка элемента, например, умножение на 2
                    int result = number * 2;
//                    System.out.println(result);
                }

                // Закрытие файла
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
//        double[] arr = DoubleStream.iterate(1, i -> ++i).limit(1_000_000).toArray();
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("Введите число N: ");
//        int N = scanner.nextInt();
//        scanner.close();
//        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
//                new FileOutputStream("output.txt"), StandardCharsets.UTF_8))) {
//            for (int i = 0; i < N; i++) {
//                writer.write(Double.toString(N));
//                writer.write(System.lineSeparator());
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//
//        int multiplier = 2;
//        int threadCount = 4;
////        int chunkSize = arr.length / threadCount;
//        int[] chunkSize = {1, 1000, 999, 9_998_000};
//
//        long startTime = System.currentTimeMillis();
//        Thread[] threads = new Thread[threadCount];
//
//
//
////        for (int i = 0; i < threadCount; i++) {
////            final int start = i * chunkSize;
////            final int end =
////                    i == threadCount - 1 ?
////                            arr.length : (i + 1) * chunkSize;
////            threads[i] = new Thread(() -> {
////                for (int j = start; j < end; j++) {
////                    arr[j] *= multiplier;
////                }
////            });
////        }
//
//
//        for (int i = 0; i < threadCount; i++) {
//            final int start = i == 0 ? 0 : sum(chunkSize, i - 1);
//            final int end = start + chunkSize[i];
//            threads[i] = new Thread(() -> {
//                for (int j = start; j < end; j++) {
//                    arr[j] = Math.pow(j, multiplier);
//                }
//            });
//        }
//
//        for (var thread : threads) {
//            thread.start();
//        }
//
//        for (var thread : threads) {
//            thread.join();
//        }


//        for (int i = 0; i < arr.length; i++) {
//            arr[i] *= multiplier;
//        }

//        long end = System.currentTimeMillis();
//
//        System.out.println(end - startTime);
//               System.out.println(Arrays.toString(arr));



//        System.out.println("Лабораторная 0");
//        task1();
//        task2();
//        task3();
//        task4();
//        task5();
//
//    }
//    public static int sum(int[] array, int endIndex) {
//        int sum = 0;
//        for (int i = 0; i <= endIndex; i++) {
//            sum += array[i];
//        }
//        return sum;
//    }
//    public static void task1() {
//        System.out.println("Задание 1");
//// 66. Plus One
//// You are given a large integer represented as an integer array digits,
////where each digits[i] is the ith digit of the integer.
////The digits are ordered from most significant to least significant in left-to-right order.
////The large integer does not contain any leading 0's.
////Increment the large integer by one and return the resulting array of digits.
//        int[] digits;
////        digits = new int[]{9};
////        digits = new int[]{1,2,3};
//        digits = new int[]{4, 3, 2, 1};
//        int leng = digits.length;
//        int number;
//        int addNumber = 1;
//        for (int i = leng - 1; i >= 0; i--) {
//            number = digits[i] + addNumber;
//            if (number == 10) {
//                digits[i] = 0;
//                int[] newDigits = new int[digits.length + 1];
//                newDigits[0] = 1;
//                System.out.println(Arrays.toString(newDigits));
//            } else {
//                digits[i] = number;
//                System.out.println(Arrays.toString(digits));
//                break;
//            }
//        }
//    }
//
//    public static void task2() {
//        System.out.println("Задание 2");
////125. Valid Palindrome
////        String s = "A man, a plan, a canal: Panama";
////        String s = "race a car";
//        String s = " ";
//        String s1 = s.replaceAll("[!\"#$%&'()*+,-./:;<=>?@\\[\\]^_`{|}~\\s]", "");
//        if (s1.equalsIgnoreCase(new StringBuffer().append(s1).reverse().toString())) {
//            System.out.println(true);
//        } else {
//            System.out.println(false);
//        }
//    }
//
//    public static void task3() {
//        System.out.println("Задание 3");
//        char[] s = {'H', 'a', 'n', 'n', 'a', 'h'};
//        int length = s.length;
//        for (int i = 0; i < (length / 2); i++) {
//            char l = s[i];
//            s[i] = s[length - i - 1];
//            s[length - i - 1] = l;
//        }
//        System.out.println(s);
//    }
//
//    public static void task4() {
//        System.out.println("Задание 4");
//        int[] nums = {2, 0, 1};
//        for (int i = 0; i < nums.length; i++) {
//            for (int j = i + 1; j < nums.length; j++) {
//                int tmp = 0;
//                if (nums[i] > nums[j]) {
//                    tmp = nums[i];
//                    nums[i] = nums[j];
//                    nums[j] = tmp;
//                }
//            }
//        }
//        System.out.println(Arrays.toString(nums));
//    }
//
//    public static void task5() {
//        System.out.println("Задание 5");
//        int target = -1;
//        int[] numbers = {-1,0};
//        int val = 0;
//        int val2 = numbers.length - 1;
//        int sum;
//        while (val < val2) {
//            sum = numbers[val] + numbers[val2];
//            if (sum == target)
//                System.out.println(Arrays.toString(new int[]{val + 1, val2 + 1}));
//            else if (sum < target)
//                val++;
//            val2--;
//        }
      }
}


