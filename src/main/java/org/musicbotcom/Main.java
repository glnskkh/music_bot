package org.musicbotcom;

public class Main {

  public static void main(String[] args) {
    CLIIO io = new CLIIO();
    Server server = new Server(io);
    server.run();
  }
}