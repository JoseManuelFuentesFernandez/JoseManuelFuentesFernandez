package iestr.jmff.cocha

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import iestr.jmff.cocha.databinding.TramitealquilerBinding

class TramiteAlquiler : AppCompatActivity() {
    val enlace : TramitealquilerBinding by lazy {
        TramitealquilerBinding.inflate(layoutInflater)
    }
    var anterior:Long=0
    var actual:Long=0
    val lapso:Long=1000
    val mensaje: Toast by lazy{
        Toast.makeText(this,R.string.back_again, Toast.LENGTH_SHORT)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(enlace.root)

        //Doble 'back' necesario para salir
        onBackPressedDispatcher.addCallback(this,object: OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                if(anterior==0L){
                    anterior=System.currentTimeMillis()
                    mensaje.show()
                }else{
                    actual=System.currentTimeMillis()
                    if(actual-anterior<lapso){
                        mensaje.cancel()
                        finish()
                    }else{
                        anterior=actual
                        mensaje.show()
                    }
                }
            }
        })
    }

    override fun onStart() {
        super.onStart()
        val extra = intent.extras
        enlace.txvModelo.text = extra?.getString(MODELO)
        enlace.txvPPH2.text = extra?.getString(PPH)
        Glide.with(this).load(extra?.getString(FOTO)).into(enlace.imageView2)

        //Sistema de opciones premium con un menu con cambio de visibilidad
        enlace.switch1.setOnClickListener{
            if(enlace.switch1.isChecked){
                enlace.btng.check(0)
                enlace.btng.visibility= View.VISIBLE
            }else{
                enlace.btng.check(0)
                enlace.btng.visibility= View.INVISIBLE
            }
        }

        //Calcular precio final
        var pulsado=false
        enlace.btnCalc.setOnClickListener{
            if(enlace.txvHoras.text.toString().equals("")||enlace.txvNhoras.text.toString().equals("")){
                pulsado=false
                Toast.makeText(this,R.string.mensaje_rellena_campos,Toast.LENGTH_SHORT).show()
            }else{
                if(enlace.txvNhoras.text.toString().equals("0")){
                    Toast.makeText(this,R.string.cero_no,Toast.LENGTH_SHORT).show()
                    pulsado=false
                }else{
                    enlace.txvTotal.text = enlace.txvNhoras.text.toString().toInt().times(enlace.txvPPH2.text.toString().toInt()).toString()
                    enlace.txvEuro2.visibility=View.VISIBLE
                    pulsado=true
                }
            }
        }

        //Alquilar
        enlace.btnAlquilar.setOnClickListener{
            if(enlace.txvHoras.text.toString().equals("")||enlace.txvNhoras.text.toString().equals("")){
                Toast.makeText(this,R.string.mensaje_boton_calc,Toast.LENGTH_SHORT).show()
            }else{
                if(pulsado){
                    val intento= Intent(this, MisAlquileres::class.java)
                    intento.putExtra(MODELO,extra?.getString(MODELO))
                    intento.putExtra(FOTO,extra?.getString(FOTO))
                    intento.putExtra(PPH,enlace.txvPPH2.text.toString())
                    intento.putExtra(HORAS,enlace.txvNhoras.text.toString())
                    intento.putExtra(PRECIO,enlace.txvTotal.text.toString())
                    if(enlace.rbtn1.isChecked){
                        intento.putExtra(PREMIUM,enlace.rbtn1.text.toString())
                    }else if(enlace.rbtn2.isChecked){
                        intento.putExtra(PREMIUM,enlace.rbtn2.text.toString())
                    }else if(enlace.rbtn3.isChecked){
                        intento.putExtra(PREMIUM,enlace.rbtn3.text.toString())
                    }else{
                        intento.putExtra(PREMIUM,getString(R.string.no))
                    }
                    finish()
                    startActivity(intento)
                }else{
                    Toast.makeText(this,R.string.mensaje_rellena_campos,Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}