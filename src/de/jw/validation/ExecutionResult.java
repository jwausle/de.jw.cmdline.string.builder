package de.jw.validation;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;

/**
 * Container to collect a execution-result and a execution-log. Execution result
 * correspond normally to return value and the execution-log is equal to a
 * collection of log-messages while a logger.
 * 
 * @author winter
 * 
 * @param <T>
 *            type of result value.
 */
public class ExecutionResult<T> {
	private final Optional<T> result;
	private final ExecutionLog executionResultLog;

	/** Default constructor with no result and warning-log. */
	public ExecutionResult() {
		this.result = Optional.absent();
		this.executionResultLog = ExecutionLog.warning("null execution-result constructed.");
	}

	/** Default constructor with no result and given logger. */
	public ExecutionResult(ExecutionLog executionResultLog2) {
		Preconditions.checkNotNull(executionResultLog2, "executionResultLog must not be null.");

		this.result = Optional.absent();
		this.executionResultLog = executionResultLog2;
	}

	/** Default constructor with result and given logger(not null approved). */
	public ExecutionResult(T result, ExecutionLog executionResultLog) {
		Preconditions.checkNotNull(executionResultLog, "executionResultLog must not be null.");

		this.result = Optional.fromNullable(result);
		this.executionResultLog = executionResultLog;
	}

	/** Return the setted result. @return maybe null result value. */
	public T getResult() {
		return result.get();
	}

	public ExecutionLog getExecutionLog() {
		return executionResultLog;
	}

	public boolean isValid() {
		return executionResultLog.isValid();
	}

	/**
	 * Reset the wrapped result and concat the logs.
	 * 
	 * @param newResult
	 *            new wrapped result.
	 * @return Merged execution-result with newResult as wrapped value.
	 */
	public <R> ExecutionResult<R> reset(ExecutionResult<R> newResult) {
		Preconditions.checkNotNull(newResult, "newExcutionResult must not be null.");
		ExecutionLog mergedExecutionResultLog = getExecutionLog().concat(newResult.getExecutionLog());
		return new ExecutionResult<R>(newResult.getResult(), mergedExecutionResultLog);
	}

	/**
	 * Reset the wrapped result with history log.
	 * 
	 * @param newResult
	 * @return Merged execution-result with newResult as wrapped value.
	 */
	public <R> ExecutionResult<R> reset(R newResult) {
		return new ExecutionResult<R>(newResult, getExecutionLog());
	}
}
