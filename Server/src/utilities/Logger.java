package utilities;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {
  private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
  private LogLevel minLevel;

  public Logger(LogLevel minLevel) {
    this.minLevel = minLevel;
  }

  public void log(String message, LogLevel level) {
    if (level.ordinal() >= minLevel.ordinal()) {
      String timestamp = LocalDateTime.now().format(formatter);
      System.out.println("[" + timestamp + "] [" + level + "] " + message);
    }
  }
}