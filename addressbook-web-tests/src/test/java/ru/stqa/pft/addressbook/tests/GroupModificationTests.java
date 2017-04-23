package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

public class GroupModificationTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    if (app.db().groups().size() == 0) {
      app.goTo().GroupPage();
      app.group().create(new GroupData().withName("test1").withHeader("test2").withFooter("test3"));
    }
  }

  @Test
  public void testGroupModification(){
    app.goTo().GroupPage();
    Groups before = app.db().groups();
    GroupData modifiedGroup = before.iterator().next();
    GroupData group = new GroupData()
            .withId(modifiedGroup.getId()).withName("test1_mod").withHeader("test2_mod").withFooter("test3_mod");
    app.group().modify(group);
    Groups after = app.db().groups();
    assertEquals(after.size(), before.size());
    assertThat(after, equalTo(before.without(modifiedGroup).withAdded(group)));
  }

}
