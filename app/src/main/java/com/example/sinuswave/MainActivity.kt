package com.example.sinuswave

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.AmbientAnimationClock
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.unit.dp
import com.example.sinuswave.ui.SinusWaveTheme
import kotlin.math.sin

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SinusWaveTheme(darkTheme = true) {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    App()
                }
            }
        }
    }
}

@Composable
fun App(){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize(1f)
    ) {
        SinusWave()
    }
}

@Composable
fun SinusWave(){
    val amplitude = 40.0
    val length = remember { 2.0 / 100.0 }
    val speed = remember {1f / 1000f } // 1 per second

    val t = remember { mutableStateOf(0f)}

    val clock = AmbientAnimationClock.current
    val observer = object : AnimationClockObserver{
        override fun onAnimationFrame(frameTimeMillis: Long) {
            t.value = frameTimeMillis * speed
        }
    }

    onActive(callback = {
        clock.subscribe(observer = observer)
    })
    onDispose(callback = {
        clock.unsubscribe(observer = observer)
    })

//    DrawSinWave(t.value, length, amplitude)



    Canvas(modifier = Modifier.fillMaxWidth(1f).height(250.dp).background(Color.White)) {
        val path = drawSinWave(size, length, amplitude, t.value,)
        drawPath(
            path = path,
            color = Color.Green,
            style = Stroke(width = 4.dp.toPx())
        )
    }
}

fun drawSinWave(size: Size, length: Double, amplitude: Double, frequency: Float) : Path {
    var x = 0.0
    var y = 0.0

    return Path().apply {
//            reset()

        moveTo(0f, size.height / 2)

        while(x < size.width){
            y = ((size.height / 2) + sin(x * length + frequency) * amplitude)
            lineTo(x.toFloat(), y.toFloat())
            x += 1
        }
    }
}

//@Composable
//fun DrawSinWave(frequency: Float, length: Double, amplitude: Double) {
//    var x = 0.0
//    var y = 0.0
//
//    Canvas(modifier = Modifier.fillMaxWidth(1f).height(250.dp).background(Color.White)){
//
//        val path = Path().apply {
////            reset()
//
//            moveTo(0f, size.height / 2)
//
//            while(x < size.width){
//                y = ((size.height / 2) + sin(x * length + frequency) * amplitude)
//                lineTo(x.toFloat(), y.toFloat())
//                x += 1
//            }
//        }
//
//        drawPath(path = path, color = Color.Green, style = Stroke(width = 4.dp.toPx()))
//    }
//}


