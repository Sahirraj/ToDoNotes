package view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import utils.AppConstant
import utils.prefConstant
import utils.prefConstant.IS_LOGGED_IN

class LoginActivity : AppCompatActivity() {
    lateinit  var editTextFullName: EditText
    lateinit  var editTextUserName: EditText
    lateinit var buttonLogin: Button
    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        editTextFullName = findViewById(R.id.editTextFullName)
        editTextUserName = findViewById(R.id.editTextUserName)
        buttonLogin = findViewById(R.id.buttonLogin)
        setupSharedPreferences()
        val clickAction =object: View.OnClickListener {
            override fun onClick(v: View?) {
                val fullName = editTextFullName.text.toString()
                val userName = editTextUserName.text.toString()
                if (fullName.isNotEmpty() && userName.isNotEmpty()) {
                    val intent = Intent(this@LoginActivity, MyNotesActivity::class.java)
                    intent.putExtra(AppConstant.FULL_NAME, fullName)
                    startActivity(intent)
                    saveLoginStatus()
                    saveFullName(fullName)
                } else {
                    Toast.makeText(this@LoginActivity, "Please Enter Valid Fullname And Username", Toast.LENGTH_SHORT).show()
                }

            }

        }
        buttonLogin.setOnClickListener(clickAction)
    }

    private fun saveFullName(fullName: String) {
        editor = sharedPreferences.edit()
        editor.putString(prefConstant.FULL_NAME, fullName)
        editor.apply()
    }

    private fun saveLoginStatus() {
        editor = sharedPreferences.edit()
        editor.putBoolean(IS_LOGGED_IN, true)
        editor.apply()
    }

    private fun setupSharedPreferences() {
        sharedPreferences = getSharedPreferences(prefConstant.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)
    }
}