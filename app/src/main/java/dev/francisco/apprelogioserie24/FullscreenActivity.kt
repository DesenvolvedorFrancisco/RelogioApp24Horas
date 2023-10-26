package dev.francisco.apprelogioserie24

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import dev.francisco.apprelogioserie24.databinding.ActivityFullscreenBinding


class FullscreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFullscreenBinding
    private var isChecked = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFullscreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // hide bar status
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }

        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        // Verify and show battery status

        val bateriaReceiver: BroadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                if (intent != null) {
                    val nivel: Int = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0)
                    //Toast.makeText(applicationContext, nivel.toString(), Toast.LENGTH_SHORT)
                // .show()
                    binding.textLevelBattery.text = "${nivel.toString()}%"
                }
            }
        }

        registerReceiver(bateriaReceiver, IntentFilter(Intent.ACTION_BATTERY_CHANGED))

        // Verify if element is checked
        binding.checkBatteryLevel.setOnClickListener {
            if (isChecked) {
                isChecked = false


                // Hide battery level, and show level when clicked

                binding.textLevelBattery.visibility = View.GONE
            } else {
                isChecked = true
                binding.textLevelBattery.visibility = View.VISIBLE
            }
            binding.checkBatteryLevel.isChecked = isChecked

        }
        // Animation of layout
        binding.layoutMenu.animate().translationY(500F)

        binding.imageViewPreference.setOnClickListener {
            binding.layoutMenu.visibility = View.VISIBLE
            binding.layoutMenu.animate().translationY(0F).setDuration(
                resources.getInteger(android.R.integer.config_mediumAnimTime).toLong()
            )
        }

        binding.imageViewClose.setOnClickListener {
            binding.layoutMenu.animate().translationY(binding.layoutMenu.measuredHeight.toFloat())
                .setDuration(
                resources.getInteger(android.R.integer.config_mediumAnimTime).toLong()
            )
        }

    }
}