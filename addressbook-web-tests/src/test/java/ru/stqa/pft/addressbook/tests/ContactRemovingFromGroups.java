package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.io.File;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactRemovingFromGroups extends TestBase {

  @BeforeClass
  public void ensurePreconditions() {
    if (app.db().contacts().size() == 0) {
      File photo = new File("src/test/resources/st.jpg");
      app.goTo().homepage();
      app.contact().create(new ContactData()
              .withFirstname("First").withMiddlename("Middle").withLastname("Last")
              .withMobile("+79010000001")
              .withAddress("Address")
              .withEmail("mail@gmail.com")
              .withPhoto(photo));
    }

    if (app.db().groups().size()== 0) {
      app.goTo().GroupPage();
      app.group().create(new GroupData()
              .withName("test" + (app.db().groups().size() + 1)));
    }

  }

  @Test
  public void testAdditionContactIntoGroup() {
    app.goTo().homepage();
    app.contact().selectCurrentGroup("[all]");
    ContactData selectedContact = app.db().contacts().iterator().next();
    Groups usedGroups = selectedContact.getGroups();
    Groups allGroups = app.db().groups();
    if (usedGroups.size() == allGroups.size()) {
      app.contact().selectCurrentGroup(allGroups.iterator().next().getName());
      app.contact().selectContactById(selectedContact.getId());

    } else if (usedGroups.size() == 0) {
      String currentGroup = allGroups.iterator().next().getName();
      app.contact().selectGroupForAdding(currentGroup);
      app.contact().selectContactById(selectedContact.getId());
      app.contact().addToGroup();
      app.goTo().homepage();

      app.contact().selectCurrentGroup(currentGroup);
      app.contact().selectContactById(selectedContact.getId());

    } else {
      app.contact().selectCurrentGroup(usedGroups.iterator().next().getName());
      app.contact().selectContactById(selectedContact.getId());
    }

    ContactData before = app.db().contacts().iterator().next();
    Groups currentGroups = before.getGroups();
    app.contact().removeFromGroup();
    app.goTo().homepage();

    ContactData after = app.db().contactWithId(selectedContact.getId());
    Groups updatedGroups = after.getGroups();
    assertThat((updatedGroups.size() +1), equalTo(currentGroups.size()));

  }

}
