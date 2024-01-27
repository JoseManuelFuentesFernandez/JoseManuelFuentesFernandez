package iestr.jmff.cocha
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import iestr.jmff.cocha.databinding.CocheBinding
import iestr.jmff.cocha.databinding.CochesdisponiblesBinding
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory

class CochesDisponibles : AppCompatActivity() {
    private val enlace: CochesdisponiblesBinding by lazy {
        CochesdisponiblesBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(enlace.root)
    }

    override fun onStart() {
        super.onStart()
        actualizaCoches()
    }

    fun actualizaCoches(){
        enlace.lista.removeAllViews()
        val coches = parsearDatosXML()

        for (coche in coches) {
            val cocheView = inflarVistaCoche(coche)
            enlace.lista.addView(cocheView)
        }
    }

    private fun parsearDatosXML(): List<Coche> {
        val coches = mutableListOf<Coche>()

        try {
            val factory = XmlPullParserFactory.newInstance()
            factory.isNamespaceAware = true
            val parser = factory.newPullParser()

            val inputStream = resources.openRawResource(R.raw.carcontent)
            parser.setInput(inputStream, null)

            var eventType = parser.eventType
            var coche: Coche? = null

            while (eventType != XmlPullParser.END_DOCUMENT) {
                when (eventType) {
                    XmlPullParser.START_TAG -> {
                        when (parser.name) {
                            "item" -> {
                                coche = Coche()
                            }
                            "modelo" -> coche?.modelo = parser.nextText()
                            "foto" -> coche?.foto = parser.nextText()
                            "ano" -> coche?.ano = parser.nextText()
                            "plazas" -> coche?.plazas = parser.nextText()
                            "potencia" -> coche?.potencia = parser.nextText()
                            "precioHora" -> coche?.precioHora = parser.nextText()
                            "motor" -> coche?.motor = parser.nextText()
                        }
                    }
                    XmlPullParser.END_TAG -> {
                        if (parser.name == "item") {
                            coches.add(coche!!)
                        }
                    }
                }
                eventType = parser.next()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return coches
    }

    private fun inflarVistaCoche(coche: Coche): ConstraintLayout {
        val cocheBinding = CocheBinding.inflate(LayoutInflater.from(this))

        cocheBinding.txvModelo2.text = coche.modelo
        cocheBinding.txvAno2.text = coche.ano
        cocheBinding.txvPlazas2.text = coche.plazas
        cocheBinding.txvPotencia2.text = coche.potencia
        cocheBinding.txvMotor2.text = coche.motor
        cocheBinding.txvPH2.text = coche.precioHora
        Glide.with(this).load(coche.foto).into(cocheBinding.imgvw)

        cocheBinding.btnAlq.setOnClickListener{
            val intento= Intent(this, TramiteAlquiler::class.java)
            intento.putExtra(MODELO,coche.modelo)
            intento.putExtra(FOTO,coche.foto)
            intento.putExtra(PPH,coche.precioHora)

            startActivity(intento)
        }

        return cocheBinding.root
    }
}

data class Coche(
    var modelo: String = "",
    var foto: String = "",
    var ano: String = "",
    var plazas: String = "",
    var potencia: String = "",
    var precioHora: String = "",
    var motor: String = ""
)