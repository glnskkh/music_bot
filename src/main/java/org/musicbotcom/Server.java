package org.musicbotcom;

public class Server {

  private static final Command commands = new Command();
  private final IO io;
  private boolean running;

  public Server(IO io) {
    this.io = io;
  }

  public void run() {
    running = true;

    while (running) {
      String message = io.getMessage();

      String response = commands.receiveMessage(message);

      io.postMessage(response);
    }
  }

  public void stop() {
    running = false;
  }
}