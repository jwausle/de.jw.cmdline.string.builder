package de.jw.validation;

import com.google.common.base.Preconditions;
import de.jw.validation.ExecutionLog.Level;

/**
 * Represents a single ValidationResultLine that holds
 * <ul>
 * <li>a level,
 * <li>an (error/warning) message,
 * <li>a detailed message and
 * <li>an (error/warning) context object.
 * </ul>
 * 
 * @see ExecutionLog
 * 
 * @since 0.3.5
 * 
 */
public class ExecutionLogLine {

	/**
	 * Create a new ValidationResultLine.
	 * 
	 * @param level
	 *            the level of this ValidationResultLine; must not be null
	 * @param message
	 *            the message of this ValidationResultLine; must not be null
	 * @param detailMessage
	 *            the detailed message of this ValidationResultLine; null is a
	 *            valid value.
	 * @param context
	 *            the context object of this ValidationResultLine; null is a
	 *            valid value
	 */
	public ExecutionLogLine(final Level level, final String message, final String detailMessage,
			final Object context) {
		Preconditions.checkNotNull(level);
		Preconditions.checkNotNull(message);

		this.level = level;
		this.message = message;
		this.detailMessage = detailMessage == null ? message : detailMessage;
		this.context = context;
	}

	// {{ level
	private final Level level;

	public final static String PROP_level = "level";

	/**
	 * returns the level of this ValidationResultLine
	 * 
	 * @return the level
	 */
	public Level getLevel() {
		return level;
	}

	// }}

	// {{ message

	private final String message;

	public final static String PROP_message = "message";

	/**
	 * returns the message of this ValidationResultLine
	 * 
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	// }}

	// {{ detailMessage
	private final String detailMessage;
	public final static String PROP_detailMessage = "detailMessage";

	/**
	 * returns the detailed message of this ValidationResultLine
	 * 
	 * @return the detailed message, which can be null
	 */
	public String getDetailMessage() {
		return detailMessage;
	}

	// }}

	// {{ context

	private final Object context;
	public final static String PROP_context = "context";

	/**
	 * returns the context object, which might by null
	 * 
	 * @return the context object, can by null
	 */
	public Object getContext() {
		return context;
	}

	// }}

	// {{ titled

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("ValidationResultLine [");
		if (level != null) {
			builder.append("level=");
			builder.append(level);
			builder.append(", ");
		}
		if (message != null) {
			builder.append("message=");
			builder.append(message);
			builder.append(", ");
		}
		if (detailMessage != null) {
			builder.append("detailMessage=");
			builder.append(detailMessage);
			builder.append(", ");
		}
		if (context != null) {
			builder.append("context=");
			builder.append(context);
		}
		builder.append("]");
		return builder.toString();
	}

	// }}

}
