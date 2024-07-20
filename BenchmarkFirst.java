package org.sample;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)
// @Threads(1)
@State(Scope.Benchmark)
public class BenchmarkFirst {

    // run using
    // java -jar target/benchmarks.jar -p fileName="C:\\Users\\c21061629\\PerformanceScalability\\JMHtest\\src\\main\\java\\org\\sample\\data.csv"

    // test with different values of id
    // @Param({"1", "50", "100", "150", "200", "250", "500", "1000"})
     // @Param({"10", "20", "30", "40", "50", "60", "70", "80", "90", "100"})
//    @Param({"1", "100", "200", "300", "400", "500", "600", "700", "800", "900", "1000"})
    public int id;

    // csv file to be tested with
    @Param({"C:\\Users\\c21061629\\PerformanceScalability\\JMHtest\\src\\main\\java\\org\\sample\\data.csv"})
    public String fileName;

    // Coursework reference
    static Coursework cw;

    // city name for searching
    @Param({"Bristol", "Birmingham", "Reading", "Sheffield", "Norwich", "Bridgend", "Swansea", "Richmond", "Ongar", "Worcester"})
    public String city;

    // number of recent sales to be printed in for loop
    // @Param({"10", "20", "30", "40", "50", "60", "70", "80", "90", "100"})
//     @Param({"100", "200", "300", "400", "500", "600", "700", "800", "900", "1000"})
    public int numOfRecentSales;

    // main method to allow args to specify the filename path
    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(BenchmarkFirst.class.getSimpleName())
                .param("fileName", "C:\\Users\\c21061629\\PerformanceScalability\\JMHtest\\src\\main\\java\\org\\sample\\data.csv")
                .build();

        new Runner(opt).run();
    }

    @Setup
    public void setup() {
        cw = new Coursework(fileName);
    }

//    @Benchmark
    public void benchmarkGetPropertyReadLinesLinear (Blackhole bh) {
         bh.consume(cw.getPropertyLinearSearch(id));
    }

     // @Benchmark
    public void benchmarkGetPropertyFindFirst (Blackhole bh) {
        bh.consume(cw.getPropertyFindFirst(id));
    }

     // @Benchmark
    public void benchmarkGetPropertyBinarySearch (Blackhole bh) {
        bh.consume(cw.getPropertyBinarySearch(id));
    }

     @Benchmark
    public void benchmarkGetTotalSalesReadLinesLinear (Blackhole bh) {
        bh.consume(cw.getTotalSalesForCityLinear(city));
    }

    // @Benchmark
//    public void benchmarkGetTotalSalesLineNumberReader (Blackhole bh) {
//        bh.consume(cw.getTotalSalesForCityLineNumReader(city));
//    }

    @Benchmark
    public void benchmarkGetTotalSalesSorted (Blackhole bh) {
        bh.consume(cw.getTotalSalesForCitySortCity(city));
    }


    @Benchmark
    public void benchmarkGetTotalSalesLineNumberReaderSimpl (Blackhole bh) {
        bh.consume(cw.getTotalSalesForCityLineNumReaderSimpl(city));
    }

//    @Benchmark
    public void benchmarkGetMostRecentSoldLinearSort (Blackhole bh) {
        bh.consume(cw.getMostRecentPropertiesSoldLinear(numOfRecentSales));
    }

//    @Benchmark
    public void benchmarkGetMostRecentSoldReverseOrder (Blackhole bh) {
        bh.consume(cw.getMostRecentPropertiesSoldLinearReversed(numOfRecentSales));
    }

//   @Benchmark
    public void benchmarkGetMostRecentSoldLineReader (Blackhole bh) {
        bh.consume(cw.getMostRecentPropertiesSoldLineReader(numOfRecentSales));
    }


}
