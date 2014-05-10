/**
 * 
 */
package cli;

/**
 * @author sasha
 * 
 */
public interface CommonCLArgs {

  // @Option(shortName = "t", defaultValue = "10000")
  public int getTimeout();

  public boolean isTimeout();

  // @Option(shortName = "v")
  public boolean getVerbose();

  public boolean isVerbose();

  // @Option(helpRequest = true)
  boolean getHelp();

  // @Unparsed
  String getEndPoint();

  public boolean isEndpoint();

}