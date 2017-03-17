package ru.stqa.pft.addressbook;

import org.testng.annotations.Test;

public class ContactCreationTests extends TestBase {


  @Test
  public void testContactCreation() {
    initContactCreation();
    fillContactForm(new ContactData("First", "Middle", "Last", "Nickname", "Title", "Company", "Address", "+79010000001", "test@gmail.com", "homepage.com", "3", "2", "1950"));
    submitContactCreation();
    returnHomepage();
  }

}
