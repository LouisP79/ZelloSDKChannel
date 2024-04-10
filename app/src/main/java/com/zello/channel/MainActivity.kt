package com.zello.channel

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.zello.channel.databinding.ActivityMainBinding
import com.zello.channel.sdk.*

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    private lateinit var session: Session
    private var stream: OutgoingVoiceStream? = null


    override fun onDestroy() {
        session.disconnect()
        super.onDestroy()
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.fab.setOnTouchListener { _, event ->
            if (event!!.action == MotionEvent.ACTION_UP) {
                stream?.stop()
            } else if (event.action == MotionEvent.ACTION_DOWN) {
                stream = session.startVoiceMessage()
            }
            true
        }

        session = Session.Builder(this, "wss://zellowork.io/ws/apolomultimedia", "", "los_olivos").
        setUsername("apolo1", "123456").build()
        session.sessionListener = object: SessionListener {
            override fun onConnectFailed(session: Session, error: SessionConnectError) {
                Log.e("[ZelloListener]","onConnectFailed")
            }

            override fun onConnectStarted(session: Session) {
                Log.e("[ZelloListener]","onConnectStarted")
            }

            override fun onConnectSucceeded(session: Session) {
                Log.e("[ZelloListener]","onConnectSucceeded")
            }

            override fun onDisconnected(session: Session) {
                Log.e("[ZelloListener]","onDisconnected")
            }

            override fun onImageMessage(session: Session, imageInfo: ImageInfo) {
                Log.e("[ZelloListener]","onImageMessage")
            }

            override fun onIncomingVoiceProgress(
                session: Session,
                stream: IncomingVoiceStream,
                positionMs: Int
            ) {
                Log.e("[ZelloListener]","onIncomingVoiceProgress")
            }

            override fun onIncomingVoiceStarted(session: Session, stream: IncomingVoiceStream) {
                Log.e("[ZelloListener]","onIncomingVoiceStarted")
            }

            override fun onIncomingVoiceStopped(session: Session, stream: IncomingVoiceStream) {
                Log.e("[ZelloListener]","onIncomingVoiceStopped")
            }

            override fun onLocationMessage(session: Session, sender: String, location: Location) {
                Log.e("[ZelloListener]","onLocationMessage")
            }

            override fun onOutgoingVoiceError(
                session: Session,
                stream: OutgoingVoiceStream,
                error: OutgoingVoiceStreamError
            ) {
                Log.e("[ZelloListener]","onOutgoingVoiceError")
            }

            override fun onOutgoingVoiceProgress(
                session: Session,
                stream: OutgoingVoiceStream,
                positionMs: Int
            ) {
                Log.e("[ZelloListener]","onOutgoingVoiceProgress")
            }

            override fun onOutgoingVoiceStateChanged(session: Session, stream: OutgoingVoiceStream) {
                Log.e("[ZelloListener]","onOutgoingVoiceStateChanged")
            }

            override fun onTextMessage(session: Session, sender: String, message: String) {
                Log.e("[ZelloListener]","onTextMessage")
            }
        }
        session.connect()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}