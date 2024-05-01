package com.example.pompapp1

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.DatePickerDialog
import android.graphics.Color
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.util.Calendar


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.my_layout)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.layout)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Accessing UI components
        val switcher: SwitchCompat = findViewById(R.id.mySwitcher)
        val title: TextView = findViewById(R.id.title)
        val image: ImageView = findViewById(R.id.image)
        val fadeAnim = AnimationUtils.loadAnimation(applicationContext, R.anim.fade)
        val rotateAnim = AnimationUtils.loadAnimation(applicationContext, R.anim.rotate)
        //------------------------------//


        // Setting the switch listener
        switcher.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                title.setText(R.string.some_text)
                image.alpha = 0.2f
            } else {
                title.setText(R.string.new_title)
                image.alpha = 1.0f
            }
        }
        //------------------------------//

        val animDrawable = image.drawable as AnimationDrawable
        animDrawable.start()
        image.startAnimation(rotateAnim)
        val btn = findViewById<Button>(R.id.my_btn)
        val translationX = ObjectAnimator.ofFloat(btn, "translationX", 0f, -100f).setDuration(1000)
        val translationY = ObjectAnimator.ofFloat(btn, "translationY", 0f, -100f).setDuration(1000)
        val animatorSet = AnimatorSet()
        animatorSet.playTogether(translationX, translationY)
        animatorSet.start()

        //btn
        btn.startAnimation(fadeAnim)
        btn.setOnClickListener {
            val color: Int = Color.rgb(
                (0..256).random(), (0..256).random(), (0..256).random()
            )
            it.setBackgroundColor(color)
        }
        //------------------------------//

        // alert
        val alertBtn = findViewById<Button>(R.id.alert_dialog_btn)
        alertBtn.setOnClickListener {
            val builder: AlertDialog.Builder = AlertDialog.Builder(this)
            builder.apply {
                setTitle(getString(R.string.exit))
                setMessage(getString(R.string.exit_string))
                setCancelable(false)
                setIcon(R.drawable.baseline_exit_to_app_24)
                setPositiveButton("Yes") { _, _ -> finish() }
                setNegativeButton("no") { dialog, _ -> dialog.dismiss() }
            }
            val dialog = builder.create()
            dialog.show()
        }
        //------------------------------//

        // DateBtn
        val dateBtn = findViewById<Button>(R.id.date_dialog_btn)
        dateBtn.setOnClickListener {
            val c = Calendar.getInstance()
            val listener =
                DatePickerDialog.OnDateSetListener { datePicker, year, month, dayOfMonth ->
                    {

                    }
                }
            val dtd = DatePickerDialog(
                this,
                listener,
                c.get(Calendar.YEAR),
                c.get(Calendar.MONTH),
                c.get(Calendar.DAY_OF_MONTH)
            )
            dtd.show()
        }
        //------------------------------//

        // CustomBtn
        val customBtn = findViewById<Button>(R.id.custom_dialog_btn)
        customBtn.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            val dialogView = layoutInflater.inflate(
                R.layout.dialog_layout,
                null
            ) // which parent to inflate? null (no parent// )
            val editText = dialogView.findViewById<EditText>(R.id.input)

            builder.setView(dialogView)
            val dialog = builder.create()
            dialogView.findViewById<Button>(R.id.my_btn).setOnClickListener {
                Toast.makeText(this,editText.text,Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
            dialog.show()
        }
        //------------------------------//
        findViewById<Button>(R.id.celebrate_btn).setOnClickListener {
            val b = BottomSheetDialog(this)
            b.setContentView(R.layout.bottom_sheet_dialog)

            val btn = findViewById<Button>(R.id.get_tickets_btn)
            btn.setOnClickListener {
                Toast.makeText(this,"stam",Toast.LENGTH_SHORT).show()

            }
        }
    }
}