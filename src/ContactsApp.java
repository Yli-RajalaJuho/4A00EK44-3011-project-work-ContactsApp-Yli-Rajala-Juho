
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

//oma verify
import src.util.Verify;

/**
* The class ContactsApp is used to run the final application.
*
* @author Juho Yli-Rajala
*/
public class ContactsApp {
    public static void main(String[] args) {

        ArrayList<Person> contacts = new ArrayList<Person>();

        JFrame display = new JFrame();
        display.setLocationRelativeTo(null);
        display.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel flowpanel = new JPanel();
        flowpanel.setLayout(new FlowLayout());


        //add
        JButton addnewcontact = new JButton("Add a new contact");
        addnewcontact.addActionListener((e) -> contacts.add(addNewPerson()));
        flowpanel.add(addnewcontact);


        //search
        JButton searchcontacts = new JButton("Seach for a contact");
        searchcontacts.addActionListener((e) -> searchPerson(contacts));
        flowpanel.add(searchcontacts);


        //update
        JButton updatecontact = new JButton("Update an existing contact");
        updatecontact.addActionListener((e) -> updateContacts(contacts));
        flowpanel.add(updatecontact);


        //delete
        JButton deletecontact = new JButton("Delete a contact");
        deletecontact.addActionListener((e) -> deletePerson(contacts));
        flowpanel.add(deletecontact);

        display.add(flowpanel);
        display.pack();
        display.setVisible(true);

    }

