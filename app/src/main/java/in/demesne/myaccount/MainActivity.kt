package `in`.demesne.myaccount

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import `in`.demesne.myaccount.features.auth.TokenManager
import `in`.demesne.myaccount.features.login.LoginActivity
import `in`.demesne.myaccount.features.account.MyAccountActivity
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var tokenManager: TokenManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Check if user is already logged in
        if (tokenManager.isLoggedIn()) {
            startActivity(Intent(this, MyAccountActivity::class.java))
        } else {
            startActivity(Intent(this, LoginActivity::class.java))
        }
        finish()
    }
}