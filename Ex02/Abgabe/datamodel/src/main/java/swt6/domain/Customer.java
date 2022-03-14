package swt6.domain;

import javax.persistence.*;

@Entity
public class Customer {
    @Id @GeneratedValue
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    @Embedded
    @AttributeOverride(name="zipCode", column = @Column(name="shipment_zipCode"))
    @AttributeOverride(name="city", column = @Column(name="shipment_city"))
    @AttributeOverride(name="street", column = @Column(name="shipment_street"))
    private Address shipmentAddress;
    @Embedded
    @AttributeOverride(name="zipCode", column = @Column(name="payment_zipCode"))
    @AttributeOverride(name="city", column = @Column(name="payment_city"))
    @AttributeOverride(name="street", column = @Column(name="payment_street"))
    private Address paymentAddress;

    public Customer(){}

    public Customer(String firstName, String lastName, String email, Address shipmentAddress, Address paymentAddress) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.shipmentAddress = shipmentAddress;
        this.paymentAddress = paymentAddress;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Address getShipmentAddress() {
        return shipmentAddress;
    }

    public void setShipmentAddress(Address shipmentAddress) {
        this.shipmentAddress = shipmentAddress;
    }

    public Address getPaymentAddress() {
        return paymentAddress;
    }

    public void setPaymentAddress(Address paymentAddress) {
        this.paymentAddress = paymentAddress;
    }
}
