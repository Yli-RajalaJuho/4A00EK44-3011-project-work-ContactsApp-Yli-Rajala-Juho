
import javax.swing.*;
import java.awt.*;
import java.util.*;


/**
* The class ContactsApp is used to run the final application.
*
* @author Juho Yli-Rajala
*/
public class ContactsApp {
    public static void main(String[] args) {

        JFrame display = new JFrame();
        //display.setLocationRelativeTo(null);
        display.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel flowpanel = new JPanel();
        flowpanel.setLayout(new FlowLayout());

        JButton addnewcontact = new JButton("Add a new contact");
        flowpanel.add(addnewcontact);

        JButton searchcontacts = new JButton("Seach for a contact");
        flowpanel.add(searchcontacts);

        JButton updatecontact = new JButton("Update an existing contact");
        flowpanel.add(updatecontact);

        JButton deletecontact = new JButton("Delete a contact");
        flowpanel.add(deletecontact);

        display.add(flowpanel);
        display.pack();
        display.setVisible(true);

    }
}
/**
* Class Person holds the mandatory values a person needs in order to be
* applied into the contacts database.
*
*/
class Person {
    private String userid;
    private String firstname;
    private String lastname;
    private String phonenumber;
    private String address;
    private String email;

    //person constructor
    public Person(String uid, String fname, String lname, String phonenum) {
        setId(uid);
        setFname(fname);
        setLname(lname);
        setPhone(phonenum);
    }


    //display the information based on id
    public void showPerson(String id) {
        JOptionPane.showMessageDialog(null, "Name: " + firstname + " " + lastname);
    }


    //set method for id
    public void setId(String id) {
        userid = id;
    }
    //get method for id
    public String getId() {
        return userid;
    }


    //set method for firstname
    public void setFname(String first) {
        firstname = first;
    }
    //get method for firstname
    public String getFname() {
        return firstname;
    }


    //set method for lastname
    public void setLname(String last) {
        lastname = last;
    }
    //get method for lastname
    public String geLFname() {
        return lastname;
    }


    //set method for phone number
    public void setPhone(String phonenum) {
        phonenumber = phonenum;
    }
    //get method for phone number
    public String getPhone() {
        return phonenumber;
    }


    //set method for address
    public void setAddress(String add) {
        address = add;
    }
    //get method for address
    public String getAddress() {
        return address;
    }


    //set method for email
    public void setEmail(String emailadd) {
        email = emailadd;
    }
    //get method for email
    public String getEmail() {
        return email;
    }
    
}
/**
* Class ContactsBook holds methods creating and modifying the existing database of contacts
*
*/
class ContactsBook {
    ArrayList contacts;

    //constructor for the contactsbook
    public void ContactsBook() {
        contacts = new ArrayList();
    }


    //adding a person into the contacts database
    public void addNewPerson(Person personinfo) {
    }


}