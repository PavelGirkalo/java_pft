package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

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
    Contacts before = app.contact().all();
    ContactData modifiedContact = before.iterator().next();
    ContactData contact = new ContactData()
            .withId(modifiedContact.getId())
            .withFirstname("First").withMiddlename("Middle").withLastname("Last")
            .withNickname("Nickname").withTitle("Title").withCompany("Company").withAddress("Address")
            .withMobile("+79010000001").withEmail("test@gmail.com").withHomepage("homepage.com")
            .withBday("3").withBmonth("2").withBirthyear("1950");
    app.contact().modify(contact);
    app.goTo().homepage();
    Contacts after = app.contact().all();

    assertEquals(after.size(), before.size());
    assertThat(after, equalTo(before.without(modifiedContact).withAdded(contact)));
  }

}
