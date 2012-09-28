package de.jw.validation2;

import java.util.Collections;
import java.util.EnumSet;
import java.util.LinkedList;
import java.util.List;

import com.google.common.base.Optional;
import com.google.common.base.Strings;

/**
 * An object encapsulating error messages as a result of a validation process.
 * 
 * @see Validatable
 * @see ExecutionLogLine
 * 
 */
public class ExecutionLog {
	public enum Level {
		DEBUG, INFO, WARN, ERROR;

		public static Level[] orderLevels() {
			return new Level[] { ERROR, WARN, INFO, DEBUG };
		}
	}

	public enum OrderBy {
		OCCURENCE, LEVEL;
	}
 
	private List<ExecutionLogLine> validationResultLines = new LinkedList<ExecutionLogLine>();

	public ExecutionLog() {
	}

	public ExecutionLog(final String... messages) {
		addMessage(messages);
	}

	public List<String> getErrorMessages() {
		return getMessages(EnumSet.of(Level.ERROR), OrderBy.OCCURENCE);
	}

	public List<String> getMessages(final EnumSet<Level> levels, final OrderBy orderBy) {
		final LinkedList<String> messageList = new LinkedList<String>();
		List<ExecutionLogLine> lines = getLines();
		for (ExecutionLogLine validationResultLine : lines) {
			messageList.add(validationResultLine.getLevel() + " " + validationResultLine.getMessage() + " detail: "
					+ validationResultLine.getDetailMessage());
		}

		return messageList;
	}

	public final static String PROP_lines = "lines";

	public List<ExecutionLogLine> getLines() {
		return Collections.unmodifiableList(validationResultLines);
	}

	public ExecutionLog addDetailMessage(final String shortMessage, final String detailMessage) {
		return addDetailMessage(shortMessage, detailMessage, null);
	}

	public ExecutionLog addDetailMessage(final String shortMessage, final String detailMessage, final Object context) {
		return addMessage(Level.ERROR, shortMessage, detailMessage, context);
	}

	public ExecutionLog addErrorWhenNot(final Boolean assertion, final String message) {
		if (!assertion) {
			addMessage(message);
		}
		return this;
	}

	public ExecutionLog addMessage(final Level level, final String shortMessage, final String detailMessage,
			final Object context) {
		validationResultLines.add(new ExecutionLogLine(level, shortMessage, detailMessage, context));
		return this;
	}

	public ExecutionLog addMessage(final Level level, final String shortMessage, final String detailMessage) {
		return addMessage(level, shortMessage, detailMessage, null);
	}

	public ExecutionLog addMessage(final Level level, final String shortMessage) {
		return addMessage(level, shortMessage, null, null);
	}

	public ExecutionLog addMessage(final String... messages) {
		for (final String message : messages) {
			validationResultLines.add(new ExecutionLogLine(Level.ERROR, message, null, null));
		}
		return this;
	}

	public ExecutionLog addError(final String shortMessage, final String detailMessage, final Object context) {
		validationResultLines.add(new ExecutionLogLine(Level.ERROR, shortMessage, detailMessage, context));
		return this;
	}

	public ExecutionLog addError(final String shortMessage, final String detailMessage) {
		return addMessage(Level.ERROR, shortMessage, detailMessage, null);
	}

	public ExecutionLog addError(final String shortMessage) {
		return addMessage(Level.ERROR, shortMessage, null, null);
	}

	public ExecutionLog addWarn(final String shortMessage, final String detailMessage, final Object context) {
		validationResultLines.add(new ExecutionLogLine(Level.WARN, shortMessage, detailMessage, context));
		return this;
	}

	public ExecutionLog addWarn(final String shortMessage, final String detailMessage) {
		return addMessage(Level.WARN, shortMessage, detailMessage, null);
	}

	public ExecutionLog addWarn(final String shortMessage) {
		return addMessage(Level.WARN, shortMessage, null, null);
	}

	public ExecutionLog addDebug(final String shortMessage, final String detailMessage, final Object context) {
		validationResultLines.add(new ExecutionLogLine(Level.DEBUG, shortMessage, detailMessage, context));
		return this;
	}

