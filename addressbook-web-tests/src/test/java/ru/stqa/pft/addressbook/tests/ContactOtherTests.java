package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.io.File;
import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactOtherTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    app.goTo().homepage();
    if (app.contact().all().size() == 0) {
      File photo = new File("src/test/resources/st.jpg");
      app.contact().create(new ContactData()
              .withFirstname("First").withLastname("Middle").withLastname("Last")
              .withMobile("+79010000001")
              .withAddress("Address")
              .withEmail("mail@gmail.com")
              .withPhoto(photo));
      app.goTo().homepage();
    }
  }

  @Test
  public void testContactPhones() {
    app.goTo().homepage();
    ContactData contact = app.contact().all().iterator().next();
    ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact);
    assertThat(contact.getAllPhones(), equalTo(mergePhones(contactInfoFromEditForm)));
  }

  @Test
  public void testContactAddress() {
    app.goTo().homepage();
    ContactData contact = app.contact().all().iterator().next();
    ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact);
    assertThat(contact.getAddress(), equalTo(contactInfoFromEditForm.getAddress()));
  }

  @Test
  public void testContactEmail() {
    app.goTo().homepage();
    ContactData contact = app.contact().all().iterator().next();
    ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact);
    assertThat(contact.getAllEmail(), equalTo(mergeEmails(contactInfoFromEditForm)));
  }

  @Test(enabled = false)
  public void testContactDetailsForm() {
    app.goTo().homepage();
    ContactData contact = app.db().contacts().iterator().next();
    ContactData editForm = app.contact().infoFromEditForm(contact);
    String details = app.contact().infoFromDetailForm(contact).getAllDetails();
    assertThat(cleaned(details), equalTo(mergeAllDetails(editForm)));
  }


  private String mergeAllDetails(ContactData contact) {
     String a = Arrays.asList(contact.getFirstname(), contact.getMiddlename(), contact.getLastname(), contact.getAddress(),
            contact.getHome(), contact.getMobile(), contact.getWork(),
            contact.getEmail(), contact.getEmail2(), contact.getEmail3())
            .stream()
            .filter((s) -> ! s.equals(""))
            .map(ContactOtherTests::cleaned)
            .collect(Collectors.joining(""));
    return a;
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

  public static String cleaned(String string) {
    return string.replaceAll("M:", "").replaceAll("H:", "").replaceAll("W:", "").replaceAll("\\s","").replaceAll("-()","");
  }

}
