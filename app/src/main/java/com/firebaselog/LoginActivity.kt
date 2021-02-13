package com.firebaselog

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.et_email
import kotlinx.android.synthetic.main.activity_login.et_password
import kotlinx.android.synthetic.main.activity_register.*


class LoginActivity : AppCompatActivity() {

        private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

       btn_register.setOnClickListener(){
           startActivity(Intent(this,Register::class.java))
           finish()
       }

        btn_login.setOnClickListener(){
            doLogin()
        }
    }
    private fun doLogin()
    {
        if (et_email.text.toString().isEmpty())
        {
            et_email.error = "Please enter email"
            et_email.requestFocus()
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(et_email.text.toString()).matches()){
            et_email.error = "Please enter valid email"
            et_email.requestFocus()
            return
        }
        if (et_password.text.toString().isEmpty())
        {
            et_password.error = "Please enter password"
            et_password.requestFocus()
            return
        }

        auth.signInWithEmailAndPassword(et_email.text.toString(), et_password.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    Toast.makeText(baseContext, "Log in failed.",
                        Toast.LENGTH_SHORT).show()
                    updateUI(null)
                    // ...
                }

                // ...
            }
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }
    private fun updateUI(currentUser : FirebaseUser?)
    {
        if (currentUser != null)
        {
            startActivity(Intent(this,DashboardActivity::class.java))
        }
        else{
            Toast.makeText(baseContext, "login failed", Toast.LENGTH_SHORT).show()
        }
    }
}