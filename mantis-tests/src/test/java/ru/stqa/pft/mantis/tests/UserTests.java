package ru.stqa.pft.mantis.tests;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.lanwen.verbalregex.VerbalExpression;
import ru.stqa.pft.mantis.model.MailMessage;
import ru.stqa.pft.mantis.model.UserData;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

import static org.testng.Assert.assertTrue;

public class UserTests extends TestBase{

  @BeforeClass
  public void startMailServer() {
    app.mail().start();
  }

  @Test
  public void testRegistration() throws IOException, MessagingException {
    long counter = System.currentTimeMillis();
    String user = String.format("user%s", counter);
    String password = "password";
    String email = String.format("user%s@localhost.localdomain", counter);
    app.registration().start(user, email);
    List<MailMessage> mailMessages = app.mail().waitForMail(2, 10000);
    String confirmationLink = findConfirmationLink(mailMessages, email);
    app.registration().finish(confirmationLink, password);
    assertTrue(app.newSession().login(user, password));
  }

  @Test
  public void testChangePassword() throws IOException, MessagingException {
    app.registration().loginAdmin();
    UserData currentUser = app.db().currentUser();
    app.registration().selectUser(currentUser);
    app.registration().resetPassword();
    List<MailMessage> mailMessages = app.mail().waitForMail(1, 10000);
    String confirmationLink = findConfirmationLink(mailMessages, currentUser.getEmail());
    String password = "password";
    app.registration().finish(confirmationLink, password);
    assertTrue(app.newSession().login(currentUser.getName(), password));
  }

  @Test(enabled = false)
  public void testChangePasswordOld() throws IOException, MessagingException {
    // for success test need create user with name contains "user"
    app.registration().loginAdmin();
    UserData randomUser = app.registration().resetPasswordOld();
    List<MailMessage> mailMessages = app.mail().waitForMail(1, 10000);
    String confirmationLink = findConfirmationLink(mailMessages, randomUser.getEmail());
    String password = "password";
    app.registration().finish(confirmationLink, password);
    assertTrue(app.newSession().login(randomUser.getName(), password));
  }


  private String findConfirmationLink(List<MailMessage> mailMessages, String email) {
    MailMessage mailMessage = mailMessages.stream().filter((m) -> m.to.equals(email)).findFirst().get();
    VerbalExpression regex = VerbalExpression.regex().find("http:/").nonSpace().oneOrMore().build();
    return regex.getText(mailMessage.text);
  }

  @AfterClass(alwaysRun = true)
  public void stopMailServer() {
    app.mail().stop();
  }

}
