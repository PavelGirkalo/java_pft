package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.io.File;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase {


  @Test(enabled = true)
  public void testContactCreation() {
    app.goTo().homepage();
    Contacts before = app.contact().all();
    File photo = new File("src/test/resources/st.jpg");
    ContactData contact = new ContactData()
            .withFirstname("First").withLastname("Last").withPhoto(photo)
            .withHome("111-111").withMobile("+79010000001").withWork("666 666")
            .withAddress("Address")
            .withEmail("email@gmail.com").withEmail2("email2@gmail.com").withEmail3("email3@gmail.com");
    app.contact().create(contact);
    app.goTo().homepage();
    Contacts after = app.contact().all();

    assertThat(after.size(), equalTo(before.size() + 1));
    assertThat(after, equalTo(
            before.withAdded(contact.withId(after.stream().mapToInt((c) -> c.getId()).max().getAsInt()))));
  }

  @Test(enabled = false)
  public void testCurrentDir() {
    File curretnDir = new File(".");
    System.out.println(curretnDir.getAbsolutePath());
    File photo = new File("src/test/resources/st.jpg");
    System.out.println(photo.getAbsolutePath());
    System.out.println(photo.exists());
  }





}
