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
      io.postMessage(commands.receiveMessage(io.getMessage()));
    }
  }

  public void stop() {
    running = false;
  }
}