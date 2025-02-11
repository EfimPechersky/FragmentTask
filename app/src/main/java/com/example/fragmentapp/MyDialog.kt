package com.example.fragmentapp

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.DialogFragment
import java.util.Arrays

class MyDialog(val ctx: Context): DialogFragment() {
    var town="Nothing"
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        Log.d("mytag", "creating dialog")
        var arr= ctx.resources.getStringArray(R.array.weather_types)
        var choice=0
        return ctx.let { AlertDialog.Builder(it).
        setSingleChoiceItems(ctx.resources.getStringArray(R.array.weather_types),
            0, {dialog, which ->choice=which}).
        setPositiveButton("Ok"){
                dialog, which ->town=arr[choice]
        }.
        create()
        }
        //return super.onCreateDialog(savedInstanceState)
    }
}