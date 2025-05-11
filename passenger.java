package Defense;

public class passenger {
    private String name;
    private String age;
    private String contactNumber;
    private String passportNumber;

    //Internation (Has passport)
    public passenger(String name, String age, String contactNumber, String passportNumber) {
        this.name = name;
        this.age = age;
        this.contactNumber = contactNumber;
        this.passportNumber = passportNumber;
    }
    
    // Domestic (no passport)
    public passenger(String name, String age, String contactNumber) {
        this.name = name;
        this.age = age;
        this.contactNumber = contactNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }
}