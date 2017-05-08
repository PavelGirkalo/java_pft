package ru.stqa.pft.rest.tests;

import com.jayway.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.stqa.pft.rest.model.Issue;

import java.io.IOException;
import java.util.Set;

import static org.testng.Assert.assertEquals;

public class RestAssuredTests extends TestBase {

  @BeforeClass
  public void init() {
    RestAssured.authentication = RestAssured.basic("LSGjeU4yP1X493ud1hNniA==", "");
  }

  @Test
  public void testCreateIssue() throws IOException {
    skipIfNotFixed(6);
    Set<Issue> oldIssues = getIssuesWithAssured();
    Issue newIssue = new Issue().withSubject("Test issue").withDescription("Test description");
    int issueId = createIssueWithAssured(newIssue);
    Set<Issue> newIssues = getIssuesWithAssured();
    oldIssues.add(newIssue.withId(issueId));
    assertEquals(newIssues, oldIssues);
  }

}
