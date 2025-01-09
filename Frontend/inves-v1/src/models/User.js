export class User {
  constructor(id, title, firstName, lastName, username, password, address, phone, email, iban, balance) {
    this.id = id;
    this.title = title;
    this.firstName = firstName;
    this.lastName = lastName;
    this.username = username;
    this.password = password;
    this.address = address;
    this.phone = phone;
    this.email = email;
    this.iban = iban;
    this.balance = balance; // Nowe pole
  }
}
