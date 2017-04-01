package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.ArrayList;
import java.util.List;

public class ContactHelper extends HelperBase {

  public ContactHelper(WebDriver wd) {
    super(wd);
  }

  public void submitContactCreation() {
    click(By.xpath("//div[@id='content']/form/input[@name='submit']"));
  }

  public void fillContactForm(ContactData contactData, boolean creation) {
    type(By.name("firstname"), contactData.getFirstname());
    type(By.name("middlename"), contactData.getMiddlename());
    type(By.name("lastname"), contactData.getLastname());
    type(By.name("nickname"), contactData.getNickname());
    type(By.name("title"), contactData.getTitle());
    type(By.name("company"), contactData.getCompany());
    type(By.name("address"), contactData.getAddress());
    type(By.name("mobile"), contactData.getMobile());
    type(By.name("email"), contactData.getEmail());
    type(By.name("homepage"), contactData.getHomepage());
    if (!wd.findElement(By.xpath("//div[@id='content']/form/select[1]//option[" + contactData.getBday() + "]")).isSelected()) {
      wd.findElement(By.xpath("//div[@id='content']/form/select[1]//option[" + contactData.getBday() + "]")).click();
    }

    if (!wd.findElement(By.xpath("//div[@id='content']/form/select[2]//option[" + contactData.getBmonth() + "]")).isSelected()) {
      wd.findElement(By.xpath("//div[@id='content']/form/select[2]//option[" + contactData.getBmonth() + "]")).click();
    }
    type(By.name("byear"), contactData.getBirthyear());

    if (creation) {
      new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroup());
    } else {
      Assert.assertFalse(isElementPresent(By.name("new_group")));
    }

  }

  public void initContactCreation() {
    click(By.linkText("add new"));
  }

  public void deleteSelectedContacts() {
    click(By.xpath("//div[@id='content']/form[@name='MainForm']/div[2]/input"));
    wd.switchTo().alert().accept();
  }

  public void selectContact(int index) {
    wd.findElements(By.name("selected[]")).get(index).click();
  }

  public void initContactModification(int index) {
    // В коллекции нумерация элементов с 0, на странице нумерация элементов с 1 + один доп. tr на странице - это описание заголовков таблицы,
    // поэтому индекс кнопки для редактирования соотв. контакта = (index + 2)
    click(By.xpath("//table[@id='maintable']/tbody/tr[" + (index+2) + "]/td[8]/a/img"));
  }

  public void submitContactModification() {
    click(By.xpath("//div[@id='content']/form/input[@name='update']"));
  }

  public void createContact(ContactData contactData, boolean b) {
    initContactCreation();
    fillContactForm(new ContactData("First", "Middle", "Last", "Nickname", "Title", "Company", "Address", "+79010000001", "test@gmail.com", "homepage.com", "3", "2", "1950", "test1"), true);
    submitContactCreation();
  }

  public boolean isThereAContact() {
    return isElementPresent(By.name("selected[]"));
  }

  public List<ContactData> getContactList() {
    List<ContactData> contacts = new ArrayList<ContactData>();
    List<WebElement> elements = wd.findElements(By.xpath("//table[@id='maintable']/tbody/tr[@name='entry']"));
    for (WebElement element : elements) {
      String lastName = element.findElement(By.xpath("td[2]")).getText();
      String firstName = element.findElement(By.xpath("td[3]")).getText();
      int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
      ContactData contact = new ContactData(id, firstName, "Middle", lastName,"Nickname", "Title", "Company", "Address", "+79010000001", "test@gmail.com", "homepage.com", "3", "2", "1950", "test1");
      contacts.add(contact);
    }
    return contacts;
  }
}
