package co.id.srs.androidregister

import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
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

    val url = ""
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
                    CURL(mobile.text.toString().replace(" ", "%20"), firstname.text.toString().replace(" ", "%20"), lastname.text.toString().replace(" ", "%20"), birth.replace(" ", "%20"), gender.replace(" ", ""), email.text.toString().replace(" ", "")).execute()
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

                // Check for error node in json
                if (success == 1) {
                    progressBarHolderRegister.visibility = View.GONE
                    AlertDialogUtility.alertDialog(this, "Data telah masuk, tunggu konfirmasi dari admin kami", "success.json")
                    mobile.setText("")
                    TODO("Grayed out")

                } else {
                    progressBarHolderRegister.visibility = View.GONE
                    Toast.makeText(applicationContext,
                        jObj.getString("message"), Toast.LENGTH_LONG).show()
                }
            } catch (e: JSONException) {
                AlertDialogUtility.withSingleAction(this,"Ulang", "Error: $e", "warning.json") {
                    val intent = Intent(this@RegisterActivity, RegisterActivity::class.java)
                    startActivity(intent)
                }
                progressBarHolderRegister.visibility = View.GONE
                e.printStackTrace()
            }
        }, Response.ErrorListener { error ->
            AlertDialogUtility.withSingleAction(this,"Ulang", "Registrasi gagal, gunakan jaringan yang stabil untuk registrasi!", "network_error.json") {
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

    class CURL(
        etNamaLengkap: String,
        etDepartemen: String,
        etLokasiKerja: String,
        etJabatan: String,
        etNoHP: String,
        etEmail: String
    ) : AsyncTask<Void, Void, String>() {

        val namaLengkap = "Nama=$etNamaLengkap"
        val departemen = "%0ADepartemen=$etDepartemen"
        val lokasi_kerja = "%0ALokasiKerja=$etLokasiKerja"
        val jabatan = "%0AJabatan=$etJabatan"
        val noHP = "%0ANoHP=$etNoHP"
        val email = "%0AEmail=$etEmail"
        var returnValue: Int? = null
        val baseURL = "https://api.telegram.org/bot1115531097:AAHOgChELkW3Kk2PtC1VvOt4BbhlZYju8l8/sendMessage?parse_mode=markdown&chat_id=-397663601&text="
        val command = "curl POST $baseURL$namaLengkap$departemen$lokasi_kerja$jabatan$noHP$email"
        val process: Process = Runtime.getRuntime().exec(command)

        override fun doInBackground(vararg params: Void?): String? {
            process.inputStream.read()
            process.waitFor()
            return null
        }

        override fun onPreExecute() {
            super.onPreExecute()
        }

        override fun onPostExecute(result: String?) {
            process.inputStream.close()
            returnValue = process.exitValue()
            super.onPostExecute(result)
        }
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