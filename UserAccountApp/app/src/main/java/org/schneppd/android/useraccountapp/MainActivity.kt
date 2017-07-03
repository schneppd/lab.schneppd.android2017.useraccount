package org.schneppd.android.useraccountapp

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import android.view.Menu
import android.view.MenuItem
import android.accounts.Account
import android.accounts.AccountManager
import android.content.Context
import android.content.Intent

import com.google.android.gms.common.AccountPicker


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        val fab = findViewById(R.id.fab) as FloatingActionButton
        fab.setOnClickListener { view -> run{
            //normal account access
            val manager = getSystemService(Context.ACCOUNT_SERVICE) as AccountManager
            val accounts = manager.accounts
            var message = ""
            for(account in accounts){
                message += " Account: " + account.name + " type " + account.type
            }
            //AccountPicker account access
            requestAccountFromAccountPicker()

            Snackbar.make(view, "Test complited" + message, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            }

        }
    }

    fun requestAccountFromAccountPicker() {
        //ask for user's account to select
        val intent: Intent = AccountPicker.newChooseAccountIntent(null, null, arrayOf("com.google"), false, null, null, null, null)
        startActivityForResult(intent, REQUEST_CODE_PICK_ACCOUNT)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId


        if (id == R.id.action_settings) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override protected  fun onActivityResult(requestCode:Int, resultCode:Int,  data:Intent){
        if (requestCode == REQUEST_CODE_PICK_ACCOUNT && resultCode == RESULT_OK){
            accountType = data.getStringExtra(AccountManager.KEY_ACCOUNT_TYPE)
            accountName = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME)
            val message = accountName + " " + accountType;
            val fab = findViewById(R.id.fab) as FloatingActionButton
            Snackbar.make(fab, "Account selected:" + message, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }

    companion object Static {
        var accountName:String? = null
        var accountType:String? = null
        val REQUEST_CODE_PICK_ACCOUNT = 101
    }
}
