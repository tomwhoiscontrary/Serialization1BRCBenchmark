package benchmark;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String... args) throws Exception {
        List<Benchmark> benchmarks = List.of(
                new Bench_Fury_String(),
                new Bench_Fury_Ordinal(),
                new Bench_CustomFileChannel_Strings(),
                new Bench_CustomFileChannel_Ordinal(),
                new Bench_ObjectOutputStream(),
                new Bench_DataOutputStream_Columnar(),
                new Bench_DataOutputStream_Interleaved(),
                new Bench_Parquet(),
                new Bench_Protobuf_InputStream_String(),
                new Bench_Protobuf_InputStream_Ordinal(),
                new Bench_Protobuf_NIO_String(),
                new Bench_Protobuf_NIO_Ordinal(),
                new Bench_Protobuf_NIO_Ordinal32k(), // hn comment asserted 4k is too small a buffer
                new Bench_RAM_CityOrdinals(),
                new Bench_RAM_CityStrings()
        );

        Map<String, Benchmark.BenchmarkResult> results = new HashMap<>();

        for (var benchmark : benchmarks) {
            System.err.println("Running " + benchmark.getName());

            results.put(benchmark.getName(), benchmark.run(BenchmarkParameters.iters));
        }

        System.out.println("*** Results ***");

        results.entrySet()
                .stream()
                .sorted(Comparator.comparing(e -> e.getValue().timeS()))
                .forEach(entry -> {
                    System.out.println(entry.getKey() + ": " + entry.getValue());
                });
    }
}