    //adding a person into the contacts list
    public static Person addNewPerson() {

        // asking the info
        String id = JOptionPane.showInputDialog("Enter social security number\nin proper Finlands id format!\nDD/MM/YY/C/ZZZ/Q :");
        while (Verify.verifyId(id) != true) {
            id = JOptionPane.showInputDialog("Please give a proper Finlands id format!!!\nDD/MM/YY/C/ZZZ/Q :");
        }
        String fname = ""; 
        while (Verify.verifyFname(fname) != true) {
            fname = JOptionPane.showInputDialog("Enter the firstname :");
        }
        String lname = ""; 
        while (Verify.verifyLname(lname) != true) {
            lname = JOptionPane.showInputDialog("Enter the lastname :");
        }
        String phoneno = "";
        while (Verify.verifyPhoneNo(phoneno) != true) {
            phoneno = JOptionPane.showInputDialog("Enter phone number :");
        }
        String address = "";
        address = JOptionPane.showInputDialog("Enter address (optional) :");
        if ((address == null) && Verify.verifyAddress(address) != true) {
            while (Verify.verifyAddress(address) != true) {
                address = "";
                address = JOptionPane.showInputDialog("Enter valid address (optional) :");
            }
        }
        String email = "";
        email = JOptionPane.showInputDialog("Enter email address (optional):");
        if ((email == null) && Verify.verifyAddress(address) != true) {
            while (Verify.verifyEmail(email) != true) {
                email = "";
                email = JOptionPane.showInputDialog("Enter a valid email address (optional):");
            }
        }

        // add the person into the arraylist
        Person newcontact = new Person(id, fname, lname, phoneno, address, email);
        System.out.println("UUSI KONTAKTI LUOTU");
        return newcontact;
    }
    //searching from the contacts list
    public static void searchPerson(ArrayList searchfrom) {
        //boolean to verify if something was found
        boolean found = false;

        // asking who to look
        String input = JOptionPane.showInputDialog("Enter one of the following:\n id\n firstname\n lastname\n phoneNo.\n address\n email\n for the contact you are searching for:");

        //searching the arraylist for matching atributes and if found returning them
        for (int i=0; i<searchfrom.size(); i++) {
            Person y = (Person) searchfrom.get(i);
            if ((input.equals(y.getId())) ||
            input.equals(y.getFname()) ||
            input.equals(y.getLname()) ||
            input.equals(y.getPhone()) ||
            input.equals(y.getAddress()) ||
            input.equals(y.getEmail())) {
                y.showPerson();
                found = true;
            }
        }

        // if nothing is found show message
        if (found != true) {
            JOptionPane.showMessageDialog(null, "No matching search results found for:\n" + input);
        }
    }
    //updating info in the contacts list
    public static void updateContacts(ArrayList updatecontact) {
        //boolean to verify if something was found
        boolean found = false;

        // asking who to look for based on any of the users info
        String input = JOptionPane.showInputDialog("Enter one of the following:\n id\n firstname\n lastname\n phoneNo.\n address\n email\n for the contact you are searching for :");

        //searching the given name
        for (int i=0; i<updatecontact.size(); i++) {
            Person y = (Person) updatecontact.get(i);
            if ((input.equals(y.getId())) ||
            input.equals(y.getFname()) ||
            input.equals(y.getLname()) ||
            input.equals(y.getPhone()) ||
            input.equals(y.getAddress()) ||
            input.equals(y.getEmail())) {
                //verifying if user wants to update this person
                if (y.validateUpd() == true) {
                    Person updated = y.updateInfo();
                    updatecontact.set(i, updated);
                }
                found = true;
            }
        }
        
        // if nothing is found show message
        if (found != true) {
            JOptionPane.showMessageDialog(null, "No matching search results found for :\n" + input);
        }
    }
    //deleting from the contacts list
    public static void deletePerson(ArrayList deletefrom) {
        //boolean to verify if something was found
        boolean found = false;

        // asking who to look for based on any of the users info
        String input = JOptionPane.showInputDialog("Enter one of the following:\n id\n firstname\n lastname\n phoneNo.\n address\n email\n for the contact you are searching for:");

        //searching the given name
        for (int i=0; i<deletefrom.size(); i++) {
            Person y = (Person) deletefrom.get(i);
            if ((input.equals(y.getId())) ||
            input.equals(y.getFname()) ||
            input.equals(y.getLname()) ||
            input.equals(y.getPhone()) ||
            input.equals(y.getAddress()) ||
            input.equals(y.getEmail())) {
                //verifying if user wants to delete this person
                if (y.validateDel() == true) {
                    deletefrom.remove(y);
                }
                found = true;
            }
        }
        
        // if nothing is found show message
        if (found != true) {
            JOptionPane.showMessageDialog(null, "No matching search results found for:\n" + input);
        }
    }
}
/**
* Class Person holds methods constructing a new Person object
* as well as methods for displaying the desired information
* according what the end user wants to do.
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
    public Person(String uid, String fname, String lname, String phonenum, String add, String emai) {
        setId(uid);
        setFname(fname);
        setLname(lname);
        setPhone(phonenum);
        setAddress(add);
        setEmail(emai);
    }

    //display all the information of a person
    public void showPerson() {
        JOptionPane.showMessageDialog(null, "Found:" + 
        "\nName: " + this.firstname + " " + this.lastname +
        "\nIdentification number: " + this.userid +
        "\nPhone number: " + this.phonenumber +
        "\nAddress: " + this.address +
        "\nEmail: " + this.email);
    }

    //if the user wants to delete this specific person
    public boolean validateDel() {
        boolean delete = false;

        String input = "";
        while (Verify.verifyYesNo(input) != true) {
            input = JOptionPane.showInputDialog("Do you wish to delete:" + 
            "\nName: " + this.firstname + " " + this.lastname +
            "\nIdentification number: " + this.userid +
            "\nPhone number: " + this.phonenumber +
            "\nAddress: " + this.address +
            "\nEmail: " + this.email + "\nfrom contacts?\n\nType: [1] for YES and [2] for NO");
        }
        if (input.equals("1")) {
            delete = true;
        } else if (input.equals("2")) {
            delete = false;
        }

        return delete;
    }

    //if the user wants to update this specific person
    public boolean validateUpd() {
        boolean update = false;

        String input = "";
        while (Verify.verifyYesNo(input) != true) {
            input = JOptionPane.showInputDialog("Do you wish to update this person :" + 
            "\nName: " + this.firstname + " " + this.lastname +
            "\nIdentification number: " + this.userid +
            "\nPhone number: " + this.phonenumber +
            "\nAddress: " + this.address +
            "\nEmail: " + this.email + "\n\nType: [1] for YES and [2] for NO");
        }
        if (input.equals("1")) {
            update = true;
        } else if (input.equals("2")) {
            update = false;
        }

        return update;
    }

    // update the info this person has
    public Person updateInfo() {
        // asking the info
        String id = JOptionPane.showInputDialog("Enter the new social security number\nin proper Finlands id format!\nDD/MM/YY/C/ZZZ/Q :\n\nIf you wish to make no changes please press [ENTER]");
        if (id.equals("")) {
            id = this.getId();
        }
        while (Verify.verifyId(id) != true) {
            id = JOptionPane.showInputDialog("Please give a proper Finlands id format!!!\nDD/MM/YY/C/ZZZ/Q :\n\nIf you wish to make no changes please press [ENTER]");
            if (id.equals("")) {
                id = this.getId();
            }
        }
        String fname = ""; 
        while (Verify.verifyFname(fname) != true) {
            fname = JOptionPane.showInputDialog("Enter the firstname :");
            if (fname.equals("")) {
                fname = this.getFname();
            }
        }
        String lname = ""; 
        while (Verify.verifyLname(lname) != true) {
            lname = JOptionPane.showInputDialog("Enter the lastname :");
            if (lname.equals("")) {
                lname = this.getLname();
            }
        }
        String phoneno = "";
        while (Verify.verifyPhoneNo(phoneno) != true) {
            phoneno = JOptionPane.showInputDialog("Enter phone number :");
            if (phoneno.equals("")) {
                phoneno = this.getPhone();
            }
        }
        String address = "";
        address = JOptionPane.showInputDialog("Enter address (optional) :");
        if ((address == null) && Verify.verifyAddress(address) != true) {
            while (Verify.verifyAddress(address) != true) {
                address = "";
                address = JOptionPane.showInputDialog("Enter a valid address (optional) :");
            }
        }
        String email = "";
        email = JOptionPane.showInputDialog("Enter email address (optional):");
        if ((email == null) && Verify.verifyAddress(address) != true) {
            while (Verify.verifyEmail(email) != true) {
                email = "";
                email = JOptionPane.showInputDialog("Enter a valid email address (optional):");
            }
        }

        // add the person into the arraylist
        Person updated = new Person(id, fname, lname, phoneno, address, email);
        System.out.println("KONTAKTIA MUOKATTU");
        return updated;
    }

    //set method for id
    public void setId(String id) {
        userid = id;
    }
    //get method for id
    public String getId() {
        return userid;
    }

    //set method for firstname first letter to uppercase
    public void setFname(String first) {
        String formalf = first.substring(0, 1).toUpperCase() + first.substring(1).toLowerCase();
        firstname = formalf;
    }
    //get method for firstname
    public String getFname() {
        return firstname;
    }

    //set method for lastname first letter to uppercase
    public void setLname(String last) {
        String formall = last.substring(0, 1).toUpperCase() + last.substring(1).toLowerCase();
        lastname = formall;
    }
    //get method for lastname
    public String getLname() {
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