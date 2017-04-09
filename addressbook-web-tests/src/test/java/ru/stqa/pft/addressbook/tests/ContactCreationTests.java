package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase {


  @Test(enabled = true)
  public void testContactCreation() {
    app.goTo().homepage();
    Contacts before = app.contact().all();
    ContactData contact = new ContactData()
            .withFirstname("First").withMiddlename("Middle").withLastname("Last")
            .withNickname("Nickname").withTitle("Title").withCompany("Company").withAddress("Address")
            .withMobile("+79010000001").withEmail("test@gmail.com").withHomepage("homepage.com")
            .withBday("3").withBmonth("2").withBirthyear("1950").withGroup("test1");
    app.contact().create(contact, true);
    app.goTo().homepage();
    Contacts after = app.contact().all();

    assertThat(after.size(), equalTo(before.size() + 1));
    assertThat(after, equalTo(
            before.withAdded(contact.withId(after.stream().mapToInt((c) -> c.getId()).max().getAsInt()))));
  }

}
