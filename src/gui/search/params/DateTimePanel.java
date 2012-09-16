package gui.search.params;

import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.UIManager;

import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;

public class DateTimePanel extends JPanel {

  private JDateChooser dateChooser;
  private JSpinner timeSpinner;

  /**
   * Create the panel.
   */
  public DateTimePanel() {
    Date now = new Date();

    GridBagLayout gridBagLayout = new GridBagLayout();
    gridBagLayout.columnWidths = new int[] { 125, 0 };
    gridBagLayout.rowHeights = new int[] { 26, 0, 0 };
    gridBagLayout.columnWeights = new double[] { 0.0, Double.MIN_VALUE };
    gridBagLayout.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
    setLayout(gridBagLayout);

    dateChooser = new JDateChooser(now);
    dateChooser.setDateFormatString("dd-MMM-yyyy");
    ((JTextFieldDateEditor) dateChooser.getDateEditor().getUiComponent()).setColumns(12);
    dateChooser.getJCalendar().setWeekOfYearVisible(false);
    dateChooser.getJCalendar().setTodayButtonVisible(true);
    dateChooser.getJCalendar().setMaxDayCharacters(1);
    GridBagConstraints gbc_dateChooser = new GridBagConstraints();
    gbc_dateChooser.insets = new Insets(0, 0, 5, 0);
    gbc_dateChooser.anchor = GridBagConstraints.NORTHWEST;
    gbc_dateChooser.gridx = 0;
    gbc_dateChooser.gridy = 0;
    add(dateChooser, gbc_dateChooser);

    timeSpinner = new JSpinner();
    timeSpinner.setModel(new SpinnerDateModel(now, null, null, Calendar.HOUR));
    JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(timeSpinner, "HH:mm:ss");
    timeSpinner.setEditor(timeEditor);
    GridBagConstraints gbc_spinner = new GridBagConstraints();
    gbc_spinner.anchor = GridBagConstraints.WEST;
    gbc_spinner.gridx = 0;
    gbc_spinner.gridy = 1;
    add(timeSpinner, gbc_spinner);

  }

  @Override
  public void setEnabled(boolean enabled) {
    dateChooser.setEnabled(enabled);
    timeSpinner.setEnabled(enabled);
  }

  public Date getDateTime() {
    Calendar calDate = new GregorianCalendar();
    calDate.setTime(dateChooser.getDate());
    Calendar calTime = new GregorianCalendar();
    calTime.setTime((Date) timeSpinner.getValue());
    calTime.set(calDate.get(Calendar.YEAR), calDate.get(Calendar.MONTH), calDate.get(Calendar.DAY_OF_MONTH));
    return calTime.getTime();
  }

  public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() {
      public void run() {
        try {
          // choose the system L&F
          UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
          // create and pack
          JFrame f = new JFrame("test");
          DateTimePanel cp = new DateTimePanel();
          cp.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
          f.setContentPane(cp);
          f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
          f.pack();
          // center on screen and show
          f.setLocationRelativeTo(null);
          f.setVisible(true);
        } catch (Exception e) {
        }
      }
    });
  }
}