	public ExecutionLog addDebug(final String shortMessage, final String detailMessage) {
		return addMessage(Level.DEBUG, shortMessage, detailMessage, null);
	}

	public ExecutionLog addDebug(final String shortMessage) {
		return addMessage(Level.DEBUG, shortMessage, null, null);
	}

	/**
	 * Add error message if condition is true.
	 * 
	 * @param condition
	 * @param message
	 * @return this
	 */
	public ExecutionLog addErrorIf(final boolean condition, final String message) {
		if (condition) {
			addMessage(message);
		}
		return this;
	}

	// }}

	public boolean isValid() {
		for (final ExecutionLogLine line : validationResultLines) {
			if (line.getLevel().equals(Level.ERROR)) {
				return false;
			}
		}
		return true;
	}

	public static ExecutionLog ok() {
		return new ExecutionLog();
	}

	public static ExecutionLog error(String errorMsg) {
		return ExecutionLog.ok().addMessage(Level.ERROR, errorMsg);
	}

	public static ExecutionLog warning(String warnMsg) {
		return ExecutionLog.ok().addMessage(Level.WARN, warnMsg);
	}

	/**
	 * Concatenation of an other ValidationResult to the current instance. The
	 * concatenation of two valid {@link ExecutionLog}s result in an also
	 * valid {@link ExecutionLog}.
	 * 
	 * @param other
	 *            The {@link ExecutionLog} to concat.
	 * @return
	 */
	public ExecutionLog concat(final ExecutionLog other) {
		validationResultLines.addAll(other.validationResultLines);
		return this;
	}

	public ExecutionLog applyMessagePrefix(final String prefix) {
		if (prefix != null) {
			final List<ExecutionLogLine> newValidationResultLines = new LinkedList<ExecutionLogLine>();
			for (final ExecutionLogLine line : validationResultLines) {
				newValidationResultLines.add(new ExecutionLogLine(line.getLevel(), prefix + line.getMessage(),
						prefix + line.getDetailMessage(), line.getContext()));
			}
			validationResultLines = newValidationResultLines;
		}
		return this;
	}

	public boolean hasErrors() {
		return getMessages(EnumSet.of(Level.ERROR), OrderBy.OCCURENCE).size() > 0;
	}

	public boolean hasWarnings() {
		return getMessages(EnumSet.of(Level.WARN), OrderBy.OCCURENCE).size() > 0;
	}

	public boolean hasInfos() {
		return getMessages(EnumSet.of(Level.INFO), OrderBy.OCCURENCE).size() > 0;
	}

	@Override
	public String toString() {
		return "ValidationResult[" + (isValid() ? "(VALID) " : "(NOT VALID)") + asFormatedString() + "]";
	}

	public String asFormatedStringWithErrorLevel() {
		return internalAsFormatedString(true);
	}

	public String asFormatedString() {
		return internalAsFormatedString(false);
	}

	private String internalAsFormatedString(final boolean printWithErrorLevel) {
		final List<String> msgs = new LinkedList<String>();
		for (final ExecutionLogLine line : getLines()) {
			final String message = Strings.isNullOrEmpty(line.getDetailMessage()) ? line.getMessage() : line
					.getDetailMessage();

			final String errorLevelStr = printWithErrorLevel ? Strings.padEnd(line.getLevel().toString(), 10, ' ') : "";
			msgs.add(errorLevelStr + message);
		}

		return join("\n", msgs);
	}

	private String join(String joiner, List<String> msgs) {
		StringBuilder stringBuilder = new StringBuilder();
		for (String msg : msgs) {
			stringBuilder.append(msg).append(joiner);
		}

		if (stringBuilder.length() > 0)// remove last joiner
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
		return stringBuilder.toString();
	}

	/**
	 * Return the highest error level (Error=High) or nothing if no level
	 * exists.
	 */
	public Optional<Level> hightestLevel() {
		for (final Level l : Level.orderLevels()) {
			if (getMessages(EnumSet.of(l), OrderBy.LEVEL).size() > 0) {
				return Optional.of(l);
			}
		}
		return Optional.absent();
	}

	public <T> ExecutionResult<T> asExecutionResult(T result){
		ExecutionResult<T> executionResult = new ExecutionResult<T>(result,this);
		return executionResult;
	}
}
