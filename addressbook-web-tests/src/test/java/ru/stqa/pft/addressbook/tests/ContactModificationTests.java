package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.List;

public class ContactModificationTests extends TestBase {

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
  public void testContactModification() {
    List<ContactData> before = app.contact().list();
    int index = before.size()-1;
    ContactData contact = new ContactData()
            .withId(before.get(index).getId())
            .withFirstname("First").withMiddlename("Middle").withLastname("Last")
            .withNickname("Nickname").withTitle("Title").withCompany("Company").withAddress("Address")
            .withMobile("+79010000001").withEmail("test@gmail.com").withHomepage("homepage.com")
            .withBday("3").withBmonth("2").withBirthyear("1950");
    app.contact().modify(index, contact);
    app.goTo().homepage();
    List<ContactData> after = app.contact().list();
    Assert.assertEquals(after.size(), before.size());

    before.remove(index);
    before.add(contact);
    Comparator<? super ContactData> byId = (c1, c2) -> Integer.compare(c1.getId(), c2.getId());
    before.sort(byId);
    after.sort(byId);
    Assert.assertEquals(before,after);

  }


}
