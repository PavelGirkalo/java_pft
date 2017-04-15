package ru.stqa.pft.addressbook.generators;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import ru.stqa.pft.addressbook.model.ContactData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class ContactDataGenetator {

  @Parameter(names = "-cc", description = "Contact count")
  public int count;

  @Parameter(names = "-cf", description = "Target contact file")
  public String file;

  public static void main(String[] args) throws IOException {
    ContactDataGenetator genetator = new ContactDataGenetator();
    JCommander jCommander = new JCommander(genetator);
    try {
      jCommander.parse(args);
    } catch (ParameterException ex) {
      jCommander.usage();
      return;
    }
    genetator.run();

  }

  private void run() throws IOException {
    List<ContactData> contacts = generateContacts(count);
    save(contacts, new File(file));

  }

  private void save(List<ContactData> contacts, File file) throws IOException {
    System.out.println(new File(".").getAbsolutePath());
    Writer writer = new FileWriter(file);
    for (ContactData contact : contacts) {
      writer.write(String.format("%s;%s;%s;%s;%s\n", contact.getFirstname(), contact.getLastname(), contact.getAddress(), contact.getEmail(), contact.getMobile()));
    }
    writer.close();

  }

  private List<ContactData> generateContacts(int count) {
    List<ContactData> contacts = new ArrayList<ContactData>();
    for (int i = 0; i <count; i++) {
      contacts.add(new ContactData().withFirstname((String.format("First %s", i)))
              .withLastname(String.format("Last %s", i)).withAddress(String.format("Address %s", i))
              .withEmail(String.format("Email%s@test.com", i)).withMobile(String.format("+7901000%s", i)));
    }
    return contacts;
  }
}
