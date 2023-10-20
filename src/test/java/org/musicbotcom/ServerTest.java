package org.musicbotcom;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ServerTest {

  private final int millisecondsTimeout = 100;
  private Server testServer;

  private static Stream<Arguments> provideIOCommands() {
    return Stream.of(Arguments.of("/help",
        "Это бот позволяет переносить аудиозаписи из вконтакте прямиком в диалог с ботом\n"
            + "Помимо этого бот также умеет находить треки близкие по жанрам, а также по звучанию\n"
            + "Перечень команд:\n" + "<...>\n<...>\n<...>\n"), Arguments.of("/start", "Привет!\n"
        + "Это бот позволяет переносить аудиозаписи из вконтакте прямиком в диалог с ботом\n"
        + "Помимо этого бот также умеет находить треки близкие по жанрам, а также по звучанию\n"
        + "Для привязки своего профиля ВК, отправьте боту <сслыка на бота в вк> код:\n"
        + "<копируемое сообщение>\n\n"
        + "После привязки, бот ВКонтакте оставит инструкцию по использованию бота\n"));
  }

  @ParameterizedTest
  @MethodSource("provideIOCommands")
  void testIO(String command, String expected) throws InterruptedException {
    var testIO = new TestIO(command);

    testServer = new Server(testIO);

    new Thread(() -> testServer.run()).start();

    Thread.sleep(millisecondsTimeout);

    testServer.stop();

    assertEquals(expected, testIO.answer);
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