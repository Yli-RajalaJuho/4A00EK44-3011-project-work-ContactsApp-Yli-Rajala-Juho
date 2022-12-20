
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
import javax.swing.border.*;

/**
* The class ContactsApp holds the main method that is used to run
* the final application.
* This application uses a GUI and is run by asking the end user for inputs.
* The application uses a list of contacts that it stores in a separate
* text file named "Contactsbook.txt" and it refers to it
* when loading and saving information.
* 
* @author Juho Yli-Rajala
*/
public class ContactsApp {
    public static void main(String[] args) {
        
        //the contactsbook
        ArrayList<Person> contacts = loadContacts();

        //GUI window
        JFrame display = new JFrame();

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(5,5,5,5));
        JPanel layout = new JPanel(new GridBagLayout());
        layout.setBorder(new EmptyBorder(10,10,10,10));
        JPanel gridpanel = new JPanel(new GridLayout(5,1,1,1));


        //add
        JButton addnewcontact = new JButton("Add a new contact");
        addnewcontact.addActionListener((e) -> addNewPerson(contacts));
        gridpanel.add(addnewcontact);


        //search
        JButton searchcontacts = new JButton("Seach for a contact");
        searchcontacts.addActionListener((e) -> searchPerson(contacts));
        gridpanel.add(searchcontacts);


        //update
        JButton updatecontact = new JButton("Update an existing contact");
        updatecontact.addActionListener((e) -> updateContacts(contacts));
        gridpanel.add(updatecontact);


        //delete
        JButton deletecontact = new JButton("Delete a contact");
        deletecontact.addActionListener((e) -> deletePerson(contacts));
        gridpanel.add(deletecontact);


        //clear
        JButton clearcontacts = new JButton("Clear contactsbook");
        clearcontacts.addActionListener((e) -> clearContacts(contacts));
        gridpanel.add(clearcontacts);


