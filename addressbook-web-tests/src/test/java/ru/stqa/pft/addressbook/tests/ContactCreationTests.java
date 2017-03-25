package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactCreationTests extends TestBase {


  @Test
  public void testContactCreation() {
    app.getNavigationHelper().returnHomepage();
    app.getContactHelper().initContactCreation();
    app.getContactHelper().fillContactForm(new ContactData("First", "Middle", "Last", "Nickname", "Title", "Company", "Address", "+79010000001", "test@gmail.com", "homepage.com", "3", "2", "1950", "test1"), true);
    app.getContactHelper().submitContactCreation();
    app.getNavigationHelper().returnHomepage();
  }

}
