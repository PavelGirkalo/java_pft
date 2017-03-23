package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import ru.stqa.pft.addressbook.model.ContactData;

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

  }

  public void initContactCreation() {
    click(By.linkText("add new"));
  }

  public void deleteSelectedContacts() {
    click(By.xpath("//div[@id='content']/form[@name='MainForm']/div[2]/input"));
    wd.switchTo().alert().accept();
  }

  public void selectContact() {
    click(By.name("selected[]"));
  }

  public void initContactModification() {
    click(By.xpath("//table[@id='maintable']/tbody/tr[2]/td[8]/a/img"));
  }

  public void submitContactModification() {
    click(By.xpath("//div[@id='content']/form/input[@name='update']"));
  }
}
