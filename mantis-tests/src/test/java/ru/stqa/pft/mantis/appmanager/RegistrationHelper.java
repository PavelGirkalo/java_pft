package ru.stqa.pft.mantis.appmanager;


import org.openqa.selenium.By;
import ru.stqa.pft.mantis.model.User;

public class RegistrationHelper extends HelperBase {

  public RegistrationHelper(ApplicationManager app) {
    super(app);
  }

  public void start(String username, String email) {
    wd.get(app.getProperty("web.baseUrl") + "/signup_page.php");
    type(By.name("username"), username);
    type(By.name("email"), email);
    click(By.cssSelector("input[type='submit']"));

  }

  public void finish(String confirmationLink, String password) {
    wd.get(confirmationLink);
    type(By.name("password"), password);
    type(By.name("password_confirm"), password);
    click(By.cssSelector("button[type='submit']"));
  }

  public void loginAdmin() {
    wd.get(app.getProperty("web.baseUrl") + "/login_page.php");
    type(By.name("username"), "administrator");
    type(By.name("password"), "root");
    click(By.cssSelector("input[type='submit']"));

  }

  public User resetPassword() {
    wd.get(app.getProperty("web.baseUrl") + "/manage_user_page.php");
    //click(By.cssSelector("button[id='menu-toggler']"));
    //click(By.cssSelector("a[href='/mantisbt/manage_overview_page.php']"));
    //click(By.cssSelector("a[href='/mantisbt/manage_user_page.php']"));
    click(By.xpath("//a[contains(text(),'user')]"));
    String name = wd.findElement(By.cssSelector("input[id='edit-username']")).getAttribute("value");
    String email = wd.findElement(By.cssSelector("input[id='email-field']")).getAttribute("value");
    click(By.cssSelector("input[value='Сбросить пароль']"));
    return new User(name, email);

  }
}
