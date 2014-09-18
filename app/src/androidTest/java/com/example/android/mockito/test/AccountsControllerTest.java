package com.example.android.mockito.test;

import com.example.exp.app.mockito.AccountController;

import org.mockito.AdditionalMatchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.test.AndroidTestCase;

import static com.example.exp.app.mockito.AccountController.AccountSelectionUi;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by zhuodong on 3/31/14.
 */
public class AccountsControllerTest extends AndroidTestCase{

    static final String ACCOUNT_TYPE = "com.example.android";
    static final Account ACCOUNT_1 = new Account("account1@gmail.com",ACCOUNT_TYPE);
    static final Account ACCOUNT_2 = new Account("account2@gmail.com",ACCOUNT_TYPE);
    static final Account[] TWO_ACCOUNTS = {ACCOUNT_1, ACCOUNT_2};


    private AccountController mAccountController;

    @Mock private AccountManager mMockAccountManager;
    @Mock private AccountSelectionUi mAccountSelectionUi;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        setupDexmaker();
        MockitoAnnotations.initMocks(this);
        mAccountController = new AccountController(mMockAccountManager);
    }

    public void testInit_twoAccountsFirstAccountsSelected()
            throws AccountController.InValidAccountException {
        when(mMockAccountManager.getAccounts()).thenReturn(TWO_ACCOUNTS);
        mAccountController.init();

        assertEquals(ACCOUNT_1,mAccountController.getSelectedAccount());
    }

    public void testInit_showAccountsUi() {
        when(mMockAccountManager.getAccounts()).thenReturn(TWO_ACCOUNTS);
        mAccountController.init();
        mAccountController.attachAccountsUi(mAccountSelectionUi);

        verify(mAccountSelectionUi).showAccounts(AdditionalMatchers.aryEq(TWO_ACCOUNTS));
    }

    /**
     * Workaround for Mockito and JB-MR2 incompatibility to avoid
     * java.lang.IllegalArgumentException: dexcache == null
     *
     * @see <a href="https://code.google.com/p/dexmaker/issues/detail?id=2">
     *     https://code.google.com/p/dexmaker/issues/detail?id=2</a>
     */
    private void setupDexmaker() {
        // Explicitly set the Dexmaker cache, so tests that use mockito work
        final String dexCache = getContext().getCacheDir().getPath();
        System.setProperty("dexmaker.dexcache", dexCache);
    }

}
