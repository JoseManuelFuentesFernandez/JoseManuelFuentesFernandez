package iestr.jmff.cocha

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import iestr.jmff.cocha.ListaAlquilados.alquilados
import iestr.jmff.cocha.databinding.AlquiladoBinding
import iestr.jmff.cocha.databinding.MisalquileresBinding

class MisAlquileres : AppCompatActivity() {
    val enlace : MisalquileresBinding by lazy{
        MisalquileresBinding.inflate(layoutInflater)
    }

    var anterior:Long=0
    var actual:Long=0
    val lapso:Long=1000
    val mensaje: Toast by lazy{
        Toast.makeText(this,R.string.pulsar_cancelar, Toast.LENGTH_SHORT)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(enlace.root)

        //Cargar los coches alquilados
        actualizarVista()

        //Insertar un coche alquilado
        val extras = intent.extras
        if(extras!=null){
            var nuevo = Alquilado(extras.getString(MODELO).toString(),
                extras.getString(FOTO).toString(),
                extras.getString(PPH).toString(),
                extras.getString(HORAS).toString(),
                extras.getString(PRECIO).toString(),
                extras.getString(PREMIUM).toString())
            alquilados.add(nuevo)
            actualizarVista()
        }
    }

    private fun inflarVistaAlquilado(alquilado: Alquilado): ConstraintLayout {
        val alquiladoBinding = AlquiladoBinding.inflate(LayoutInflater.from(this))

        alquiladoBinding.txvMod.text=alquilado.modelo
        alquiladoBinding.txvHorasEntrada.text=alquilado.horas
        alquiladoBinding.txvPPHEntrada.text=alquilado.precioHora
        alquiladoBinding.txvTotalEntrada.text=alquilado.precioTotal
        alquiladoBinding.txvPremLvl.text=alquilado.premium

        Glide.with(this).load(alquilado.foto).into(alquiladoBinding.imgvw)

        alquiladoBinding.btnCancelar.setOnClickListener{
            if(anterior==0L){
                anterior=System.currentTimeMillis()
                mensaje.show()
            }else{
                actual=System.currentTimeMillis()
                if(actual-anterior<lapso){
                    alquilados.remove(alquilado)
                    mensaje.cancel()
                    Toast.makeText(this,R.string.cancelado,Toast.LENGTH_LONG).show()
                    actualizarVista()
                }else{
                    anterior=actual
                    mensaje.show()
                }
            }
        }

        return alquiladoBinding.root
    }

    private fun actualizarVista(){
        enlace.lista.removeAllViews()
        for (alquilado in alquilados){
            enlace.lista.addView(inflarVistaAlquilado(alquilado))
        }

        //Control del mensaje+boton de no tener coches alquilados
        if(alquilados.isEmpty()) {
            enlace.txvNocars.visibility= View.VISIBLE
            enlace.btnRent.visibility= View.VISIBLE
            enlace.btnRent.setOnClickListener {
                val intento = Intent(this, CochesDisponibles::class.java)
                finish()
                startActivity(intento)
            }
        }else{
            enlace.txvNocars.visibility= View.INVISIBLE
            enlace.btnRent.visibility= View.INVISIBLE
        }
    }

}

object ListaAlquilados {
    val alquilados: MutableList<Alquilado> = mutableListOf()
}

data class Alquilado(
    var modelo: String = "",
    var foto: String = "",
    var precioHora: String = "",
    var horas: String = "",
    var precioTotal:String = "",
    var premium:String = ""
)