package view

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import utils.AppConstant

class DetailActivity : AppCompatActivity() {
    lateinit var textViewTitle: TextView
    lateinit var textViewDescription: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        bindViews()
        setupIntentData()
    }

    private fun setupIntentData() {
        val intent = intent  //same as getIntent()
        val title = intent.getStringExtra(AppConstant.TITLE)
        val description = intent.getStringExtra(AppConstant.DESCRIPTION)
        // to setText
        textViewTitle.text = title
        textViewDescription.text = description
    }

    private fun bindViews() {
        textViewTitle = findViewById(R.id.textViewTitle)
        textViewDescription = findViewById(R.id.textViewDescription)
    }
}