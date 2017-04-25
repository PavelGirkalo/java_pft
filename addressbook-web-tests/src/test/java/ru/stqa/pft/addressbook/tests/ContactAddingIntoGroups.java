package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.io.File;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactAddingIntoGroups extends TestBase {

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
    ContactData selectedContact = app.db().contacts().iterator().next();
    Groups usedGroups = selectedContact.getGroups();
    Groups allGroups = app.db().groups();
    if (usedGroups.size() == allGroups.size()) {
      GroupData newGroup = new GroupData().withName("test" + (allGroups.size() + 1));
      app.goTo().GroupPage();
      app.group().create(newGroup);
      app.goTo().homepage();
      app.contact().selectGroupForAdding(newGroup.getName());
      app.contact().selectContactById(selectedContact.getId());
    } else if (usedGroups.size() == 0) {
      app.contact().selectGroupForAdding(allGroups.iterator().next().getName());
      app.contact().selectContactById(selectedContact.getId());
    } else {
      Groups unusedGroups = allGroups;
      GroupData currentGroup = unusedGroups.iterator().next();
      for (GroupData group : usedGroups) {
        if (currentGroup.getName().equals(group.getName())) {
          unusedGroups.remove(currentGroup);
          currentGroup = unusedGroups.iterator().next();
        }
      }
      app.contact().selectGroupForAdding(unusedGroups.iterator().next().getName());
      app.contact().selectContactById(selectedContact.getId());
    }

    app.contact().addToGroup();
    app.goTo().homepage();

    ContactData updatedContact = app.db().contactWithId(selectedContact.getId());
    Groups updatedGroups = updatedContact.getGroups();
    assertThat(updatedGroups.size(), equalTo(usedGroups.size() + 1));

  }

}
