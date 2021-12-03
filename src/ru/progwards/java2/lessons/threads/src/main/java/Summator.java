import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Summator {
    private final int count;

    public Summator(int count) {
        this.count = count;
    }

    public BigInteger sum(BigInteger number) {
        BigInteger result = null;
        if (number.longValue() <= count) {
            Span span = new Span(0, Long.valueOf(number.toString()));
            FutureTask<BigInteger> future = new FutureTask<>(span);
            new Thread(future).start();
            try {
                result = future.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        } else {
            Span[] edges = prepareEdgesNew(number);
            ExecutorService es = Executors.newFixedThreadPool(count);
            List<Future<BigInteger>> futures = new ArrayList<>();
            for (int i = 0; i < count; i++) {
                Future<BigInteger> future = es.submit(edges[i]);
                futures.add(future);
            }
            try {
                result = (futures.get(0)).get();
                for (int i = 1; i < count; i++) {
                    result = result.add(futures.get(i).get());
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            es.shutdown();
        }
        return result;
    }

    @Deprecated
    private Span[] prepareEdges(BigInteger number) {
        BigInteger divided = number.divide(new BigInteger(String.valueOf(this.count)));
        Span[] nums = new Span[count];
        BigInteger from = new BigInteger("1");
        BigInteger to;
        for (int i = count - 1; i > 0; i--) {
            to = from.add(divided);
            nums[i] = new Span(Long.parseLong(from.toString()), Long.parseLong(to.toString()));
            from = to.add(BigInteger.ONE);
        }
        nums[0] = new Span(Long.parseLong(from.toString()), Long.parseLong(number.toString()));
        return nums;
    }

    private Span[] prepareEdgesNew(BigInteger number) {
        Span[] nums = new Span[count];
        long divided = number.longValue();
        divided /= this.count;
        long from = 1L;
        long to;
        for (int i = count - 1; i > 0 ; i--) {
            to = from + divided;
            nums[i] = new Span(from, to);
            from = to + 1;
        }
        nums[0] = new Span(from, number.longValue());
        return nums;
    }

    public static void main(String[] args) {
        int num = 1356543;
        long l = 0;
        for (int i = 0; i <= num; i++) {
            l = l + i;
        }
        System.out.println("long   = " + l);

        long time = System.currentTimeMillis();
        int tr = 100_000;
        Summator test = new Summator(tr);
        System.out.println("result = " + test.sum(BigInteger.valueOf(num)) + "   потоков - " + tr);
        System.out.println("Время работы: " + (System.currentTimeMillis() - time) + " мс.");

        time = System.currentTimeMillis();
        tr = 2;
        test = new Summator(tr);
        System.out.println("result = " + test.sum(BigInteger.valueOf(num)) + "   потоков - " + tr);
        System.out.println("Время работы: " + (System.currentTimeMillis() - time) + " мс.");
    }
}

class Span implements Callable<BigInteger> {
    private final long from;
    private final long to;

    public Span(long from, long to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public BigInteger call() {
        BigInteger result;
        boolean condition = (to - from) % 2 == 0;
//        if (condition) {
//            BigInteger basic = new BigInteger(String.valueOf(to + from));
//            BigInteger multyplier = new BigInteger(String.valueOf((to - from) / 2));
//            result = basic.multiply(multyplier);
//            result = result.add(new BigInteger(String.valueOf(basic.divide(BigInteger.TWO))));
//        } else {
//            BigInteger basic = new BigInteger(String.valueOf(to + from - 1));
//            BigInteger multyplier = new BigInteger(String.valueOf((to - from) / 2));
//            result = basic.multiply(multyplier);
//            result = result.add(new BigInteger(String.valueOf(basic.divide(BigInteger.TWO))));
//            result = result.add(new BigInteger(String.valueOf(to)));
//        }
        BigInteger basic = new BigInteger(condition ? String.valueOf(to + from) : String.valueOf(to + from - 1));
        BigInteger multyplier = new BigInteger(String.valueOf((to - from) / 2));
        result = basic.multiply(multyplier).add(new BigInteger(String.valueOf(basic.divide(BigInteger.TWO))));
        result = condition ? result : result.add(new BigInteger(String.valueOf(to)));
        return result;
    }
}
