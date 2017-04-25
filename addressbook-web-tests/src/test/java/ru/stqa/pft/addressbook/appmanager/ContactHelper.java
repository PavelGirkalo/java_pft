package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.util.List;

public class ContactHelper extends HelperBase {

  public ContactHelper(WebDriver wd) {
    super(wd);
  }

  public void submitContactCreation() {
    click(By.xpath("//div[@id='content']/form/input[@name='submit']"));
  }

  public void fillContactForm(ContactData contactData) {
    type(By.name("firstname"), contactData.getFirstname());
    type(By.name("middlename"), contactData.getMiddlename());
    type(By.name("lastname"), contactData.getLastname());
    attach(By.name("photo"), contactData.getPhoto());
    type(By.name("address"), contactData.getAddress());
    type(By.name("home"), contactData.getHome());
    type(By.name("mobile"), contactData.getMobile());
    type(By.name("work"), contactData.getWork());
    type(By.name("email"), contactData.getEmail());
    type(By.name("email2"), contactData.getEmail2());
    type(By.name("email3"), contactData.getEmail3());
  }

  public void initContactCreation() {
    click(By.linkText("add new"));
  }

  public void deleteSelectedContacts() {
    click(By.xpath("//div[@id='content']/form[@name='MainForm']/div[2]/input"));
    wd.switchTo().alert().accept();
  }

  public void addToGroup() {
    wd.findElement(By.cssSelector("input[name='add']")).click();
  }

  public void selectContact(int index) {
    wd.findElements(By.name("selected[]")).get(index).click();
  }

  public void selectContactById(int id) {
    wd.findElement(By.cssSelector("input[value='" + id + "']")).click();
  }

  public void initContactModification(int id) {
    wd.findElement(By.cssSelector("a[href='edit.php?id=" + id + "']")).click();
  }

  public void submitContactModification() {
    click(By.xpath("//div[@id='content']/form/input[@name='update']"));
  }

  public void create(ContactData contact) {
    initContactCreation();
    fillContactForm(contact);
    submitContactCreation();
  }

  public void modify(ContactData contact) {
    selectContactById(contact.getId());
    initContactModification(contact.getId());
    fillContactForm(contact);
    submitContactModification();
  }

  public void delete(int index) {
    selectContact(index);
    deleteSelectedContacts();
  }

  public void delete(ContactData contact) {
    selectContactById(contact.getId());
    deleteSelectedContacts();
  }

  public boolean isThereAContact() {
    return isElementPresent(By.name("selected[]"));
  }


  public Contacts all() {
    Contacts contacts = new Contacts();
    List<WebElement> elements = wd.findElements(By.xpath("//table[@id='maintable']/tbody/tr[@name='entry']"));
    for (WebElement element : elements) {
      int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
      String firstName = element.findElement(By.xpath("td[3]")).getText();
      String lastName = element.findElement(By.xpath("td[2]")).getText();
      String allPhones = element.findElement(By.xpath("td[6]")).getText();
      String address = element.findElement(By.xpath("td[4]")).getText();
      String allEmail = element.findElement(By.xpath("td[5]")).getText();

      ContactData contact = new ContactData()
              .withId(id)
              .withFirstname(firstName).withLastname(lastName)
              .withAllPhones(allPhones)
              .withAddress(address)
              .withAllEmail(allEmail);
      contacts.add(contact);
    }
    return contacts;
  }

  public ContactData infoFromEditForm(ContactData contact) {
    initContactModificationById(contact.getId());
    String firstname = wd.findElement(By.name("firstname")).getAttribute("value");
    String middlename = wd.findElement(By.name("middlename")).getAttribute("value");
    String lastname = wd.findElement(By.name("lastname")).getAttribute("value");
    String home = wd.findElement(By.name("home")).getAttribute("value");
    String mobile = wd.findElement(By.name("mobile")).getAttribute("value");
    String work = wd.findElement(By.name("work")).getAttribute("value");
    String address = wd.findElement(By.name("address")).getAttribute("value");
    String email = wd.findElement(By.name("email")).getAttribute("value");
    String email2 = wd.findElement(By.name("email2")).getAttribute("value");
    String email3 = wd.findElement(By.name("email3")).getAttribute("value");

    wd.navigate().back();
    return new ContactData().withId(contact.getId()).withFirstname(firstname).withMiddlename(middlename).withLastname(lastname)
            .withHome(home).withMobile(mobile).withWork(work)
            .withAddress(address)
            .withEmail(email).withEmail2(email2).withEmail3(email3);
  }

  private void initContactModificationById(int id) {
    wd.findElement(By.cssSelector(String.format("a[href='edit.php?id=%s']", id))).click();
  }

  public ContactData infoFromDetailForm(ContactData contact) {
    initContactDetailsById(contact.getId());
    String allDetails = wd.findElement(By.xpath("//div[@id='content']")).getText();

    wd.navigate().back();
    return new ContactData().withId(contact.getId())
            .withAllDetails(allDetails);

  }

  private void initContactDetailsById(int id) {
    wd.findElement(By.cssSelector(String.format("a[href='view.php?id=%s']", id))).click();
  }

  public void selectGroupForAdding(String group) {
    new Select(wd.findElement(By.name("to_group"))).selectByVisibleText(group);
  }

  public void selectCurrentGroup(String group) {
    new Select(wd.findElement(By.name("group"))).selectByVisibleText(group);
  }


  public void removeFromGroup() {
    wd.findElement(By.cssSelector("input[name=remove]")).click();
  }
}
