package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.List;

public class ContactDeletionTests extends TestBase {

  @Test
  public void ContactDeletionTests() {
    app.getNavigationHelper().returnHomepage();
    if (! app.getContactHelper().isThereAContact()) {
      app.getContactHelper().createContact(new ContactData("First", "Middle", "Last", "Nickname", "Title", "Company", "Address", "+79010000001", "test@gmail.com", "homepage.com", "3", "2", "1950", null), true);
      app.getNavigationHelper().returnHomepage();
    }
    List<ContactData> before = app.getContactHelper().getContactList();
    app.getContactHelper().selectContact(before.size()-1);
    app.getContactHelper().deleteSelectedContacts();
    app.getNavigationHelper().returnHomepage();
    List<ContactData> after = app.getContactHelper().getContactList();
    Assert.assertEquals(after.size(), before.size() - 1);

    before.remove(before.size()-1);
    Comparator<? super ContactData> byId = (c1, c2) -> Integer.compare(c1.getId(), c2.getId());
    Assert.assertEquals(before,after);

  }

}
