package apcs.searchsort;

import java.util.ArrayList;
import java.util.List;

/**
 * A flexible class to profile operations.
 * 
 * @author William Chargin
 * 
 * @param <I>
 *            input for the profiler
 * @param <O>
 *            output from each run
 * @param <F>
 *            final output
 */
public abstract class Profiler<I, O extends TimeStorage, F> {

	/**
	 * Invoked before the profiling begins. This will be run once per call to
	 * {@link #profile(Object, int)}. The default implementation does nothing.
	 * 
	 * @param input
	 *            the input for the profiling call
	 */
	protected void beforeProfiling(I input) {
	}

	/**
	 * Invoked before each run. The default implementation does nothing.
	 * 
	 * @param input
	 *            the input for the profiling call
	 */
	protected void beforeRun(I input) {
	}

	/**
	 * Profiles the input.
	 * 
	 * @param input
	 *            the input for the profiling call
	 * @return the output for this run
	 */
	protected abstract O run(I input);

	/**
	 * Invoked after each run. The default implementation does nothing.
	 * 
	 * @param input
	 *            the input for the profiling call
	 */
	protected void afterRun(I input) {
	}

	/**
	 * Invoked after the profiling has completed. The default implementation
	 * does nothing.
	 * 
	 * @param input
	 *            the input for the profiling call
	 */
	protected void afterProfiling(I input) {
	}

	/**
	 * Synthesizes the run data into a single output.
	 * 
	 * @param o
	 *            the list of data from each run
	 * @return the synthesized output
	 */
	protected abstract F synthesize(List<O> o);

	/**
	 * Runs the profiler.
	 * 
	 * @param input
	 *            the input parameters
	 * @param runs
	 *            the number of runs
	 * @return the final output
	 */
	public F profile(I input, int runs) {
		beforeProfiling(input);

		List<O> outputs = new ArrayList<O>(runs);

		long[] times = new long[runs];
		for (int i = 0; i < runs; i++) {
			beforeRun(input);

			long startTime = System.nanoTime();
			O o = run(input);
			long time = System.nanoTime() - startTime;

			outputs.add(o);
			o.setTime(time);

			times[i] = time;
			afterRun(input);
		}

		afterProfiling(input);

		return synthesize(outputs);
	}

	/**
	 * Automatically generates a standard result for the given set of outputs.
	 * 
	 * @param outputs
	 *            the outputs
	 * @return the result
	 */
	protected static ProfilingResult generateStandardResult(
			List<? extends TimeStorage> outputs) {
		double each = 1d / outputs.size();
		double mean = 0;
		double stddev = 0;
		for (TimeStorage tr : outputs) {
			mean += each * tr.getTime();
		}
		for (int i = 0; i < outputs.size(); i++) {
			stddev += each * Math.pow(mean - outputs.get(i).getTime(), 2);
		}
		stddev = Math.sqrt(stddev);
		return new ProfilingResult(mean, stddev);
	}

}
