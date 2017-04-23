package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.io.File;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

public class ContactModificationTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    app.goTo().homepage();
    if (app.db().contacts().size() == 0) {
      File photo = new File("src/test/resources/st.jpg");
      app.contact().create(new ContactData()
                      .withFirstname("First").withMiddlename("Middle").withLastname("Last")
                      .withAddress("Address")
                      .withMobile("+79010000001").withEmail("mail@gmail.com")
                      .withPhoto(photo));
      app.goTo().homepage();
    }
  }

  @Test
  public void testContactModification() {
    File photo = new File("src/test/resources/st.jpg");
    Contacts before = app.db().contacts();
    ContactData modifiedContact = before.iterator().next();
    ContactData contact = new ContactData()
            .withId(modifiedContact.getId())
            .withFirstname("First_mod").withMiddlename("Middle_mod").withLastname("Last_mod")
            .withAddress("Address")
            .withHome("111-111").withMobile("+79010000001").withWork("666 666")
            .withMobile("+79010000001").withEmail("mail_mod@gmail.com")
            .withPhoto(photo);
    app.contact().modify(contact);
    app.goTo().homepage();
    Contacts after = app.db().contacts();

    assertEquals(after.size(), before.size());
    assertThat(after, equalTo(before.without(modifiedContact).withAdded(contact)));
  }

}
