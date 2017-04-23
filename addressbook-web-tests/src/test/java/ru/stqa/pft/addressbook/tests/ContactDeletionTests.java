package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

public class ContactDeletionTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    app.goTo().homepage();
    if (app.db().contacts().size() == 0) {
      app.contact().create(new ContactData()
                      .withFirstname("First").withLastname("Last")
                      .withHome("111-111").withMobile("+79010000001").withWork("666 666")
                      .withAddress("Address")
                      .withEmail("mail@gmail.com").withEmail2("mail2@gmail.com").withEmail3("mail3@gmail.com"));
      app.goTo().homepage();
    }
  }

  @Test
  public void ContactDeletionTests() {
    Contacts before = app.db().contacts();
    ContactData deletedContact = before.iterator().next();
    app.contact().delete(deletedContact);
    app.goTo().homepage();
    Contacts after = app.db().contacts();

    assertEquals(after.size(), before.size()-1);
    assertThat(after, equalTo(before.without(deletedContact)));
  }

}
