package com.example.exp.app.mockito;

import android.accounts.Account;
import android.accounts.AccountManager;

/**
 * Created by zhuodong on 3/31/14.
 */
public class AccountController {

    AccountManager mAccountManager;

    Account[] accounts;

    int selectedIndex;

    public static final int INVALID_INDEX = -1;

    public AccountController(AccountManager accountManager) {
        mAccountManager = accountManager;
    }

    public void init() {
        accounts = mAccountManager.getAccounts();
        if (accounts != null) {
            selectedIndex = 0;
        } else {
            selectedIndex = INVALID_INDEX;
        }
    }

    public Account getSelectedAccount() throws InValidAccountException {
        if(selectedIndex == INVALID_INDEX)
            throw new InValidAccountException("No Account");
        return accounts[selectedIndex];
    }

    public Account[] getAccounts() {
        return accounts;
    }

    public void attachAccountsUi(AccountSelectionUi ui) {
        ui.showAccounts(accounts);
    }

    public static class AccountSelectionUi {
        AccountController mAccountController;
        public void showAccounts(Account[] accounts) {
        }

    }

    public static class InValidAccountException extends Exception {

        public InValidAccountException(String detailMessage) {
            super(detailMessage);
        }
    }
}
