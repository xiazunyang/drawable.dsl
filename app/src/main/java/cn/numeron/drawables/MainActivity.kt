package cn.numeron.drawables

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import cn.numeron.color.colorSelector
import cn.numeron.drawable.selector
import cn.numeron.drawable.shape
import cn.numeron.drawable.State

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
        colorSelector {
            addState(!State.ENABLED, Color.GRAY)
            addState(State.PRESSED, Color.WHITE)
            defState(Color.BLACK)
        }
        selector {
            addState(State.PRESSED) {
                shape {
                    corners(8f)
                    solid(Color.parseColor("#FFA500"))
                }
            }
            addState(State.SELECTED + State.ENABLED + !State.ENABLED) {
                shape {
                    corners(8f)
                    solid(Color.parseColor("#FAF0E6"))
                }
            }
            defState {
                shape {
                    corners(8f)
                    solid(Color.parseColor("#FF8C00"))
                }
            }
        }
    }

}