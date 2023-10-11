package org.musicbotcom;

import java.util.Scanner;

public class CLIIO implements IO {

  private final Scanner scanner = new Scanner(System.in);

  @Override
  public String getMessage() {
    System.out.print("Введите команду: ");
    return scanner.nextLine();
  }

  @Override
  public void postMessage(String message) {
    System.out.println(message);
  }
}
