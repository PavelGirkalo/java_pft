package ru.stqa.pft.mantis.appmanager;

import biz.futureware.mantis.rpc.soap.client.*;
import ru.stqa.pft.mantis.model.Issue;
import ru.stqa.pft.mantis.model.Project;

import javax.xml.rpc.ServiceException;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class SoapHelper {

  private ApplicationManager app;

  public SoapHelper (ApplicationManager app) {
    this.app = app;
  }

  public Set<Project> getProjects() throws MalformedURLException, ServiceException, RemoteException {
    MantisConnectPortType mc = getMantisConnect();
    ProjectData[] projects = mc.mc_projects_get_user_accessible(app.getProperty("soap.login"), app.getProperty("soap.password"));
    return Arrays.asList(projects).stream().map((p) -> new Project().withId(p.getId().intValue()).withName(p.getName())).collect(Collectors.toSet());
  }

  private MantisConnectPortType getMantisConnect() throws ServiceException, MalformedURLException {
    return new MantisConnectLocator()
              .getMantisConnectPort(new URL(app.getProperty("soap.host")));
  }

  public Issue addIssue(Issue issue) throws MalformedURLException, ServiceException, RemoteException {
    MantisConnectPortType mc = getMantisConnect();
    String[] categories = mc.mc_project_get_categories(app.getProperty("soap.login"), app.getProperty("soap.password"), BigInteger.valueOf(issue.getProject().getId()));
    IssueData issueData = new IssueData();
    issueData.setSummary(issue.getSummary());
    issueData.setDescription(issue.getDescription());
    issueData.setProject(new ObjectRef(BigInteger.valueOf(issue.getProject().getId()), issue.getProject().getName()));
    issueData.setCategory(categories[0]);

    BigInteger issueId = mc.mc_issue_add(app.getProperty("soap.login"), app.getProperty("soap.password"), issueData);
    IssueData createdIssueData = mc.mc_issue_get(app.getProperty("soap.login"), app.getProperty("soap.password"), issueId);
    return new Issue().withId(createdIssueData.getId().intValue())
            .withSummary(createdIssueData.getSummary()).withDescription(createdIssueData.getDescription())
            .withProject( new Project().withId(createdIssueData.getProject().getId().intValue())
                                       .withName(createdIssueData.getProject().getName()));
  }

  public Issue getIssueWithId(BigInteger issueId) throws MalformedURLException, ServiceException, RemoteException {
    MantisConnectPortType mc = getMantisConnect();
    IssueData issueData = mc.mc_issue_get(app.getProperty("soap.login"), app.getProperty("soap.password"), issueId);
    return Arrays.asList(issueData).stream().map((i) -> new Issue().withId(i.getId().intValue())
            .withSummary(i.getSummary()).withDescription(i.getDescription())
            .withProject( new Project().withId(i.getProject().getId().intValue()).withName(i.getProject().getName()))
            .withStatus(i.getStatus().getName())).collect(Collectors.toSet()).iterator().next();
  }

  public Set<Issue> getIssuesOfProject(int project_id) throws MalformedURLException, ServiceException, RemoteException {
    MantisConnectPortType mc = getMantisConnect();
    IssueData[] issues = mc.mc_project_get_issues(app.getProperty("soap.login"), app.getProperty("soap.password"), BigInteger.valueOf(project_id), BigInteger.valueOf(1), BigInteger.valueOf(-1));
    return Arrays.asList(issues).stream().map((i) -> new Issue().withId(i.getId().intValue())
            .withSummary(i.getSummary()).withDescription(i.getDescription())
            .withProject( new Project().withId(i.getProject().getId().intValue()).withName(i.getProject().getName()))
            .withStatus(i.getStatus().getName())).collect(Collectors.toSet());
  }
}
