package cn.numeron.drawables

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import cn.numeron.drawable.dsl.State
import cn.numeron.drawable.dsl.ripple
import cn.numeron.drawable.dsl.shape
import cn.numeron.drawable.dsl.stateList

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val textView = findViewById<TextView>(R.id.text_view)
        textView.setOnClickListener {
            it.isEnabled = false
            it.isSelected = true
            Log.e("MainActivity", "$it clicked.")
        }
        textView.background = stateList {
            addState(State.PRESSED) {
                shape {
                    corners(8f)
                    solid(Color.parseColor("#FFA500"))
                }
            }
            addState(State.SELECTED + !State.ENABLED) {
                shape {
                    solid(Color.parseColor("#FAF0E6"))
                }
            }
            setDefault {
                shape {
                    corners(8f)
                    solid(Color.parseColor("#FF8C00"))
                }
            }
        }
    }

}