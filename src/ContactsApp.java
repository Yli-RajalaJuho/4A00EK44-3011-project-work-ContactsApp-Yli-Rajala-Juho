
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

/**
* The class ContactsApp holds the main method that is used to run the final application.
* This application uses a GUI and is run by asking the end user for inputs.
* The application uses a list of contacts that it stores in a separate text file named "Contactsbook.txt" and it refers to it when loading and saving information.
* 
* @author Juho Yli-Rajala
*/
public class ContactsApp {
    public static void main(String[] args) {
        
        //the contactsbook
        ArrayList<Person> contacts = loadContacts();


        JFrame display = new JFrame();
        display.setLocationRelativeTo(null);
        display.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        JPanel flowpanel = new JPanel();
        flowpanel.setLayout(new FlowLayout());


        //add
        JButton addnewcontact = new JButton("Add a new contact");
        addnewcontact.addActionListener((e) -> addNewPerson(contacts));
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


        //clear
        JButton clearcontacts = new JButton("Clear contactsbook");
        clearcontacts.addActionListener((e) -> clearContacts(contacts));
        flowpanel.add(clearcontacts);


        display.add(flowpanel);
        display.pack();
        display.setVisible(true);

    }

    /**
    * This method is used to search for a certain Person object from the contacts ArrayList.
    *
    * @param contactsbook
    * @param id
    * @param fname
    * @param lname
    * @param phoneno
    * @param address
    * @param email
    * @param newcontact
    */
    public static void addNewPerson(ArrayList contactsbook) {

        // asking the info
        String id = JOptionPane.showInputDialog("Enter social security number\nin proper Finlands id format!\nDD/MM/YY/C/ZZZ/Q :");
        while (Verify.verifyId(id) != true) {
            id = JOptionPane.showInputDialog("Please give a proper Finlands id format!!!\nDD/MM/YY/C/ZZZ/Q :");
        }
        String fname = JOptionPane.showInputDialog("Enter firstname :"); 
        while (Verify.verifyFname(fname) != true) {
            fname = JOptionPane.showInputDialog("Please give a proper firstname :");
        }
        String lname = JOptionPane.showInputDialog("Enter lastname :");
        while (Verify.verifyLname(lname) != true) {
            lname = JOptionPane.showInputDialog("Please give a proper lastname :");
        }
        String phoneno = JOptionPane.showInputDialog("Enter a phone number :");
        while (Verify.verifyPhoneNo(phoneno) != true) {
            phoneno = JOptionPane.showInputDialog("Please give a proper phone number :");
        }
        String address = JOptionPane.showInputDialog("Enter address (optional) :");
        if (address.equals("")) {
            address = " ";
        }
        while (Verify.verifyAddress(address) != true) {
            address = JOptionPane.showInputDialog("Enter a valid address (optional) :");
            if (address.equals("")) {
                address = " ";
            }
        }
        String email = JOptionPane.showInputDialog("Enter email address (optional):");
        if (email.equals("")) {
            email = " ";
        }
        while (Verify.verifyEmail(email) != true) {
            email = JOptionPane.showInputDialog("Enter a valid email address (optional):");
            if (email.equals("")) {
                email = " ";
            }
        }

        // add the person into the arraylist
        Person newcontact = new Person(id, fname, lname, phoneno, address, email);
        contactsbook.add(newcontact);
        System.out.println("UUSI KONTAKTI LUOTU");

        saveContacts(contactsbook);
    }

