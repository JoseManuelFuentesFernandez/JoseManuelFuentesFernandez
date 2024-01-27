package iestr.jmff.cocha

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import android.widget.VideoView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import iestr.jmff.cocha.databinding.MenuBinding

class Menu : AppCompatActivity() {
    private val enlace: MenuBinding by lazy {
        MenuBinding.inflate(layoutInflater)
    }

    //Control de persistencia del video
    private lateinit var videoView: VideoView
    private var ultimaPosicion: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(enlace.root)
    }

    override fun onStart() {
        super.onStart()
        // Video
        videoView = findViewById(enlace.vdvw1.id)
        val videoPath = "android.resource://" + packageName + "/" + R.raw.video
        val uri = Uri.parse(videoPath)
        videoView.setVideoURI(uri)
        videoView.setOnPreparedListener { mediaPlayer ->
            mediaPlayer.isLooping = true //Reproducido en bucle
            if (ultimaPosicion > 0) {
                mediaPlayer.seekTo(ultimaPosicion) //Sistema de persistencia de visualización del vídeo
            } else {
                videoView.start()
            }
        }

        // Controlar la salida (pide verificar apareciendo un boton de salida que estaba invisible)
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                enlace.btnLogOut.visibility = View.VISIBLE
                Toast.makeText(this@Menu, getString(R.string.salida_logout), Toast.LENGTH_SHORT).show()
            }
        })
        enlace.btnLogOut.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            finishAffinity() //Para en el login no volver a entrar en el menu al pulsar 'back'
            startActivity(intent)
        }

        // Boton Noticias
        enlace.btnNoticias.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.url_noticias))) //URL que cambia con el lenguaje que tengamos en el sistema
            startActivity(intent)
        }

        //Boton Coches Disponibles
        enlace.btnCoDi.setOnClickListener{
            val intent = Intent(this, CochesDisponibles::class.java)
            startActivity(intent)
        }

        //Boton Mis Alquileres
        enlace.btnMisAl.setOnClickListener{
            val intent = Intent(this, MisAlquileres::class.java)
            startActivity(intent)
        }
    }

    override fun onPause() {
        super.onPause()
        ultimaPosicion = videoView.currentPosition //Guarda el segundo en el que deja el vídeo
        videoView.pause()
    }

    override fun onResume() {
        super.onResume()
        videoView.start()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("ultimaPosicion", ultimaPosicion) //Sale guardando el segundo marcado previamente en onPause
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        ultimaPosicion = savedInstanceState.getInt("ultimaPosicion", 0) //Al volver a entrar lo recupera
    }
}
