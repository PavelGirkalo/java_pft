package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactOtherTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    app.goTo().homepage();
    if (app.contact().all().size() == 0) {
      app.contact().create(new ContactData()
                      .withFirstname("First").withLastname("Last")
                      .withHome("111-111").withMobile("+79010000001").withWork("666 666")
                      .withAddress("Address")
                      .withEmail("mail@gmail.com").withEmail2("mail2@gmail.com").withEmail3("mail3@gmail.com"));
      app.goTo().homepage();
    }
  }

  @Test
  public void testContactPhones() {
    app.goTo().homepage();
    ContactData contact = app.contact().all().iterator().next();
    ContactData contactInfoFromEdirForm = app.contact().infoFromEditForm(contact);
    assertThat(contact.getAllPhones(), equalTo(mergePhones(contactInfoFromEdirForm)));
  }

  @Test
  public void testContactAddress() {
    app.goTo().homepage();
    ContactData contact = app.contact().all().iterator().next();
    ContactData contactInfoFromEdirForm = app.contact().infoFromEditForm(contact);
    assertThat(contact.getAddress(), equalTo(contactInfoFromEdirForm.getAddress()));
  }

  @Test
  public void testContactEmail() {
    app.goTo().homepage();
    ContactData contact = app.contact().all().iterator().next();
    ContactData contactInfoFromEdirForm = app.contact().infoFromEditForm(contact);
    assertThat(contact.getAllEmail(), equalTo(mergeEmails(contactInfoFromEdirForm)));
  }

  private String mergeEmails(ContactData contact) {
    return Arrays.asList(contact.getEmail(), contact.getEmail2(), contact.getEmail3())
            .stream().filter((s) -> ! s.equals(""))
            .map(ContactOtherTests::cleaned)
            .collect(Collectors.joining("\n"));
  }

  private String mergePhones(ContactData contact) {
    return Arrays.asList(contact.getHome(), contact.getMobile(), contact.getWork())
            .stream().filter((s) -> ! s.equals(""))
            .map(ContactOtherTests::cleaned)
            .collect(Collectors.joining("\n"));
  }

  public static String cleaned(String phone) {
    return phone.replaceAll("\\s","").replaceAll("-()","");
  }

}
