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
    Contacts before = app.contact().all();
    ContactData deletedContact = before.iterator().next();
    app.contact().delete(deletedContact);
    app.goTo().homepage();
    Contacts after = app.contact().all();

    assertEquals(after.size(), before.size()-1);
    assertThat(after, equalTo(before.without(deletedContact)));
  }

}
