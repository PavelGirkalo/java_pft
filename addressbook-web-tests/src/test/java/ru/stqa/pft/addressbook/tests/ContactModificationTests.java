package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.List;

public class ContactModificationTests extends TestBase {

  @Test
  public void testContactModification() {
    app.getNavigationHelper().returnHomepage();
    if (! app.getContactHelper().isThereAContact()) {
      app.getContactHelper().createContact(new ContactData("First_mod", "Middle", "Last_mod", "Nickname", "Title", "Company", "Address", "+79010000001", "test@gmail.com", "homepage.com", "3", "2", "1950", "test1"), true);
      app.getNavigationHelper().returnHomepage();
    }
    List<ContactData> before = app.getContactHelper().getContactList();
    app.getContactHelper().selectContact(before.size()-1);
    app.getContactHelper().initContactModification(before.size()-1);
    ContactData contact = new ContactData(before.get(before.size()-1).getId(),"First_mod", "Middle", "Last_mod", "Nickname", "Title", "Company", "Address", "+79010000001", "test@gmail.com", "homepage.com", "3", "2", "1950", null);
    app.getContactHelper().fillContactForm(contact, false);
    app.getContactHelper().submitContactModification();
    app.getNavigationHelper().returnHomepage();
    List<ContactData> after = app.getContactHelper().getContactList();
    Assert.assertEquals(after.size(), before.size());

    before.remove(before.size()-1);
    before.add(contact);
    Comparator<? super ContactData> byId = (c1, c2) -> Integer.compare(c1.getId(), c2.getId());
    before.sort(byId);
    after.sort(byId);
    Assert.assertEquals(before,after);

  }
}
