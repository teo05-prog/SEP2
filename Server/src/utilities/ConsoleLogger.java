package utilities;

public class ConsoleLogger extends Logger
{
  private LogLevel logLevel;

  public ConsoleLogger(LogLevel logLevel)
  {
    super(logLevel);
    this.logLevel = logLevel;
  }

  @Override public void log(String text, LogLevel level)
  {
    if (level.ordinal() >= logLevel.ordinal())
    {
      System.out.println(level.name() + ": " + text);
    }
  }
}
