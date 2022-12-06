package com.address_book;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class AddressBookMainUC13 {
    private List<personDetails> addressBook;

    //Map is used to store data in key and value format.
    //Used create multiple address books
    private Map<String, List<personDetails>> addressBooks = new HashMap<String, List<personDetails>>();

    private static final Scanner sc = new Scanner(System.in);

    private void addPerson() {
        System.out.println("Please select the book");
        String bookName = sc.nextLine();
        addressBook = getAddressBook(bookName);
        personDetails person = new personDetails();
        System.out.println("Enter First Name");
        String firstName = sc.nextLine();
        System.out.println("Enter Last Name");
        String lastName = sc.nextLine();
        System.out.println("Enter Address");
        String address = sc.nextLine();
        System.out.println("Enter City");
        String city = sc.nextLine();
        System.out.println("Enter State");
        String state = sc.nextLine();
        System.out.println("Enter Zip code");
        int zipcode = Integer.parseInt(sc.nextLine());
        System.out.println("Enter Phone Number");
        long phoneNumber = Long.parseLong(sc.nextLine());

        person.setFirstName(firstName);
        person.setLastName(lastName);
        person.setAddress(address);
        person.setCity(city);
        person.setState(state);
        person.setZipCode(zipcode);
        person.setPhoneNumber(phoneNumber);

        addressBook.add(person);
    }

    private void editPerson() {
        System.out.println("Please select the book");
        String bookName = sc.nextLine();
        addressBook = getAddressBook(bookName);
        System.out.println("Enter the person name");
        String personName = sc.nextLine();
        personDetails personDetails = null;
        for (personDetails details : addressBook) {
            if (personName.equals(details.getFirstName()) || personName.equals(details.getLastName())) {
                personDetails = details;
                break;
            }
        }
        if (personDetails != null) {
            System.out.println("Enter Address");
            String address = sc.nextLine();
            System.out.println("Enter City");
            String city = sc.nextLine();
            System.out.println("Enter State");
            String state = sc.nextLine();
            System.out.println("Enter Zip code");
            int zipcode = Integer.parseInt(sc.nextLine());
            System.out.println("Enter Phone Number");
            long phoneNumber = Long.parseLong(sc.nextLine());
            personDetails.setAddress(address);
            personDetails.setCity(city);
            personDetails.setState(state);
            personDetails.setZipCode(zipcode);
            personDetails.setPhoneNumber(phoneNumber);
        } else {
            System.out.println("No contacts details found" + personName);
        }
    }

    private void deletePerson() {
        System.out.println("Please select the book");
        String bookName = sc.nextLine();
        addressBook = getAddressBook(bookName);
        System.out.println("Enter the person name");
        String personName = sc.nextLine();
        for (int i = 0; i < addressBook.size(); i++) {
            if (personName.equals(addressBook.get(i).getFirstName()) || personName.equals(addressBook.get(i).getLastName())) {
                addressBook.remove(i);
                System.out.println("Deleting contact......");
            } else {
                System.out.println("No contact found");
            }
        }
    }

    //Searching person details are duplicate or not
    public void searchPerson() {
        System.out.println("Please select the book");
        String bookName = sc.nextLine();
        addressBook = getAddressBook(bookName);
        System.out.println("Enter the First name");
        String firstName = sc.nextLine();
        System.out.println("Enter the Last name");
        String lastName = sc.nextLine();

        if (isPersonAdded(addressBook, firstName, lastName)) {
            System.out.println("Duplicate Entry");
        } else {
            System.out.println("No Entry found so adding person");
            addPerson();
        }
    }

    // checking person by first name and last name.
    public boolean isPersonAdded(List<personDetails> personList, String firstName, String lastName) {
        return personList.stream().anyMatch(item -> item.getFirstName().equals(firstName) && item.getLastName().equals(lastName));
    }


    //searching person from all address books by using city or state
    private void searchPersonInMultipleBook() {
        addressBook = getAddressBook(null);
        System.out.println("Enter the city or state name");
        String name = sc.nextLine();

        if (SearchPersonInMultipleBook(addressBooks, name).size() > 0) {
            printMap(SearchPersonInMultipleBook(addressBooks, name));
        } else {
            System.out.println("No Details Found");
        }
    }

    public Map<String, List<personDetails>> SearchPersonInMultipleBook(Map<String, List<personDetails>> addressBooks, String input) {
        HashMap<String, List<personDetails>> allDetails = new HashMap<>();
        List<personDetails> allPerson;
        for (Map.Entry<String, List<personDetails>> list : addressBooks.entrySet()) {
            allPerson = list.getValue().stream()
                    .filter(i -> i.getCity().equals(input) || i.getState().equals(input))
                    .collect(Collectors.toList());
            allDetails.put(list.getKey(), allPerson);

        }
        return allDetails;
    }

    //Searching person by city or state
    public void searchPersonByCityOrState() {
        System.out.println("Please select the book");
        String bookName = sc.nextLine();
        addressBook = getAddressBook(bookName);
        System.out.println("Enter the city or state name");
        String name = sc.nextLine();

        if (searchByCityOrState(addressBook, name).size() > 0) {
            System.out.println(searchByCityOrState(addressBook, name));
        } else {
            System.out.println("No Details Found");
        }
    }

    private List<personDetails> searchByCityOrState(List<personDetails> addressBook, String input) {
        return addressBook.stream()
                .filter(i -> i.getCity().equals(input) || i.getState().equals(input))
                .collect(Collectors.toList());
    }

    // fining count by city or state
    public void getCountByCityState() {
        System.out.println("Please select the book");
        String bookName = sc.nextLine();
        addressBook = getAddressBook(bookName);
        System.out.println("Enter the city or state name");
        String input = sc.nextLine();
        System.out.println("Count : " + addressBook.stream().filter(city -> city.getCity().equals(input) || city.getState().equals(input)).count());

    }

    //sorting person by alphabetically by person's name
    public void sortPersonByName() {
        System.out.println("Please select the book");
        String bookName = sc.nextLine();
        addressBook = getAddressBook(bookName);
        List<personDetails> result = addressBook.stream().sorted(
                Comparator.comparing(personDetails::getFirstName)).collect(Collectors.toList());
        Map<String, List<personDetails>> map = new HashMap<>();
        map.put(bookName, result);
        printMap(map);
    }

    //Sorting person by city, state & zip code
    public void sortByCityStateZip() {
        System.out.println("Please select the book");
        String bookName = sc.nextLine();
        addressBook = getAddressBook(bookName);
        System.out.println("Select option \n 1.for City \n 2.for state \n 3.for zip ");
        int choice = Integer.parseInt(sc.nextLine());
        List<personDetails> result = null;
        switch (choice) {
            case 1 -> result = addressBook.stream().sorted(
                    Comparator.comparing(personDetails::getCity)).collect(Collectors.toList());
            case 2 -> result = addressBook.stream().sorted(
                    Comparator.comparing(personDetails::getState)).collect(Collectors.toList());
            case 3 -> result = addressBook.stream().sorted(
                    Comparator.comparing(personDetails::getZipCode)).collect(Collectors.toList());
            default -> System.out.println("Please enter valid number");
        }
        Map<String, List<personDetails>> map = new HashMap<>();
        map.put(bookName, result);
        printMap(map);
    }

    //Read or Write the Address Book with Persons Contact into a File using File IO
    public void readAndWriteFile() throws IOException {

        System.out.println("Please select the book");
        String bookName = sc.nextLine();
        addressBook = getAddressBook(bookName);
        StringBuilder sb = new StringBuilder();
        for (personDetails personDetails : addressBook) {
            sb.append(personDetails.toString()).append("\n");
        }

        // attached a file to File Writer
        FileWriter fw = new FileWriter("src/" + bookName + ".txt");

        // read character wise from string and write
        // into FileWriter
        for (int i = 0; i < sb.length(); i++)
            fw.write(sb.charAt(i));

        System.out.println("Writing successful ...........");
        //close the file
        fw.close();

        //To read the file
        System.out.println("Read below data from file");
        int ch;

        // check if File exists or not
        FileReader fr = null;
        try {
            fr = new FileReader("src/" + bookName + ".txt");
        } catch (FileNotFoundException fe) {
            System.out.println("File not found");
        }

        // read from FileReader till the end of file
        while (true) {
            assert fr != null;
            if ((ch = fr.read()) == -1) break;
            System.out.print((char) ch);
        }

        // close the file
        fr.close();
    }


    //Provided person details
    {
        addressBooks = new HashMap<>();
        addressBook = new ArrayList<>();
        List<personDetails> addressBook1 = new ArrayList<>();
        addressBook.add(new personDetails("Vishnu", "Reddy", "Hyderabad", "Hyderabad", "Telangana", 123456, 9553275711L));
        addressBook.add(new personDetails("Sai", "Krishna", "Bengaluru", "Bengaluru", "Karnataka", 789456, 9632145696L));
        addressBook.add(new personDetails("Bharat", "Kalinga", "Pune", "pune", "Maharashtra", 987654, 8978978979L));
        addressBook1.add(new personDetails("Aditya", "Choudhary", "Vizag", "Vizag", "Andhra Pradesh", 456456, 9156874527L));
        addressBook1.add(new personDetails("Tabriz", "Shaikh", "Hyderabad", "Hyderabad", "Telangana", 369258, 9321356488L));
        addressBook1.add(new personDetails("Tilak", "Varma", "Mumbai", "Mumbai", "Maharashtra", 123456, 7741258926L));
        addressBooks.put("A", addressBook);
        addressBooks.put("B", addressBook1);
    }

    public void printMap(Map<String, List<personDetails>> map) {
        for (Map.Entry<String, List<personDetails>> list : map.entrySet()) {
            System.out.println("address book : " + list.getKey());
            for (personDetails details : list.getValue()) {

                System.out.print("First name : " + details.getFirstName() + " , ");
                System.out.print("Last name : " + details.getLastName() + " , ");
                System.out.print("Address : " + details.getAddress() + " , ");
                System.out.print("City : " + details.getCity() + " , ");
                System.out.print("State : " + details.getState() + " , ");
                System.out.print("Zipcode : " + details.getZipCode() + " , ");
                System.out.print("Phone Number : " + details.getPhoneNumber());
                System.out.println();
            }
            System.out.println("---------------------------------------------");
        }
    }

    public void showAddressBooks() {
        printMap(addressBooks);
    }

    private void showAddressBook() {
        System.out.println("Please select the book");
        String bookName = sc.nextLine();
        addressBook = getAddressBook(bookName);
        for (personDetails personDetails : addressBook) {
            System.out.println(personDetails);
        }
    }

    private List<personDetails> getAddressBook(String addressBookName) {
        addressBook = addressBooks.get(addressBookName);
        return addressBook;
    }

    private void addAddressBooks() {
        System.out.println("Enter the book name");
        String addressBookName = sc.nextLine();
        addressBook = new ArrayList<personDetails>();
        addressBooks.put(addressBookName, addressBook);
        printMap(addressBooks);
    }


    public static void main(String[] args) throws IOException {
        System.out.println("Welcome to Address Book");
        boolean isExit = false;
        AddressBookMainUC13 addressBookMain = new AddressBookMainUC13();

        while (!isExit) {
            System.out.println("""
                     Select option
                    1. Add new Address book
                    2. Add new person details
                    3. Edit person details
                    4. Delete Person
                    5. Show Address book
                    6. Show total Address books
                    7. Search person for duplicate entry
                    8. Search Person in a City or State from all AddressBook
                    9. Search person by city or state
                    10. Find count of cities or state
                    11. Sort person alphabetically by person's name
                    12. Sort person by city, state and zip code
                    13. Read or write AddressBook using file .txt
                    14. Exit""");
            int choice = Integer.parseInt(sc.nextLine());
            switch (choice) {
                case 1 -> addressBookMain.addAddressBooks();
                case 2 -> addressBookMain.addPerson();
                case 3 -> addressBookMain.editPerson();
                case 4 -> addressBookMain.deletePerson();
                case 5 -> addressBookMain.showAddressBook();
                case 6 -> addressBookMain.showAddressBooks();
                case 7 -> addressBookMain.searchPerson();
                case 8 -> addressBookMain.searchPersonInMultipleBook();
                case 9 -> addressBookMain.searchPersonByCityOrState();
                case 10 -> addressBookMain.getCountByCityState();
                case 11 -> addressBookMain.sortPersonByName();
                case 12 -> addressBookMain.sortByCityStateZip();
                case 13 -> addressBookMain.readAndWriteFile();
                case 14 -> isExit = true;
                default -> System.out.println("Please enter valid details");
            }
        }
    }
}
