package jdkbenchmark;

import java.io.FileNotFoundException;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import jdkbenchmark.JdkFight;

public class MyJdkBenchmark {

	@Benchmark @BenchmarkMode(Mode.Throughput) @OutputTimeUnit(TimeUnit.SECONDS)
	public void testJdkFight() throws FileNotFoundException {
		JdkFight.main(null);
	}

	 public static void main(String[] args) throws RunnerException {
	        Options opt = new OptionsBuilder()
	                .include(MyJdkBenchmark.class.getSimpleName())
	                .forks(1)
	                .build();

	        new Runner(opt).run();
	 }

}
