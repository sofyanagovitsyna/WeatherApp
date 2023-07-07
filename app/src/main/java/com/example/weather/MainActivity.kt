package com.example.weather

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.weather.All.Application2
import com.example.weather.All.HourFragment
import com.example.weather.All.Main
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    private lateinit var pLauncher: ActivityResultLauncher<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
                supportFragmentManager.beginTransaction().replace(R.id.Place, Main())
                    .commit()

            if (ContextCompat.checkSelfPermission(Application2.applicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_DENIED) {
                permissionListener()
                pLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }

//checkPermission()
        }
    }

    private fun permissionListener(){
        pLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()){
            // Toast.makeText(activity, "Permission is $it", Toast.LENGTH_LONG).show()
            if (it==false){
                DialogManager2.locationSettingsDialog(this, object : DialogManager2.Listener{
                    override fun onClick(i: Int) {
                        if (i==1)
                        {

                            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                            intent.data = Uri.parse("package:" + Application2.applicationContext().packageName)
                            startActivity(intent)
                        }

                    }
                })
            }
        }
    }




}