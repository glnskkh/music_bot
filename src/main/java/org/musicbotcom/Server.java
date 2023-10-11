package org.musicbotcom;

public class Server {

  private IO io;
  private boolean running;

  public Server(IO io) {
    this.io = io;
  }

  public void run() {
    this.running = true;
    Command obj = new Command();

    while (running) {
      System.out.print(obj.receiveMessage(io.getMessage()));
    }
  }
}