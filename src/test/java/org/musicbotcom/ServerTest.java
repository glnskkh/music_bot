package org.musicbotcom;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ServerTest {

  private Server testServer;

  @Test
  void testHelp() throws InterruptedException {
    var help = new TestIO("/help");

    testServer = new Server(help);

    new Thread(() -> testServer.run()).start();

    Thread.sleep(1000);

    testServer.stop();

    assertEquals("Это бот позволяет переносить аудиозаписи из вконтакте прямиком в диалог с ботом\n"
        + "Помимо этого бот также умеет находить треки близкие по жанрам, а также по звучанию\n"
        + "Перечень команд:\n" + "<...>\n<...>\n<...>\n", help.answer);
  }


  @Test
  void testStart() throws InterruptedException {
    var start = new TestIO("/start");

    testServer = new Server(start);

    new Thread(() -> testServer.run()).start();

    Thread.sleep(1000);

    testServer.stop();

    assertEquals("Привет!\n"
        + "Это бот позволяет переносить аудиозаписи из вконтакте прямиком в диалог с ботом\n"
        + "Помимо этого бот также умеет находить треки близкие по жанрам, а также по звучанию\n"
        + "Для привязки своего профиля ВК, отправьте боту <сслыка на бота в вк> код:\n"
        + "<копируемое сообщение>\n\n"
        + "После привязки, бот ВКонтакте оставит инструкцию по использованию бота\n", start.answer);
  }

  static class TestIO implements IO {

    String answer;
    String request;

    TestIO(String request) {
      this.request = request;
    }

    @Override
    public String getMessage() {
      return request;
    }

    @Override
    public void postMessage(String message) {
      answer = message;
    }
  }
}