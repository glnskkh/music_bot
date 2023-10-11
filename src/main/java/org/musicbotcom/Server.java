package org.musicbotcom;

public class Server {

  private IO io;
  private boolean running;
  private static Command commands = new Command();

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