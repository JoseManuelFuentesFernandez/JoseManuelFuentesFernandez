package iestr.jmff.cocha

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import iestr.jmff.cocha.databinding.LoginBinding

class Login : AppCompatActivity(){
    val enlace : LoginBinding by lazy {
        LoginBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(enlace.root)
    }

    override fun onStart() {
        super.onStart()
        //Boton login con control de entrada de usuario y contrasena (sin usuario ni contrasena definidos)
        enlace.btnLogIn.setOnClickListener{
            if(enlace.txtUser.text.isNullOrBlank() || enlace.txtPwd.text.isNullOrBlank()){
                Toast.makeText(this, this.getString(R.string.mensaje_rellena_campos) ,Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(this, String.format("%s %s",this.getString(R.string.bienvenido),enlace.txtUser.text),Toast.LENGTH_SHORT).show()
                val intent = Intent(this, Menu::class.java)
                startActivity(intent)
            }
        }
    }
}