package com.example.passwords

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import com.example.passwords.room.AppDatabase
import com.example.passwords.room.Password
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class PasswordActivity : AppCompatActivity() {
    private lateinit var editTextPassword: EditText
    private lateinit var editTextSite: EditText
    private lateinit var editTextDiscricao: EditText
    private var loadedPass: Password? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password)


        val loadedPassData = intent.getIntExtra("password", 0)

            GlobalScope.launch {
                loadedPass = AppDatabase(this@PasswordActivity).passwordDao().getOnePassword(
                    loadedPassData
                )

                editTextPassword.setText(loadedPass?.password)
                editTextSite.setText(loadedPass?.site)
                editTextDiscricao.setText(loadedPass?.descricao)
        }

        editTextPassword = findViewById(R.id.editTextPassword)
        editTextSite = findViewById(R.id.editTextSite)
        editTextDiscricao = findViewById(R.id.editTextDiscricao)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        return true
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_add -> savePass()
            R.id.action_delete -> deletePass()
        }
        return true
    }

    override fun onBackPressed() {
        cancelPass()
    }

    private fun cancelPass() {
        val dialogCancel =
            AlertDialog.Builder(this)
                .setTitle("discard changes...")
                .setMessage("are you sure you do not want to save changes to this password?")
                .setPositiveButton(
                    "YES"
                ) { _, _ -> finish() }
                .setNegativeButton("NO", null)
        dialogCancel.show()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun savePass() {
        val password = editTextPassword.text.toString()
        val site = editTextSite.text.toString()
        val descricao = editTextDiscricao.text.toString()

        if (password.isEmpty()) {
            Toast.makeText(
                this, "please enter a password!"
                , Toast.LENGTH_SHORT
            ).show()
            return
        }
        if (site.isEmpty()) {
            Toast.makeText(
                this, "please enter a site!"
                , Toast.LENGTH_SHORT
            ).show()
            return
        }
        if (descricao.isEmpty()) {
            Toast.makeText(
                this, "please enter a description!"
                , Toast.LENGTH_SHORT
            ).show()
            return
        }
        val current = LocalDateTime.now()

        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
        val formatted = current.format(formatter)

        val temPassword = Password(formatted, password, site, descricao)


        GlobalScope.launch {
            if (loadedPass == null) {
                AppDatabase(this@PasswordActivity).passwordDao().insertPassword(temPassword)

            } else {
                temPassword.id = loadedPass!!.id
                AppDatabase(this@PasswordActivity).passwordDao().updatePassword(temPassword)
            }
            finish()
        }
    }

    private fun deletePass() {
        if (loadedPass == null)
            finish()
        else
        {
            val dialog = AlertDialog.Builder(this).apply {
                setTitle("Delete")
                    .setMessage("You are about to delete " + editTextPassword.text.toString() + ", Are you sure?")
                    .setPositiveButton("Yes") { _, _ ->
                        GlobalScope.launch {
                            context.let {
                                AppDatabase(it).passwordDao().deletePassword(loadedPass!!)
                            }
                        }
                        finish()
                    }
                    .setNegativeButton("No", null)
                    .setCancelable(false)
            }

            dialog.show()
        }
    }}