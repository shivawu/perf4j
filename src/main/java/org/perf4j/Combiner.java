package org.perf4j;

/**
 * Combiner Interface
 *
 * @author Shiva Wu
 */
public interface Combiner<T> {
	public T merge(T other);
}