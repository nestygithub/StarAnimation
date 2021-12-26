package com.chiki.staranimation

import android.animation.*
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.LinearInterpolator
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.DataBindingUtil
import com.chiki.staranimation.databinding.ActivityMainBinding
import kotlinx.coroutines.delay

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        var star = findViewById<ImageView>(R.id.star)
        binding.rotateButton.setOnClickListener {
            firstRotate()
        }
        binding.translateButton.setOnClickListener {
            translater()
        }
        binding.scaleButton.setOnClickListener {
            scaler()
        }
        binding.fadeButton.setOnClickListener {
            fader()
        }
        binding.colorizeButton.setOnClickListener {
            colorizer()
        }
        binding.showerButton.setOnClickListener {
            repeat(100){
                shower()
            }
        }
    }

    private fun firstRotate(){
        val animator = ObjectAnimator.ofFloat(binding.star, View.ROTATION, -360f, 0f)
        animator.duration = 1000
        animator.disableViewDuringAnimation(binding.rotateButton)
        animator.start()
    }
    private fun translater() {
        val animator = ObjectAnimator.ofFloat(binding.star, View.TRANSLATION_Y, 200f)
        animator.repeatCount = 1
        animator.repeatMode = ObjectAnimator.REVERSE
        animator.disableViewDuringAnimation(binding.translateButton)
        animator.start()
    }
    private fun scaler() {
        val scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 4f)
        val scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 4f)
        val animator = ObjectAnimator.ofPropertyValuesHolder(binding.star, scaleX, scaleY)
        animator.repeatCount = 1
        animator.repeatMode = ObjectAnimator.REVERSE
        animator.disableViewDuringAnimation(binding.scaleButton)
        animator.start()
    }
    private fun fader() {
        val animator = ObjectAnimator.ofFloat(binding.star, View.ALPHA, 0f)
        animator.repeatCount = 1
        animator.repeatMode = ObjectAnimator.REVERSE
        animator.disableViewDuringAnimation(binding.fadeButton)
        animator.start()
    }
    private fun colorizer() {
        val animator = ObjectAnimator.ofArgb(binding.star,"backgroundColor", Color.BLACK, Color.RED)
        animator.duration = 500
        animator.repeatCount = 1
        animator.repeatMode = ObjectAnimator.REVERSE
        animator.disableViewDuringAnimation(binding.colorizeButton)
        animator.start()
    }
    private fun shower() {
        val container = binding.star.parent as ViewGroup
        val containerW = container.width
        val containerH = container.height
        var starW: Float = binding.star.width.toFloat()
        var starH: Float = binding.star.height.toFloat()

        val newStar = AppCompatImageView(this)
        newStar.setImageResource(R.drawable.ic_star)
        newStar.layoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.WRAP_CONTENT,
            FrameLayout.LayoutParams.WRAP_CONTENT)
        container.addView(newStar)
        newStar.scaleX = Math.random().toFloat() * 1.5f + .1f
        newStar.scaleY = newStar.scaleX
        starW *= newStar.scaleX
        starH *= newStar.scaleY

        newStar.translationX = Math.random().toFloat() *
                containerW - starW / 2

        val mover = ObjectAnimator.ofFloat(newStar, View.TRANSLATION_Y,
            -starH, containerH + starH)
        mover.interpolator = AccelerateInterpolator(1f)
        val rotator = ObjectAnimator.ofFloat(newStar, View.ROTATION,
            (Math.random() * 1080).toFloat())
        rotator.interpolator = LinearInterpolator()

        val set = AnimatorSet()
        set.playTogether(mover, rotator)
        set.duration = (Math.random() * 1500 + 3000).toLong()
        set.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                container.removeView(newStar)
                shower()
            }
        })
        set.start()

    }

    private fun ObjectAnimator.disableViewDuringAnimation(view: View) {
        addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator?) {
                view.isEnabled = false
            }
            override fun onAnimationEnd(animation: Animator?) {
                view.isEnabled = true
            }
        })
    }
}