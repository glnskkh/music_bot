package org.musicbotcom.commands.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.musicbotcom.commands.CommandTest;
import org.musicbotcom.commands.ProcessCommand;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@RunWith(JUnit4.class)
public class UnknownCommandTest extends CommandTest {

  @Override
  public void setUpBot() throws TelegramApiException {
    super.setUpBot();

    getDefaultUser().changeNextCommand(new ProcessCommand());
  }

  @Test
  public void testIdentifyUnknownCommand() {
    sendDefaultUserMessage("/asdsad");

    assertFalse(getLastAnswer().contains("Введите"));
    assertTrue(getLastAnswer().contains("Бот не знает такой комманды"));
  }

  @Test
  public void testShowHelp() {
    sendDefaultUserMessage("/adsdsa");

    assertTrue(getLastAnswer().contains("/help"));
  }
}