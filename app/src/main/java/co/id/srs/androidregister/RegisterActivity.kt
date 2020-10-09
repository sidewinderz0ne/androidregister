package co.id.srs.androidregister

import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_register.*
import org.json.JSONException
import org.json.JSONObject
import java.util.*

class RegisterActivity : AppCompatActivity() {

    val url = "https://test-domain.srs-ssms.com/register.php"
    private var birth = ""
    private var gender = "na"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        lottieRegister.setAnimation("loading_circle.json")
        lottieRegister.loop(true)
        lottieRegister.playAnimation()

        val dateList = (1..31).map { "$it" }
        val yearList = (2021 downTo 1950).map { "$it" }

        mobile.inputType

        spMonth.setItems("", "Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec",)
        spDate.setItems(dateList)
        spYear.setItems(yearList)

        reg.setOnClickListener {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            if (Objects.requireNonNull(imm).isAcceptingText) {
                imm.hideSoftInputFromWindow(Objects.requireNonNull(currentFocus)?.windowToken, 0)
            }
        }

        bt_reg.setOnClickListener {
            birth = if (spDate.text.toString() == "" || spMonth.text.toString() == "" || spYear.text.toString() == ""){
                "na"
            } else {
                "${spMonth.text.toString()}-${spDate.text.toString()}-${spYear.text.toString()}"
            }
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            if (Objects.requireNonNull(imm).isAcceptingText) {
                imm.hideSoftInputFromWindow(Objects.requireNonNull(currentFocus)?.windowToken, 0)
            }
            if (mobile.text.isEmpty()){
                AlertDialogUtility.alertDialog(this, "Mobile Number is empty, please fill the from!", "warning.json")
            }else if (firstname.text.isEmpty()){
                AlertDialogUtility.alertDialog(this, "First name is empty, please fill the from!", "warning.json")
            }else if (lastname.text.isEmpty()){
                AlertDialogUtility.alertDialog(this, "Last name is empty, please fill the from!", "warning.json")
            }else if (email.text.isEmpty()){
                AlertDialogUtility.alertDialog(this, "Email is empty, please fill the from!", "warning.json")
            }
            else if (!android.util.Patterns.PHONE.matcher(mobile.text.toString()).matches()){
                AlertDialogUtility.alertDialog(this, "Please enter valid mobile number!", "warning.json")
            } else {
                try {
                    checkRegister(
                        mobile.text.toString(),
                        firstname.text.toString(),
                        lastname.text.toString(),
                        birth,
                        gender,
                        email.text.toString())
                } catch (e: Exception) {
                    Toasty.error(this, "Terjadi kesalahan, hubungi pengembang", Toasty.LENGTH_LONG, true).show()
                }
            }
        }
    }

    private fun checkRegister(_mobileNum: String, _firstName: String, _lastName: String, _birth: String, _gender: String,
                              _email: String) {
        progressBarHolderRegister.visibility = View.VISIBLE
        val strReq: StringRequest = object : StringRequest(Method.POST, url, Response.Listener { response ->
            try {
                val jObj = JSONObject(response)
                val success = jObj.getInt("success")
                val message = jObj.getString("message")

                // Check for error node in json
                if (success == 1) {
                    progressBarHolderRegister.visibility = View.GONE
                    AlertDialogUtility.alertDialog(this, message, "success.json")
                    tvLogin.visibility = View.VISIBLE
                    bt_login.visibility = View.VISIBLE
                    mobilelogin.visibility = View.VISIBLE
                    emaillogin.visibility = View.VISIBLE
                    textView.visibility = View.GONE
                    mobile.visibility = View.GONE
                    firstname.visibility = View.GONE
                    lastname.visibility = View.GONE
                    dateofbirth.visibility = View.GONE
                    radio_gender.visibility = View.GONE
                    spMonth.visibility = View.GONE
                    spYear.visibility = View.GONE
                    spDate.visibility = View.GONE
                    email.visibility = View.GONE
                    bt_reg.visibility = View.GONE
                } else {
                    progressBarHolderRegister.visibility = View.GONE
                    Toast.makeText(applicationContext,
                        jObj.getString("message"), Toast.LENGTH_LONG).show()
                }
            } catch (e: JSONException) {
                AlertDialogUtility.withSingleAction(this,"TRY AGAIN", "Error: $e", "warning.json") {
                    val intent = Intent(this@RegisterActivity, RegisterActivity::class.java)
                    startActivity(intent)
                }
                progressBarHolderRegister.visibility = View.GONE
                e.printStackTrace()
            }
        }, Response.ErrorListener { error ->
            AlertDialogUtility.withSingleAction(this,"TRY AGAIN", "Nerwork error", "network_error.json") {
                val intent = Intent(this@RegisterActivity, RegisterActivity::class.java)
                startActivity(intent)
            }
            progressBarHolderRegister.visibility = View.GONE
        }) {
            override fun getParams(): Map<String, String> {
                // Posting parameters to login url
                val params: MutableMap<String, String> = HashMap()
                params["mobile"] = _mobileNum
                params["firstname"] = _firstName
                params["lastname"] = _lastName
                params["birth"] = _birth
                params["gender"] = _gender
                params["email"] = _email
                return params
            }
        }
        // Adding request to request queue
        val queue = Volley.newRequestQueue(this)
        queue.add(strReq)
    }

    fun onRadioButtonClicked(view: View) {
        if (view is RadioButton) {
            // Is the button now checked?
            val checked = view.isChecked

            // Check which radio button was clicked
            when (view.getId()) {
                R.id.male ->
                    if (checked) {
                        gender = "male"
                    }
                R.id.female ->
                    if (checked) {
                        gender = "female"
                    }
            }
        }
    }
}