    /**
    * This method is used to search for a certain Person object from the contacts ArrayList.
    *
    * @param contactsbook
    * @param found
    * @param input
    * @param y
    */
    public static void searchPerson(ArrayList contactsbook) {
        //boolean to verify if something was found
        boolean found = false;

        // asking who to look
        String input = JOptionPane.showInputDialog("Enter one of the following:\n id\n firstname\n lastname\n phoneNo.\n address\n email\n for the contact you are searching for:");

        //searching the arraylist for matching atributes and if found returning them
        for (int i=0; i<contactsbook.size(); i++) {
            Person y = (Person) contactsbook.get(i);
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

    /**
    * This method is used to update a certain Person object in the contacts ArrayList.
    *
    * @param contactsbook
    * @param found
    * @param input
    * @param y
    */
    public static void updateContacts(ArrayList contactsbook) {
        //boolean to verify if something was found
        boolean found = false;

        // asking who to look for based on any of the users info
        String input = JOptionPane.showInputDialog("Enter one of the following:\n id\n firstname\n lastname\n phoneNo.\n address\n email\n for the contact you are searching for :");

        //searching the given name
        for (int i=0; i<contactsbook.size(); i++) {
            Person y = (Person) contactsbook.get(i);
            if ((input.equals(y.getId())) ||
            input.equals(y.getFname()) ||
            input.equals(y.getLname()) ||
            input.equals(y.getPhone()) ||
            input.equals(y.getAddress()) ||
            input.equals(y.getEmail())) {
                //verifying if user wants to update this person
                if (y.validateUpd() == true) {
                    Person updated = y.updatePerson();
                    contactsbook.set(i, updated);
                }
                found = true;
            }
        }
        
        // if nothing is found show message
        if (found != true) {
            JOptionPane.showMessageDialog(null, "No matching search results found for :\n" + input);
        }

        saveContacts(contactsbook);
    }

    /**
    * This method is used to delete a certain Person object from the contacts ArrayList.
    *
    * @param contactsbook
    * @param found
    * @param input
    * @param y
    */
    public static void deletePerson(ArrayList contactsbook) {
        //boolean to verify if something was found
        boolean found = false;

        // asking who to look for based on any of the users info
        String input = JOptionPane.showInputDialog("Enter one of the following:\n id\n firstname\n lastname\n phoneNo.\n address\n email\n for the contact you are searching for:");

        //searching the given name
        for (int i=0; i<contactsbook.size(); i++) {
            Person y = (Person) contactsbook.get(i);
            if ((input.equals(y.getId())) ||
            input.equals(y.getFname()) ||
            input.equals(y.getLname()) ||
            input.equals(y.getPhone()) ||
            input.equals(y.getAddress()) ||
            input.equals(y.getEmail())) {
                //verifying if user wants to delete this person
                if (y.validateDel() == true) {
                    contactsbook.remove(y);
                }
                found = true;
            }
        }
        
        // if nothing is found show message
        if (found != true) {
            JOptionPane.showMessageDialog(null, "No matching search results found for:\n" + input);
        }
        saveContacts(contactsbook);
    }

    /**
    * This method is used to save the whole ArrayList into the text file "Contactsbook.txt".
    *
    * @param contactsbook
    * @param contactslist
    * @param personinfo
    * @param currentperson
    */
    public static void saveContacts(ArrayList contactsbook) {
        try {
            Person personinfo;
            String currentperson;
            FileWriter infoholder = new FileWriter("Contactsbook.txt");
            PrintWriter contactsholder = new PrintWriter(infoholder);

            for (int i=0; i<contactsbook.size(); i++) {
                personinfo = (Person) contactsbook.get(i);
                currentperson = personinfo.getId() + "," + personinfo.getFname() + "," + personinfo.getLname() + "," + personinfo.getPhone() + "," + personinfo.getAddress() + "," + personinfo.getEmail();
                contactsholder.println(currentperson);
            }
            contactsholder.flush();
            contactsholder.close();
            infoholder.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
    * This method is used to load the whole separate text file "Contactsbook.txt" into an ArrayList.
    *
    * @param contactslist
    * @param tokens
    * @param id
    * @param fname
    * @param lname
    * @param phone
    * @param add
    * @param email
    * @param personinfo
    * @param y
    * @return Returns the old information stored back into an ArrayList @param contactslist.
    */
    public static ArrayList<Person> loadContacts() {
        ArrayList<Person> contactslist = new ArrayList<Person>();
        String tokens[] = null;
        String id, fname, lname, phone, add, email;
        try {
            FileReader bookreader = new FileReader("Contactsbook.txt");
            BufferedReader inforeader = new BufferedReader(bookreader);
            String personinfo = inforeader.readLine();
            while (personinfo != null) {
                tokens = personinfo.split(",");
                id = tokens[0];
                fname = tokens[1];
                lname = tokens[2];
                phone = tokens[3];
                add = tokens[4];
                email = tokens[5];
                Person y = new Person(id, fname, lname, phone, add, email);
                contactslist.add(y);
                personinfo = inforeader.readLine();
            }
            inforeader.close();
            bookreader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return contactslist;
    }

    /**
    * This method is used to clear the whole separate text file "Contactsbook.txt".
    *
    * @param yesno This is simply a String that holds a value "1" or "2" depending on if the end user wants to clear the whole contactsbook.
    * @param p This is the Person object that is currently being removed from the contactsbook.
    */
    public static void clearContacts(ArrayList contactsbook) {
        String yesno = "";
        while (Verify.verifyYesNo(yesno) != true) {
            yesno = JOptionPane.showInputDialog("Do you wish to clear the whole contactsbook?\n\nType: [1] for YES and [2] for NO");
        }
        if (yesno.equals("1")) {
            for (int i=0; i<contactsbook.size(); i++) {
                Person p = (Person) contactsbook.get(i);
                contactsbook.remove(p);
            }
            JOptionPane.showMessageDialog(null, "Contactsbook cleared");
        } else {
            JOptionPane.showMessageDialog(null, "No changes made to the contactsbook");
        }

        saveContacts(contactsbook);
    }
}

/**
* Class Person has private Strings that hold the information a Person object has. It also holds methods constructing a new Person object
* as well as methods that aquire certain aspects of the specific Person object
* depending on what the end user wants to do.
* 
* @param userid Is a private String that holds the Person objects personal identification number.
* @param firstname Is a private String that holds the Person objects firstname.
* @param lastname Is a private String that holds the Person objects lastname.
* @param phonenumber Is a private String that holds the Person objects phonenumber.
* @param address Is a private String that holds the Person objects address.
* @param email Is a private String that holds the Person objects email address.
* @author Juho Yli-Rajala
*/
class Person {

    //Strings that hold the information a Person has.
    private String userid;
    private String firstname;
    private String lastname;
    private String phonenumber;
    private String address;
    private String email;

    /**
    * This method is the constructor for a new Person object.
    * A new Person object must have an id, firstname, lastname, phonenumber, address and an email.
    * The Person is constructed using the set methods.
    * 
    * @param uid User id is a mandatory String that the Person must have and it is validated using class Verify.
    * The String is then placed as the Person objects information using the Persons setId() method.
    * @param fname Users firstname is a mandatory String that the Person must have and it is validated using class Verify.
    * The String is then placed as the Person objects information using the Persons setFname() method.
    * @param lname Users lastname is a mandatory String that the Person must have and it is validated using class Verify.
    * The String is then placed as the Person objects information using the Persons setLname() method.
    * @param phonenum Users phonenumber is a mandatory String that the Person must have and it is validated using class Verify.
    * The String is then placed as the Person objects information using the Persons setPhone() method.
    * @param add Users address is a mandatory String that the Person must have but can be set as " " and it is validated using class Verify.
    * The String is then placed as the Person objects information using the Persons setAddress() method.
    * @param emai Users email address is a mandatory String that the Person must have but can be set as " " and it is validated using class Verify.
    * The String is then placed as the Person objects information using the Persons setEmail() method.
    */
    public Person(String uid, String fname, String lname, String phonenum, String add, String emai) {
        setId(uid);
        setFname(fname);
        setLname(lname);
        setPhone(phonenum);
        setAddress(add);
        setEmail(emai);
    }

    /**
    * This method is used to show a Person objects information through a JOptionPanel MessageDialog.
    *
    * @param userid Is a private String that holds the Person objects personal identification number.
    * @param firstname Is a private String that holds the Person objects firstname.
    * @param lastname Is a private String that holds the Person objects lastname.
    * @param phonenumber Is a private String that holds the Person objects phonenumber.
    * @param address Is a private String that holds the Person objects address.
    * @param email Is a private String that holds the Person objects email address.
    */
    public void showPerson() {
        JOptionPane.showMessageDialog(null, "Found:" + 
        "\nName: " + this.firstname + " " + this.lastname +
        "\nIdentification number: " + this.userid +
        "\nPhone number: " + this.phonenumber +
        "\nAddress: " + this.address +
        "\nEmail: " + this.email);
    }

    /**
    * This method is used to update a Person objects information.
    * 
    * @param id This is the new id that is asked from the end user.
    * If the end user decides to press enter the String is then filled with the original @param userid using the Persons getId() method.
    * @param fname This is the new id that is asked from the end user.
    * If the end user decides to press enter the String is then filled with the original @param firstname using the Persons getFname() method.
    * @param lname This is the new id that is asked from the end user.
    * If the end user decides to press enter the String is then filled with the original @param lastname using the Persons getLname() method.
    * @param phoneno This is the new id that is asked from the end user.
    * If the end user decides to press enter the String is then filled with the original @param phonenumber using the Persons getPhone() method.
    * @param address This is the new id that is asked from the end user.
    * If the end user decides to press enter the String is then filled with the original @param address using the Persons getAddress() method.
    * @param email This is the new id that is asked from the end user.
    * If the end user decides to press enter the String is then filled with the original @param email using the Persons getEmail() method.
    * @param updated This is a new Person object that is constructed using the Persons constructor method.
    * The Person is constructed using the new/old information @param id, @param fname, @param lname, @param phoneno, @param address and @param email.
    * @return This method returns the newly generated Person object @param updated.
    */
    public Person updatePerson() {
        String id = JOptionPane.showInputDialog("Old social security number :" + this.getId() + "\n\nEnter the new social security number\nin proper Finlands id format!\nDD/MM/YY/C/ZZZ/Q :\n\nIf you wish to make no changes please press [ENTER]");
        if (id.equals("")) {
            id = this.getId();
        }
        while (Verify.verifyId(id) != true) {
            id = JOptionPane.showInputDialog("Old social security number :" + this.getId() + "\n\nPlease give a proper Finlands id format!!!\nDD/MM/YY/C/ZZZ/Q :\n\nIf you wish to make no changes please press [ENTER]");
            if (id.equals("")) {
                id = this.getId();
            }
        }
        String fname = JOptionPane.showInputDialog("Old firstname :" + this.getFname() + "\n\nEnter a new firstname :\n\nIf you wish to make no changes please press [ENTER]");
        if (fname.equals("")) {
            fname = this.getFname();
        }
        while (Verify.verifyFname(fname) != true) {
            fname = JOptionPane.showInputDialog("Old firstname :" + this.getFname() + "\n\nPlease give a proper firstname :\n\nIf you wish to make no changes please press [ENTER]");
            if (fname.equals("")) {
                fname = this.getFname();
            }
        }
        String lname = JOptionPane.showInputDialog("Old lastname :" + this.getLname() + "\n\nEnter a new lastname :\n\nIf you wish to make no changes please press [ENTER]");
        if (lname.equals("")) {
            lname = this.getLname();
        }
        while (Verify.verifyLname(lname) != true) {
            lname = JOptionPane.showInputDialog("Old lastname :" + this.getLname() + "\n\nPlease give a proper lastname :\n\nIf you wish to make no changes please press [ENTER]");
            if (lname.equals("")) {
                lname = this.getLname();
            }
        }
        String phoneno = JOptionPane.showInputDialog("Old phonenumber :" + this.getPhone() + "\n\nEnter a new phonenumber :\n\nIf you wish to make no changes please press [ENTER]");
        if (phoneno.equals("")) {
            phoneno = this.getPhone();
        }
        while (Verify.verifyPhoneNo(phoneno) != true) {
            phoneno = JOptionPane.showInputDialog("Old phonenumber :" + this.getPhone() + "\n\nPlease give a proper phone number :\n\nIf you wish to make no changes please press [ENTER]");
            if (phoneno.equals("")) {
                phoneno = this.getPhone();
            }
        }
        String address = JOptionPane.showInputDialog("Old address :" + this.getAddress() + "\n\nEnter new address (optional) :\n\nIf you wish to make no changes please press [ENTER]");
        if (address.equals("")) {
            address = this.getAddress();
        }
        while (Verify.verifyAddress(address) != true) {
            address = JOptionPane.showInputDialog("Old address :" + this.getAddress() + "\n\nPlease enter a valid address (optional) :\n\nIf you wish to make no changes please press [ENTER]");
            if (address.equals("")) {
                address = this.getAddress();
            }
        }
        String email = JOptionPane.showInputDialog("Old email :" + this.getEmail() + "\n\nEnter new email address (optional) :\n\nIf you wish to make no changes please press [ENTER]");
        if (email.equals("")) {
            email = this.getEmail();
        }
        while (Verify.verifyEmail(email) != true) {
            email = JOptionPane.showInputDialog("Old email :" + this.getEmail() + "\n\nPlease enter a valid email address (optional) :\n\nIf you wish to make no changes please press [ENTER]");
            if (email.equals("")) {
                email = this.getEmail();
            }
        }

        // modify the existing Person object
        Person updated = new Person(id, fname, lname, phoneno, address, email);
        JOptionPane.showMessageDialog(null, "Contact updated");
        return updated;
    }

    /**
    * This method is used to ask if the end user wants to proceed deleting a Person object.
    * The method first shows a Person objects information and asks the end user to either press "1" to continue or "2" to not proceed
    * deleting the Person object that is currently being shown.
    *
    * @param userid Is a private String that holds the Person objects personal identification number.
    * @param firstname Is a private String that holds the Person objects firstname.
    * @param lastname Is a private String that holds the Person objects lastname.
    * @param phonenumber Is a private String that holds the Person objects phonenumber.
    * @param address Is a private String that holds the Person objects address.
    * @param email Is a private String that holds the Person objects email address.
    * @param delete Is the returned boolean value true if the Person is set to be deleted or false if not.
    * @return Returns a boolean value @param delete.
    */
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
            JOptionPane.showMessageDialog(null, "Contact deleted");
            delete = true;
        } else if (input.equals("2")) {
            JOptionPane.showMessageDialog(null, "No changes were made to the contact");
            delete = false;
        }

        return delete;
    }

    /**
    * This method is used to ask if the end user wants to proceed updating a Person object.
    * The method first shows a Person objects information and asks the end user to either press "1" to continue or "2" to not proceed
    * updating the Person object that is currently being shown.
    *
    * @param userid Is a private String that holds the Person objects personal identification number.
    * @param firstname Is a private String that holds the Person objects firstname.
    * @param lastname Is a private String that holds the Person objects lastname.
    * @param phonenumber Is a private String that holds the Person objects phonenumber.
    * @param address Is a private String that holds the Person objects address.
    * @param email Is a private String that holds the Person objects email address.
    * @param update Is the returned boolean value true if the Person is set to be updated or false if not.
    * @return Returns a boolean value @param update.
    */
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
            JOptionPane.showMessageDialog(null, "No changes were made to the contact");
            update = false;
        }

        return update;
    }

    /**
    * This is the set method for a Person object that sets a String for the private @param userid a Person object owns.
    * 
    * @param id This is the String that is being set for the Person object.
    */
    public void setId(String id) {
        userid = id;
    }
    /**
    * This is the get method for a Person object that gets the private String @param userid a Person object owns.
    * 
    * @return Returns the private String @param userid of the Person object.
    */
    public String getId() {
        return userid;
    }

    /**
    * This is the set method for a Person object that sets a String for the private @param firstname a Person object owns.
    * The method also automatically raises the first letter of the String to an uppercase character.
    * 
    * @param first This is the String that is being set for the Person object.
    */
    public void setFname(String first) {
        String formalf = first.substring(0, 1).toUpperCase() + first.substring(1).toLowerCase();
        firstname = formalf;
    }
    /**
    * This is the get method for a Person object that gets the private String @param firstname a Person object owns.
    * 
    * @return Returns the private String @param firstname of the Person object.
    */
    public String getFname() {
        return firstname;
    }

    /**
    * This is the set method for a Person object that sets a String for the private @param lastname a Person object owns.
    * The method also automatically raises the first letter of the String to an uppercase character.
    * 
    * @param last This is the String that is being set for the Person object.
    */
    public void setLname(String last) {
        String formall = last.substring(0, 1).toUpperCase() + last.substring(1).toLowerCase();
        lastname = formall;
    }
    /**
    * This is the get method for a Person object that gets the private String @param lastname a Person object owns.
    * 
    * @return Returns the private String @param lastname of the Person object.
    */
    public String getLname() {
        return lastname;
    }

    /**
    * This is the set method for a Person object that sets a String for the private @param phonenumber a Person object owns.
    * The method also automatically raises the first letter of the String to an uppercase character.
    * 
    * @param phonenum This is the String that is being set for the Person object.
    */
    public void setPhone(String phonenum) {
        phonenumber = phonenum;
    }
    /**
    * This is the get method for a Person object that gets the private String @param phonenumber a Person object owns.
    * 
    * @return Returns the private String @param phonenumber of the Person object.
    */
    public String getPhone() {
        return phonenumber;
    }

    /**
    * This is the set method for a Person object that sets a String for the private @param address a Person object owns.
    * The method also automatically raises the first letter of the String to an uppercase character.
    * 
    * @param add This is the String that is being set for the Person object.
    */
    public void setAddress(String add) {
        address = add;
    }
    /**
    * This is the get method for a Person object that gets the private String @param address a Person object owns.
    * 
    * @return Returns the private String @param address of the Person object.
    */
    public String getAddress() {
        return address;
    }

    /**
    * This is the set method for a Person object that sets a String for the private @param email a Person object owns.
    * The method also automatically raises the first letter of the String to an uppercase character.
    * 
    * @param emailadd This is the String that is being set for the Person object.
    */
    public void setEmail(String emailadd) {
        email = emailadd;
    }
    /**
    * This is the get method for a Person object that gets the private String @param email a Person object owns.
    * 
    * @return Returns the private String @param email of the Person object.
    */
    public String getEmail() {
        return email;
    }
}

/**
* Class Verify holds basic methods for verifying that the end user gives proper input
* to be held in the given field of the contactsbook.
*
* @author Juho Yli-Rajala
*/
class Verify {
    /**
    * The method verifyId() verifies if the given input was in the correct Finlands personal id format.
    * The validation process is set so that the String can not be empty.
    *
    * @param inputid End users input inputid is the String that the method then proceeds to verify.
    * @return return a boolean value true if the given input was in the correct Finlands personal id format
    * and false if it wasn't.
    */
    public static boolean verifyId(String inputid) {
        
        // checks if the given inputid Strings length is exactly 11
        if (inputid.length() != 11) {
            return false;
        }

        // checks if the first 6 indexes of the inputid String are numbers
        for (int i=0; i < 6; i++) {
            if (!("" + inputid.charAt(i)).matches("[0-9]+")) {
                return false;
            }
        }

        // validating through every possible error in the date month and year
        if ((Character.getNumericValue(inputid.charAt(0))) < 0 || (Character.getNumericValue(inputid.charAt(0))) > 3) {
            return false;
        } else if (Character.getNumericValue(inputid.charAt(0)) == 3 && Character.getNumericValue(inputid.charAt(1)) > 2) {
            return false;
        } else if (Character.getNumericValue(inputid.charAt(1)) < 0 || Character.getNumericValue(inputid.charAt(1)) > 9) {
            return false;
        } else if (Character.getNumericValue(inputid.charAt(2)) < 0 || Character.getNumericValue(inputid.charAt(2)) > 1) {
            return false;
        } else if (Character.getNumericValue(inputid.charAt(2)) == 1 && Character.getNumericValue(inputid.charAt(3)) > 2) {
            return false;
        } else if (Character.getNumericValue(inputid.charAt(3)) < 0 || Character.getNumericValue(inputid.charAt(3)) > 9) {
            return false;
        } else if (Character.getNumericValue(inputid.charAt(4)) < 0 || Character.getNumericValue(inputid.charAt(4)) > 9) {
            return false;
        } else if (Character.getNumericValue(inputid.charAt(5)) < 0 || Character.getNumericValue(inputid.charAt(5)) > 9) {
            return false;
        }

        // possiple marks for the century sign
        String centurysign = "+-UVWXYABCDEF";
        if (!centurysign.contains("" + inputid.charAt(6))) {
            return false;
        }

        // validates that the individual number ZZZ contains only numbers
        if (!("" + inputid.charAt(7)).matches("[0-9]+") || !("" + inputid.charAt(8)).matches("[0-9]+") || !("" + inputid.charAt(9)).matches("[0-9]+")) {
            return false;
        }

        // possible marks for the controlcharacter sign
        String controlcha = "0123456789ABCDEFHJKLMNPRSTUVWXY";
        if (!controlcha.contains("" + inputid.charAt(10))) {
            return false;
        }


        return true;
    }
    /**
    * The method verifyFname() verifies if the given input was a valid firstname.
    * The method requires the given String input to be atleast 1 character long and no longer than 39 characters.
    * The validation process is set so that the String can not be empty.
    *
    * @param inputfname End users input inputfname is the String that the method then proceeds to verify.
    * @return return a boolean value true if the given input was a valid firstname
    * and false if it wasn't.
    */
    public static boolean verifyFname(String inputfname) {
        if (inputfname.length() > 0 && inputfname.length() < 40 ) {
            return true;
        }
        
        return false;
    }
    /**
    * The method verifyLname() verifies if the given input was a valid lastname.
    * The method requires the given String input to be atleast 1 character long and no longer than 39 characters.
    * The validation process is set so that the String can not be empty.
    *
    * @param inputLname End users input inputlname is the String that the method then proceeds to verify.
    * @return return a boolean value true if the given input was a valid lastname
    * and false if it wasn't.
    */
    public static boolean verifyLname(String inputlname) {
        if (inputlname.length() > 0 && inputlname.length() < 40 ) {
            return true;
        }

        return false;
    }
    /**
    * The method verifyPhoneNo() verifies if the given input was a valid phonenumber.
    * The method requires the given String input to be atleast 3 numbers long and no greater than 12.
    * The validation process is set so that the String can not be empty.
    *
    * @param inputphone End users input inputphone is the String that the method then proceeds to verify.
    * @return return a boolean value true if the given input was a valid phonenumber
    * and false if it wasn't.
    */
    public static boolean verifyPhoneNo(String inputphone) {
        if (inputphone.length() >= 3 && inputphone.length() < 13) {
            boolean stringvalid = inputphone.matches("[0-9]+");
            if (stringvalid == true) {
                return true;
            }
        }

        return false;
    }
    /**
    * The method verifyAddress() verifies if the given input was a valid address.
    * The validation process is set so that the String can also be " " since the field is not mandatory.
    *
    * @param inputaddress End users input inputaddress is the String that the method then proceeds to verify.
    * @return return a boolean value true if the given input was a valid inputaddress
    * and false if it wasn't.
    */
    public static boolean verifyAddress(String inputaddress) {
        if (inputaddress.equals(" ")) {
            return true;
        }
        if (inputaddress.length() > 0 && inputaddress.length() < 40) {
            return true;
        }

        return false;
    }
    /**
    * The method verifyEmail() verifies if the given input was a valid email address.
    * The method requires the given String input to have atleast one character as ',' and also one character as '@',
    * but these characters cannot be set as the first or last character of String.
    * The validation process is set so that the String can also be " " since the field is not mandatory.
    *
    * @param inputemail End users input inputaddress is the String that the method then proceeds to verify.
    * @return return a boolean value true if the given input was a valid inputemail
    * and false if it wasn't.
    */
    public static boolean verifyEmail(String inputemail) {
        boolean verify;
        if (inputemail.equals(" ")) {
            verify = true;
        } else {
            int atcount = 0;
            int dotcount = 0;
            for (int i=0; i<inputemail.length(); i++) {
                if (inputemail.charAt(i) == '@') {
                    atcount++;
                } else if (inputemail.charAt(i) == '.') {
                    dotcount++;
                }
            }

            if (inputemail.charAt(0) != '@' &&
            inputemail.charAt(0) != '.' &&
            inputemail.charAt(inputemail.length()-1) != '@' &&
            inputemail.charAt(inputemail.length()-1) != '.' &&
            atcount == 1 && dotcount > 0) {
                verify = true;
            } else {
                verify = false;
            }
        }

        return verify;
    }
    /**
    * The method verifyYesNo() simply verifies if the given input was a character '1' or '2'.
    *
    * @param input Is the end users input that the method then proceeds to verify.
    * @return return a boolean value true if the given input was a character '1' or '2'.
    * and false if it wasn't.
    */
    public static boolean verifyYesNo(String input) {

        if (input.equals("1") || input.equals("2")) {
            return true;
        }

        return false;
    }
}