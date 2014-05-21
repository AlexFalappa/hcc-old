package net.falappa.logging;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

/**
 * Formats loging messages as one line strings, no header and footer.
 * <p>
 * A line is as follows:
 * <pre>
 * YYYY-MM-DD hh:mm:ss.sss LEVEL message
 * </pre> If the message is bundled with a trowable object it is printed on a second line.
 *
 * @author sasha
 */
public class OneLineFormatter extends Formatter {

    private final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    @Override
    public String format(LogRecord rec) {
        StringBuilder buf = new StringBuilder(200);
        buf.append(df.format(new Date(rec.getMillis()))).append(' ');
        buf.append(String.format("%7s", rec.getLevel().toString()));
        buf.append(": ").append(formatMessage(rec)).append('\n');
        final Throwable throwable = rec.getThrown();
        if (throwable != null) {
            buf.append(throwable.toString()).append('\n');
        }
        return buf.toString();
    }

    @Override
    public String getHead(Handler h) {
        return "";
    }

    @Override
    public String getTail(Handler h) {
        return "";
    }
}