        layout.add(gridpanel);
        panel.add(layout, BorderLayout.CENTER);
        display.add(panel);
        display.pack();
        display.setLocationRelativeTo(null);
        display.setSize(300,300);
        display.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        display.setVisible(true);

    }

    /**
    * This method is used to add a new Person object to the contacts ArrayList.
    * This method simply starts asking different info a Person object must have and
    * then constructs a new Person object and adds it to the given ArrayList.
    *
    * @param contactsbook This methods requires an existing ArrayList where to store the new Person object.
    * @param id This is a String that stores the end users input for id
    * and the method then proceeds to validate it using the class Verify.
    * @param fname This is a String that stores the end users input for firstname
    * and the method then proceeds to validate it using the class Verify.
    * @param lname This is a String that stores the end users input for lastname
    * and the method then proceeds to validate it using the class Verify.
    * @param phoneno This is a String that stores the end users input for phonenumber
    * and the method then proceeds to validate it using the class Verify.
    * @param address This is a String that stores the end users input for address which can also be empty
    * and the method then proceeds to validate it using the class Verify.
    * @param email This is a String that stores the end users input for email address which can also be empty
    * and the method then proceeds to validate it using the class Verify.
    * @param newcontact This is the newly constructed Person object that uses all the end users given and validated input as its info.
    * This new Person object is then stored into the given ArrayList contactsbook.
    */
    public static void addNewPerson(ArrayList contactsbook) {

        // asking the info
        String id = JOptionPane.showInputDialog("Enter social security number\nin the proper Finlands id format!\nDD/MM/YY/C/ZZZ/Q :");
        while (Verify.verifyId(id) != true || Verify.verifyIdDuplicates(id, contactsbook) != true) {
            if (Verify.verifyIdDuplicates(id, contactsbook) != true) {
                id = JOptionPane.showInputDialog("This id: [" + id + "] was already saved to another contact!" +
            "\n\nPlease give a unique id in the proper Finlands id format\nDD/MM/YY/C/ZZZ/Q :");
            } else {
                id = JOptionPane.showInputDialog("Please give a proper Finlands id format!!!\nDD/MM/YY/C/ZZZ/Q :");
            }
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
            address = JOptionPane.showInputDialog("Enter a valid address (optional) or press [ENTER] to continue:");
            if (address.equals("")) {
                address = " ";
            }
        }
        String email = JOptionPane.showInputDialog("Enter email address (optional):");
        if (email.equals("")) {
            email = " ";
        }
        while (Verify.verifyEmail(email) != true) {
            email = JOptionPane.showInputDialog("Enter a valid email address (optional) or press [ENTER] to continue:");
            if (email.equals("")) {
                email = " ";
            }
        }

        // add the contact into the arraylist
        Person newcontact = new Person(id, fname, lname, phoneno, address, email);
        contactsbook.add(newcontact);
        JOptionPane.showMessageDialog(null, "New contact has been stored");

        saveContacts(contactsbook);
    }

    /**
    * This method is used to search for a certain Person object from the contacts ArrayList.
    * The method first asks who the end user is searching for
    * and if something related to the end users input was found
    * it shows a separate messagedialog containing all the Person objects info related to the input.
    * If many Person objects share the same name etc. it shows every Person object
    * related to that one end users input one at a time.
    *
    * @param contactsbook This methods requires an existing ArrayList where to search everything from.
    * @param found This is a boolean value that is set false by default and
    * it is used to show a messagedialog if nothing was found.
    * @param input This is a String that holds the end users input
    * when searching for a Person object from the ArrayList contactsbook.
    * @param y This is the Person object that has been found based on the String input.
    */
    public static void searchPerson(ArrayList contactsbook) {
        //boolean to verify if something was found
        boolean found = false;

        // asking who to look for
        String input = JOptionPane.showInputDialog("Enter one of the following:\nid\nfirstname\nlastname\nphoneNo.\naddress\nemail\nfor the contact you are searching for:" +
        "\n\nIf you wish to get all the contacts type [ALL]");

        //if searching all
        if (input.equalsIgnoreCase("ALL")) {
            for (int i=0; i<contactsbook.size(); i++) {
                Person currentp = (Person) contactsbook.get(i);
                currentp.showPerson();
            }
            found = true;
            if (contactsbook.size() == 0) {
                JOptionPane.showMessageDialog(null, "The contactsbook is empty");
            }
        } else {
            //searching the arraylist for matching atributes and if found returning them
            for (int j=0; j<contactsbook.size(); j++) {
                Person y = (Person) contactsbook.get(j);
                if ((input.equalsIgnoreCase(y.getId())) ||
                input.equalsIgnoreCase(y.getFname()) ||
                input.equalsIgnoreCase(y.getLname()) ||
                input.equals(y.getPhone()) ||
                input.equalsIgnoreCase(y.getAddress()) ||
                input.equalsIgnoreCase(y.getEmail())) {
                    y.showPerson();
                    found = true;
                }
            }
        }

        // if nothing is found show message
        if (found != true && input != null) {
            JOptionPane.showMessageDialog(null, "No matching search results found for:\n" + input);
        }
    }

    /**
    * This method is used to update a certain Person object in the contacts ArrayList.
    * The method first asks who the end user is searching for and if something related to the end users input was found
    * it asks if the end user wants to proceed updating this Person object showing the contacts info simultaneously.
    * The method saves the everything after being called using the separate method for saving.
    *
    * @param contactsbook This methods requires an existing ArrayList where to search everything from.
    * @param found This is a boolean value that is set false by default and
    * it is used to show a messagedialog if nothing was found.
    * @param input This is a String that holds the end users input
    * when searching for a Person object from the ArrayList contactsbook.
    * @param y This is the Person object that has been found based on the String input.
    */
    public static void updateContacts(ArrayList contactsbook) {
        //boolean to verify if something was found
        boolean found = false;

        // asking who to look for based on any of the users info
        String input = JOptionPane.showInputDialog("Enter one of the following:\nid\nfirstname\nlastname\nphoneNo.\naddress\nemail\nfor the contact you are searching for :");

        //searching the given input
        for (int i=0; i<contactsbook.size(); i++) {
            Person y = (Person) contactsbook.get(i);
            if ((input.equalsIgnoreCase(y.getId())) ||
            input.equalsIgnoreCase(y.getFname()) ||
            input.equalsIgnoreCase(y.getLname()) ||
            input.equals(y.getPhone()) ||
            input.equalsIgnoreCase(y.getAddress()) ||
            input.equalsIgnoreCase(y.getEmail())) {
                //verifying if user wants to update this person
                if (y.validateUpd() == 0) {
                    Person updated = y.updatePerson(contactsbook);
                    contactsbook.set(i, updated);
                }
                found = true;
            }
        }
        
        // if nothing is found show message
        if (found != true && input != null) {
            JOptionPane.showMessageDialog(null, "No matching search results found for :\n" + input);
        }

        saveContacts(contactsbook);
    }

    /**
    * This method is used to delete a certain Person object from the contacts ArrayList.
    * The method first asks who the end user is searching for and if something related to the end users input was found
    * it asks if the end user wants to proceed deleting this Person object showing the contacts info simultaneously.
    * The method saves the everything after being called using the separate method for saving.
    *
    * @param contactsbook This methods requires an existing ArrayList where to search everything from.
    * @param found This is a boolean value that is set false by default and
    * it is used to show a messagedialog if nothing was found.
    * @param input This is a String that holds the end users input
    * when searching for a Person object from the ArrayList contactsbook.
    * @param y This is the Person object that has been found based on the String input.
    */
    public static void deletePerson(ArrayList contactsbook) {
        //boolean to verify if something was found
        boolean found = false;

        // asking who to look for based on any of the users info
        String input = JOptionPane.showInputDialog("Enter one of the following:\nid\nfirstname\nlastname\nphoneNo.\naddress\nemail\nfor the contact you are searching for:");

        //searching the given input
        for (int i=0; i<contactsbook.size(); i++) {
            Person y = (Person) contactsbook.get(i);
            if ((input.equalsIgnoreCase(y.getId())) ||
            input.equalsIgnoreCase(y.getFname()) ||
            input.equalsIgnoreCase(y.getLname()) ||
            input.equals(y.getPhone()) ||
            input.equalsIgnoreCase(y.getAddress()) ||
            input.equalsIgnoreCase(y.getEmail())) {
                //verifying if user wants to delete this person
                if (y.validateDel() == 0) {
                    contactsbook.remove(y);
                }
                found = true;
            }
        }
        
        // if nothing is found show message
        if (found != true && input != null) {
            JOptionPane.showMessageDialog(null, "No matching search results found for:\n" + input);
        }
        saveContacts(contactsbook);
    }

    /**
    * This method is used to save the whole ArrayList into the text file "Contactsbook.txt".
    *
    * @param contactsbook This is the current ArrayList that holds different Person objects information.
    * @param personinfo This is the Person object which info is currently being set as one single String currentperson.
    * @param currentperson This is a String constructed using personinfos get methods and the separating each piece of info with a char ','.
    * @param infoholder This is a new FileWriter that stores everything in the text file "Contactsbook.txt".
    * @param contactsholder This is a new PrintWriter that prints one Person objects info
    * using the String currentperson into a sigle line into the text file.
    * @throws e Throws an IOExeption.
    */
    public static void saveContacts(ArrayList contactsbook) {
        try {
            Person personinfo;
            String currentperson;
            FileWriter infoholder = new FileWriter("Contactsbook.txt");
            PrintWriter contactsholder = new PrintWriter(infoholder);

            //going through the whole ArrayList and writing it down
            for (int i=0; i<contactsbook.size(); i++) {
                personinfo = (Person) contactsbook.get(i);
                currentperson = personinfo.getId() + "," +
                personinfo.getFname() + "," + personinfo.getLname() + "," +
                personinfo.getPhone() + "," + personinfo.getAddress() + "," +
                personinfo.getEmail();
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
    * The method first separates all the information the text file has by splitting the information based on the char ','.
    * Then the method constructs a Person object using this information and as the BufferedReader finishes a line in the text file
    * it continues reading the next one but now constructing a new Person object separate from the last one
    * and continues this as long as the next line is not null.
    *
    * @param contactslist An ArrayList that is filled by the multiple Person objects information the text file "Contactsbook.txt" has.
    * @param personinfo This String holds a whole line (one Person objects information) fetched from the text file.
    * @param tokens This is a String that holds the current Person objects information in the String personinfo split by the char ','.
    * @param id A Person objects id that is found in the tokens first field.
    * @param fname A Person objects firstname that is found in the tokens second field.
    * @param lname A Person objects lastname that is found in the tokens third field.
    * @param phone A Person objects phone number that is found in the tokens fourth field.
    * @param add A Person objects address that is found in the tokens fifth field.
    * @param email A Person objects email that is found in the tokens sixth field.
    * @param currentp This is a new Person object that is then stored into the ArrayList contactslist.
    * @param bookreader This is a new FileReader that goes through the text file "Contactsbook.txt".
    * @param inforader This is a new BufferedReader that stores the current Person objects info (one single line in the text file).
    * @return Returns the new ArrayList contactslist.
    * @throws e Throws an IOExeption.
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
                Person currentp = new Person(id, fname, lname, phone, add, email);
                contactslist.add(currentp);
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
    * This method is used to clear the whole contactsbook ArrayList and
    * then saving it, also clearing the separate text file "Contactsbook.txt".
    *
    * @param yesno This is simply an integer that holds a value "0" or "1"
    * depending on if the end user wants to clear the whole contactsbook.
    */
    public static void clearContacts(ArrayList contactsbook) {
        int yesno = JOptionPane.showConfirmDialog(null, "Do you wish to clear all of the contacts", "clear contacts", JOptionPane.YES_NO_OPTION);
        if (yesno == 0) {
            contactsbook.removeAll(contactsbook);
            JOptionPane.showMessageDialog(null, "Contactsbook cleared");
        } else {
            JOptionPane.showMessageDialog(null, "No changes made to the contactsbook");
        }

        saveContacts(contactsbook);
    }
}

/**
* Class Person has private Strings which hold the information a Person object has. 
* It also holds methods constructing a new Person object
* as well as methods that aquire certain aspects of the specific Person object
* depending on what the end user wants to currently do.
* 
* @param userid Is a private String that holds the Person objects personal identification number.
* @param firstname Is a private String that holds the Person objects firstname.
* @param lastname Is a private String that holds the Person objects lastname.
* @param phonenumber Is a private String that holds the Person objects phonenumber.
* @param address Is a private String that holds the Person objects address.
* @param email Is a private String that holds the Person objects email address.
*
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
    * This method is used to show all of the Person objects information through JOptionPanel MessageDialog.
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
    * The method first checks if the end user has given
    * a new String to be stored into the old informations place.
    * The method then proceeds to verify the new String using the correct verifying method.
    * If the end user doesn't enter a new String
    * it uses the old information using the Person objects get() methods.
    * 
    * @param contacts This is the contacts list where the id refers to when defining
    * if the end users given input for id was already presented in another Person objects data.
    * @param id This is the new id that is asked from the end user.
    * If the end user decides to press enter the String is then filled
    * with the original userid using the Persons getId() method.
    * @param fname This is the new id that is asked from the end user.
    * If the end user decides to press enter the String is then filled
    * with the original firstname using the Persons getFname() method.
    * @param lname This is the new id that is asked from the end user.
    * If the end user decides to press enter the String is then filled
    * with the original lastname using the Persons getLname() method.
    * @param phoneno This is the new id that is asked from the end user.
    * If the end user decides to press enter the String is then filled
    * with the original phonenumber using the Persons getPhone() method.
    * @param address This is the new id that is asked from the end user.
    * If the end user decides to press enter the String is then filled
    * with the original address using the Persons getAddress() method.
    * @param email This is the new id that is asked from the end user.
    * If the end user decides to press enter the String is then filled
    * with the original email using the Persons getEmail() method.
    * @param updated This is a new Person object that is constructed
    * using the Persons constructor method.
    * The Person is constructed using the new/old information.
    * @return This method returns the newly generated Person object updated.
    */
    public Person updatePerson(ArrayList contacts) {
        String id = JOptionPane.showInputDialog("Old social security number :" + this.getId() + 
        "\n\nEnter the new social security number\nin proper Finlands id format!\nDD/MM/YY/C/ZZZ/Q :\n\nIf you wish to make no changes please press [ENTER]");
        if (id == null || id.equals("")) {
            id = this.getId();
        }
        while ((Verify.verifyId(id) != true || Verify.verifyIdDuplicates(id, contacts) != true) && !id.equals(this.getId())) {
            if (id == null || id.equals("")) {
                id = this.getId();
            } else if (Verify.verifyIdDuplicates(id, contacts) != true) {
                id = JOptionPane.showInputDialog("This id: [" + id + "] was already saved to another contact!" +
                "\n\nPlease give a unique id in the proper Finlands id format\nDD/MM/YY/C/ZZZ/Q :");
            } else {
                id = JOptionPane.showInputDialog("Please give a proper Finlands id format!!!\nDD/MM/YY/C/ZZZ/Q :");
            }
        }
        String fname = JOptionPane.showInputDialog("Old firstname :" + this.getFname() + 
        "\n\nEnter a new firstname :\n\nIf you wish to make no changes please press [ENTER]");
        if (fname.equals("")) {
            fname = this.getFname();
        }
        while (Verify.verifyFname(fname) != true) {
            fname = JOptionPane.showInputDialog("Old firstname :" + this.getFname() + 
            "\n\nPlease give a proper firstname :\n\nIf you wish to make no changes please press [ENTER]");
            if (fname.equals("")) {
                fname = this.getFname();
            }
        }
        String lname = JOptionPane.showInputDialog("Old lastname :" + this.getLname() + 
        "\n\nEnter a new lastname :\n\nIf you wish to make no changes please press [ENTER]");
        if (lname.equals("")) {
            lname = this.getLname();
        }
        while (Verify.verifyLname(lname) != true) {
            lname = JOptionPane.showInputDialog("Old lastname :" + this.getLname() + 
            "\n\nPlease give a proper lastname :\n\nIf you wish to make no changes please press [ENTER]");
            if (lname.equals("")) {
                lname = this.getLname();
            }
        }
        String phoneno = JOptionPane.showInputDialog("Old phonenumber :" + this.getPhone() + 
        "\n\nEnter a new phonenumber :\n\nIf you wish to make no changes please press [ENTER]");
        if (phoneno.equals("")) {
            phoneno = this.getPhone();
        }
        while (Verify.verifyPhoneNo(phoneno) != true) {
            phoneno = JOptionPane.showInputDialog("Old phonenumber :" + this.getPhone() + 
            "\n\nPlease give a proper phone number :\n\nIf you wish to make no changes please press [ENTER]");
            if (phoneno.equals("")) {
                phoneno = this.getPhone();
            }
        }
        String address = JOptionPane.showInputDialog("Old address :" + this.getAddress() + 
        "\n\nEnter new address (optional) :\n\nIf you wish to make no changes please press [ENTER]");
        if (address.equals("")) {
            address = this.getAddress();
        }
        while (Verify.verifyAddress(address) != true) {
            address = JOptionPane.showInputDialog("Old address :" + this.getAddress() + 
            "\n\nPlease enter a valid address (optional) :\n\nIf you wish to make no changes please press [ENTER]");
            if (address.equals("")) {
                address = this.getAddress();
            }
        }
        String email = JOptionPane.showInputDialog("Old email :" + this.getEmail() + 
        "\n\nEnter new email address (optional) :\n\nIf you wish to make no changes please press [ENTER]");
        if (email.equals("")) {
            email = this.getEmail();
        }
        while (Verify.verifyEmail(email) != true) {
            email = JOptionPane.showInputDialog("Old email :" + this.getEmail() + 
            "\n\nPlease enter a valid email address (optional) :\n\nIf you wish to make no changes please press [ENTER]");
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
    * The method first shows the Person objects information and asks the end user to press either yes or no to
    * delete the Person object that is currently being shown.
    *
    * @param yesno This integer holds the end users input which is then validated.
    * @return Returns the integer value that yesno holds currently.
    */
    public int validateDel() {
        int yesno = JOptionPane.showConfirmDialog(null, "Do you wish to delete this contact:" + 
            "\nName: " + this.firstname + " " + this.lastname +
            "\nIdentification number: " + this.userid +
            "\nPhone number: " + this.phonenumber +
            "\nAddress: " + this.address +
            "\nEmail: " + this.email, "delete contact", JOptionPane.YES_NO_OPTION);
        if (yesno == 0) {
            JOptionPane.showMessageDialog(null, "Contact deleted");
        } else {
            JOptionPane.showMessageDialog(null, "No changes made to the contact");
        }

        return yesno;
    }

    /**
    * This method is used to ask if the end user wants to proceed updating a Person object.
    * The method first shows the Person objects information and asks the end user to press either yes or no to
    * update the Person object that is currently being shown.
    *
    * @param yesno This integer holds the end users input which is then validated.
    * @return Returns the integer value that yesno holds currently.
    */
    public int validateUpd() {
        int yesno = JOptionPane.showConfirmDialog(null, "Do you wish to update this contact:" + 
            "\nName: " + this.firstname + " " + this.lastname +
            "\nIdentification number: " + this.userid +
            "\nPhone number: " + this.phonenumber +
            "\nAddress: " + this.address +
            "\nEmail: " + this.email, "update contact", JOptionPane.YES_NO_OPTION);
        if (yesno != 0) {
            JOptionPane.showMessageDialog(null, "No changes made to the contact");
        }

        return yesno;
    }

    /**
    * This is the set method for a Person object that sets a String for the private userid a Person object owns.
    * 
    * @param id This is the String that is being set for the Person object.
    */
    public void setId(String id) {
        userid = id;
    }
    /**
    * This is the get method for a Person object that gets the private String userid a Person object owns.
    * 
    * @return Returns the private String userid of the Person object.
    */
    public String getId() {
        return userid;
    }

    /**
    * This is the set method for a Person object that sets a String for the private firstname a Person object owns.
    * The method also automatically raises the first letter of the String to an uppercase character.
    * 
    * @param first This is the String that is being set for the Person object.
    * @param formalf This is the String first but the first character is now in uppercase.
    */
    public void setFname(String first) {
        String formalf = first.substring(0, 1).toUpperCase() + first.substring(1).toLowerCase();
        firstname = formalf;
    }
    /**
    * This is the get method for a Person object that gets the private String firstname a Person object owns.
    * 
    * @return Returns the private String firstname of the Person object.
    */
    public String getFname() {
        return firstname;
    }

    /**
    * This is the set method for a Person object that sets a String for the private lastname a Person object owns.
    * The method also automatically raises the first letter of the String to an uppercase character.
    * 
    * @param last This is the String that is being set for the Person object.
    * @param formall This is the String last but the first character is now in uppercase.
    */
    public void setLname(String last) {
        String formall = last.substring(0, 1).toUpperCase() + last.substring(1).toLowerCase();
        lastname = formall;
    }
    /**
    * This is the get method for a Person object that gets the private String lastname a Person object owns.
    * 
    * @return Returns the private String lastname of the Person object.
    */
    public String getLname() {
        return lastname;
    }

    /**
    * This is the set method for a Person object that sets a String for the private phonenumber a Person object owns.
    * The method also automatically raises the first letter of the String to an uppercase character.
    * 
    * @param phonenum This is the String that is being set for the Person object.
    */
    public void setPhone(String phonenum) {
        phonenumber = phonenum;
    }
    /**
    * This is the get method for a Person object that gets the private String phonenumber a Person object owns.
    * 
    * @return Returns the private String phonenumber of the Person object.
    */
    public String getPhone() {
        return phonenumber;
    }

    /**
    * This is the set method for a Person object that sets a String for the private address a Person object owns.
    * The method also automatically raises the first letter of the String to an uppercase character.
    * 
    * @param add This is the String that is being set for the Person object.
    */
    public void setAddress(String add) {
        address = add;
    }
    /**
    * This is the get method for a Person object that gets the private String address a Person object owns.
    * 
    * @return Returns the private String address of the Person object.
    */
    public String getAddress() {
        return address;
    }

    /**
    * This is the set method for a Person object that sets a String for the private email a Person object owns.
    * The method also automatically raises the first letter of the String to an uppercase character.
    * 
    * @param emailadd This is the String that is being set for the Person object.
    */
    public void setEmail(String emailadd) {
        email = emailadd;
    }
    /**
    * This is the get method for a Person object that gets the private String email a Person object owns.
    * 
    * @return Returns the private String email of the Person object.
    */
    public String getEmail() {
        return email;
    }
}

/**
* Class Verify holds basic methods for verifying that the end user gives proper input
* to be held by the Person object in the given field of the contactsbook.
*
* @author Juho Yli-Rajala
*/
class Verify {
    /**
    * The method verifyId() verifies if the given input was in the correct Finlands personal id format.
    * The validation process is set so that the String can not be empty.
    *
    * @param inputid End users input inputid is the String that the method then proceeds to verify.
    * @param centurysign Is used to define all the possible inputs a centurysign holds in the Finnish id format.
    * @param controlcha Is used to define all the possible inputs the controlcharacter holds in the Finnish id format.
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
        if ((Character.getNumericValue(inputid.charAt(0))) < 0 ||
        (Character.getNumericValue(inputid.charAt(0))) > 3) {
            return false;
        } else if (Character.getNumericValue(inputid.charAt(0)) == 3 &&
        Character.getNumericValue(inputid.charAt(1)) > 2) {
            return false;
        } else if (Character.getNumericValue(inputid.charAt(1)) < 0 ||
        Character.getNumericValue(inputid.charAt(1)) > 9) {
            return false;
        } else if (Character.getNumericValue(inputid.charAt(2)) < 0 ||
        Character.getNumericValue(inputid.charAt(2)) > 1) {
            return false;
        } else if (Character.getNumericValue(inputid.charAt(2)) == 1 &&
        Character.getNumericValue(inputid.charAt(3)) > 2) {
            return false;
        } else if (Character.getNumericValue(inputid.charAt(3)) < 0 ||
        Character.getNumericValue(inputid.charAt(3)) > 9) {
            return false;
        } else if (Character.getNumericValue(inputid.charAt(4)) < 0 ||
        Character.getNumericValue(inputid.charAt(4)) > 9) {
            return false;
        } else if (Character.getNumericValue(inputid.charAt(5)) < 0 ||
        Character.getNumericValue(inputid.charAt(5)) > 9) {
            return false;
        }

        // possiple marks for the century sign
        String centurysign = "+-UVWXYABCDEF";
        if (!centurysign.contains("" + inputid.charAt(6))) {
            return false;
        }

        // validates that the individual number ZZZ contains only numbers
        if (!("" + inputid.charAt(7)).matches("[0-9]+") ||
        !("" + inputid.charAt(8)).matches("[0-9]+") ||
        !("" + inputid.charAt(9)).matches("[0-9]+")) {
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
    * This method verifies that the given id was a unique id and it was not already found from another Person objects data.
    * 
    * @param inputid End users input inputid is the String that the method then proceeds to verify.
    * @param book This is the ArrayList where the method searches every Person objects ids.
    * @param currentp This is the current Person object where the inputid is then compared to.
    * @return This returns a boolean true if the id was unique and false if it wasn't.
    */
    public static boolean verifyIdDuplicates(String inputid, ArrayList book) {
        for (int i=0; i<book.size(); i++) {
            Person currentp = (Person) book.get(i);
            if (inputid.equals(currentp.getId())) {
                return false;
            }
        }

        return true;
    }
    /**
    * The method verifyFname() verifies if the given input was a valid firstname.
    * The method requires the given String input to be atleast 2 characters long and no longer than 39 characters.
    * If the name contains '-' it can not be the first or the last char of the String.
    * The validation process is set so that the String can not be empty.
    *
    * @param inputfname End users input inputfname is the String that the method then proceeds to verify.
    * @return return a boolean value true if the given input was a valid firstname
    * and false if it wasn't.
    */
    public static boolean verifyFname(String inputfname) {
        if (inputfname.isBlank() && inputfname.length() > 1) {
            return false;
        }
        if (inputfname.length() > 1 &&
        inputfname.length() < 40 &&
        inputfname.matches(".*[A-Za-zåäöÅÄÖ-]*+") &&
        inputfname.charAt(0) != '-' &&
        inputfname.charAt(inputfname.length()-1) != '-') {
            return true;
        }
        
        return false;
    }
    /**
    * The method verifyLname() verifies if the given input was a valid lastname.
    * The method requires the given String input to be atleast 2 characters long and no longer than 39 characters.
    * If the name contains '-' it can not be the first or the last char of the String.
    * The validation process is set so that the String can not be empty.
    *
    * @param inputLname End users input inputlname is the String that the method then proceeds to verify.
    * @return return a boolean value true if the given input was a valid lastname
    * and false if it wasn't.
    */
    public static boolean verifyLname(String inputlname) {
        if (inputlname.isBlank() && inputlname.length() > 1) {
            return false;
        }
        if (inputlname.length() > 1 &&
        inputlname.length() < 40 &&
        inputlname.matches(".*[A-Za-zåäöÅÄÖ-]*+") &&
        inputlname.charAt(0) != '-' &&
        inputlname.charAt(inputlname.length()-1) != '-') {
            return true;
        }

        return false;
    }
    /**
    * The method verifyPhoneNo() verifies if the given input was a valid phonenumber.
    * The method requires the given String input to be atleast 3 numbers long and no greater than 11.
    * If the number given includes a '+' sign it can be atleast 4 chars long and no greater than 12.
    * The validation process is set so that the String can not be empty.
    *
    * @param inputphone End users input inputphone is the String that the method then proceeds to verify.
    * @param pluscount This counts down how many '+' chars the String contains.
    * @param plusfirst This checks if the first char of the String was '+'.
    * @param stringvalid This validates if the given String contained only certain chars using regex.
    * @return return a boolean value true if the given input was a valid phonenumber
    * and false if it wasn't.
    */
    public static boolean verifyPhoneNo(String inputphone) {
        int pluscount = 0;
        for (int i=0; i<inputphone.length(); i++) {
            if (inputphone.charAt(i) == '+') {
                pluscount++;
            }
        }
        boolean plusfirst = true;
        for (int j=1; j<inputphone.length(); j++) {
            if (inputphone.charAt(j) == '+') {
                plusfirst = false;
            }
        }
        boolean stringvalid = inputphone.matches("[0-9+]+");
        if (inputphone.contains("+")) {
            if (inputphone.length() >= 4 &&
            inputphone.length() <= 12 &&
            pluscount == 1 &&
            plusfirst == true &&
            stringvalid == true) {
                return true;
            }
        } else if (!inputphone.contains("+")) {
            if (inputphone.length() >= 3 &&
            inputphone.length() <= 11 &&
            stringvalid == true) {
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
        if (inputaddress.length() > 0 && inputaddress.length() < 40 &&
        inputaddress.matches("[A-Za-z0-9/-]+")) {
            return true;
        }

        return false;
    }
    /**
    * The method verifyEmail() verifies if the given input was a valid email address.
    * The method requires the given String input to have atleast one character as ',' and also one character as '@',
    * but these characters cannot be set as the first or last character of String or back to back.
    * The validation process is set so that the String can also be " " since the field is not mandatory.
    *
    * @param inputemail End users input inputaddress is the String that the method then proceeds to verify.
    * @param verify This is a boolean value that validates if all the requirements for a valid String are met.
    * @param atcount This holds the number of how many '@' chars the String has.
    * @param dotcount This holds the number of how many '.' chars the String has.
    * @param atsignindex This holds the index where the '@' char is in the String and
    * it is used when defining that the character next to '@' is not '.'.
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
            int atsignindex = 0;
            for (int i=0; i<inputemail.length(); i++) {
                if (inputemail.charAt(i) == '@') {
                    atsignindex = i;
                    atcount++;
                } else if (inputemail.charAt(i) == '.') {
                    dotcount++;
                }
            }

            if (inputemail.charAt(0) != '@' &&
            inputemail.charAt(0) != '.' &&
            inputemail.charAt(inputemail.length()-1) != '@' &&
            inputemail.charAt(inputemail.length()-1) != '.' &&
            atcount == 1 && dotcount > 0 &&
            inputemail.charAt(atsignindex - 1) != '.' && 
            inputemail.charAt(atsignindex + 1) != '.') {
                verify = true;
            } else {
                verify = false;
            }
        }

        return verify;
    }
}