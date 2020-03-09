package org.mimi.clibrary.Beans.account;

import org.joda.time.LocalDate;

public abstract class AccountBean {

    private Integer id;
    private String accountOwnerFirstname;
    private String accountOwnerLastname;
    private String accountOwnerPass;
    private String accountOwnerEmail;
    private String accountOwnerPhoneNb;
    private boolean activeAccount;
    private LocalDate registrationDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccountOwnerFirstname() {
        return accountOwnerFirstname;
    }

    public void setAccountOwnerFirstname(String accountOwnerFirstname) {
        this.accountOwnerFirstname = accountOwnerFirstname;
    }

    public String getAccountOwnerLastname() {
        return accountOwnerLastname;
    }

    public void setAccountOwnerLastname(String accountOwnerLastname) {
        this.accountOwnerLastname = accountOwnerLastname;
    }

    public String getAccountOwnerPass() {
        return accountOwnerPass;
    }

    public void setAccountOwnerPass(String accountOwnerPass) {
        this.accountOwnerPass = accountOwnerPass;
    }

    public String getAccountOwnerEmail() {
        return accountOwnerEmail;
    }

    public void setAccountOwnerEmail(String accountOwnerEmail) {
        this.accountOwnerEmail = accountOwnerEmail;
    }

    public String getAccountOwnerPhoneNb() {
        return accountOwnerPhoneNb;
    }

    public void setAccountOwnerPhoneNb(String accountOwnerPhoneNb) {
        this.accountOwnerPhoneNb = accountOwnerPhoneNb;
    }

    public boolean isActiveAccount() {
        return activeAccount;
    }

    public void setActiveAccount(boolean activeAccount) {
        this.activeAccount = activeAccount;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }
}
