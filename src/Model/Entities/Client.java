package Model.Entities;

/**
 * Entity for table Client
 */
public class Client {

    private String idPassport;
    private String name;
    private String residenceAddress;
    private String birthDate;

    public String getIdPassport() {
        return idPassport;
    }

    public void setIdPassport(String idPassport) {
        this.idPassport = idPassport;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getResidenceAddress() {
        return residenceAddress;
    }

    public void setResidenceAddress(String residenceAddress) {
        this.residenceAddress = residenceAddress;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }
}
