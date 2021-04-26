package com.alexlianardo.github2

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_notification.*

class NotificationActivity : AppCompatActivity(){
    companion object {
        const val PREFS_NAME = "setting_pref"
        private const val DAILY = "daily"
    }

    private lateinit var notifReceiver: NotificationConfig
    private lateinit var mSharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)

        notifReceiver = NotificationConfig()
        mSharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

        notification_switch.isChecked = mSharedPreferences.getBoolean(DAILY, false)
        notification_switch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                notifReceiver.setDailyAlarm(
                    applicationContext,
                    NotificationConfig.TYPE_DAILY,
                    getString(R.string.remind)
                )
            } else {
                notifReceiver.cancelAlarm(applicationContext)
            }
            saveChange(isChecked)
        }
    }

    private fun saveChange(value: Boolean) {
        val editor = mSharedPreferences.edit()
        editor.putBoolean(DAILY, value)
        editor.apply()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }

}