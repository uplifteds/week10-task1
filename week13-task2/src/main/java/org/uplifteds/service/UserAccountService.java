package org.uplifteds.service;

import org.uplifteds.entity.UserAccount;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

//Add DAO and service objects for new entity.
public class UserAccountService implements BookingFacade {
    public static List<UserAccount> listOfUserAccounts = new ArrayList<>();
    private UserAccount userAccount;

    public UserAccountService(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    public List<String> doService (){
        String userAccountServiceOutput = "\nUserAccount is created: " + userAccount.toString();
        System.out.println(userAccountServiceOutput);

        listOfUserAccounts.add(userAccount);

        List<String> listOfServiceOutput = new CopyOnWriteArrayList<>();
        listOfServiceOutput.add(userAccountServiceOutput);
        return listOfServiceOutput;
    }

    //Add methods for refilling the account
    private void refillBalance(int moneyAdded) {
        int balanceBeforeRefill = userAccount.getMoney();
        userAccount.setMoney(balanceBeforeRefill + moneyAdded);
    }

}
