package org.musicbotcom.commands.service;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.musicbotcom.commands.CommandTest;

@RunWith(JUnit4.class)
public class StartCommandTest extends CommandTest {

  @Test
  public void testIsPolite() {
    sendDefaultUserMessage("/start");

    assertTrue(getLastAnswer().contains("Привет!"));
  }
}