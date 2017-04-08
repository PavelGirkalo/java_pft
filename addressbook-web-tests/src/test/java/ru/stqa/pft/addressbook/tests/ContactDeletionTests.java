package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.List;

public class ContactDeletionTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    app.goTo().homepage();
    if (app.contact().list().size() == 0) {
      app.contact().create(new ContactData()
              .withFirstname("First").withMiddlename("Middle").withLastname("Last")
              .withNickname("Nickname").withTitle("Title").withCompany("Company").withAddress("Address")
              .withMobile("+79010000001").withEmail("test@gmail.com").withHomepage("homepage.com")
              .withBday("3").withBmonth("2").withBirthyear("1950").withGroup("test1")
              , true);
      app.goTo().homepage();
    }
  }

  @Test(enabled = true)
  public void ContactDeletionTests() {
    List<ContactData> before = app.contact().list();
    int index = before.size()-1;
    app.contact().delete(index);
    app.goTo().homepage();
    List<ContactData> after = app.contact().list();
    Assert.assertEquals(after.size(), before.size() - 1);

    before.remove(index);
    Comparator<? super ContactData> byId = (c1, c2) -> Integer.compare(c1.getId(), c2.getId());
    Assert.assertEquals(before,after);

  }

}
