package main;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

/**
 * Formats loging messages as one line strings, no header and footer.<br/>
 * A line is as follows:
 * 
 * <pre>
 * YYYY-MM-DD hh:mm:ss.sss LEVEL message
 * </pre>
 * 
 * If the message is bundled with a trowable object it is printed on a second
 * line.
 * 
 * @author sasha
 */
public class OneLineFormatter extends Formatter {
	private SimpleDateFormat df = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss.SSS");

	public String format(LogRecord rec) {
		StringBuilder buf = new StringBuilder(200);
		buf.append(df.format(new Date(rec.getMillis()))).append(' ');
		buf.append(String.format("%7s", rec.getLevel().toString()));
		buf.append(": ").append(formatMessage(rec)).append('\n');
		if (rec.getThrown() != null) {
			buf.append(rec.getThrown().toString()).append('\n');
		}
		return buf.toString();
	}

	public String getHead(Handler h) {
		return "";
	}

	public String getTail(Handler h) {
		return "";
	}
}
