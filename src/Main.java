
import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        long[] arr = LongStream.iterate(1, i  -> ++i).limit(50_000_000).toArray();


        int multiplier = 4;
        int threadCount = 4;
        int chunkSize = arr.length/threadCount;

        long startTime = System.currentTimeMillis();
        Thread[] threads = new Thread[threadCount];

        for (int i =0; i< threadCount; i++) {
            final int start = i*chunkSize;
            final int end =
                    i == threadCount - 1 ?
                            arr.length : (i + 1) * chunkSize;

            threads[i] = new Thread(() -> {
                for(int j = start; j < end; j++) {
                    arr[j] *= multiplier;
                }
            });
        }

        for (var thread : threads){
            thread.start();
        }

        for (var thread : threads) {
            thread.join();
        }

//        for (int i = 0; i < arr.length; i++) {
//            arr[i] *= multiplier;
//        }

        long end = System.currentTimeMillis();

        System.out.println(end -startTime);
    }
}