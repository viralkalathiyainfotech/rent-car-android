package com.example.rentcar.base.utils

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.core.graphics.toColorInt
import com.example.rentcar.R
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

class KnobView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : View(context, attrs, defStyle) {

    // configurable values
    var minValue = 0
    var maxValue = 100

    var value: Int = 50
        set(v) {
            field = v.coerceIn(minValue, maxValue)
            invalidate()
            onValueChanged?.invoke(field)
        }

    // colors
    private var trackColor = "#555555".toColorInt()
    private var progressColor = Color.WHITE
    private var bgColor = "#333333".toColorInt()
    private var labelColor = Color.WHITE

    // drawing
    private val knobPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val arcPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val labelPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val rect = RectF()
    private var radius = 0f
    private var cx = 0f
    private var cy = 0f

    // callbacks
    var onValueChanged: ((Int) -> Unit)? = null

    // angle settings: like your screenshot (dial from ~ 135° to 405°)
    private val startAngle = 135f
    private val sweepAngle = 270f

    init {
        context.theme.obtainStyledAttributes(attrs, R.styleable.KnobView, 0, 0).apply {
            try {
                minValue = getInt(R.styleable.KnobView_kv_min, 0)
                maxValue = getInt(R.styleable.KnobView_kv_max, 100)
                value = getInt(R.styleable.KnobView_kv_value, (minValue + maxValue) / 2)

                trackColor = getColor(R.styleable.KnobView_kv_trackColor, trackColor)
                progressColor = getColor(R.styleable.KnobView_kv_progressColor, progressColor)
                bgColor = getColor(R.styleable.KnobView_kv_bgColor, bgColor)
                labelColor = getColor(R.styleable.KnobView_kv_labelColor, labelColor)
            } finally {
                recycle()
            }
        }

        knobPaint.style = Paint.Style.FILL

        arcPaint.style = Paint.Style.STROKE
        arcPaint.strokeCap = Paint.Cap.ROUND

        labelPaint.color = labelColor
        labelPaint.textAlign = Paint.Align.CENTER
        labelPaint.textSize = 32f * resources.displayMetrics.scaledDensity / 2.5f
        labelPaint.isAntiAlias = true
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        val knobHeight = min(w, h)
        radius = knobHeight / 2f * 0.7f
        cx = w / 2f
        cy = h / 2f - 16f    // shift up a bit to have room for label

        rect.set(cx - radius, cy - radius, cx + radius, cy + radius)
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // background circle
        knobPaint.color = bgColor
        canvas.drawCircle(cx, cy, radius, knobPaint)

        // outer track
        arcPaint.strokeWidth = radius * 0.15f
        arcPaint.color = trackColor
        canvas.drawArc(rect, startAngle, sweepAngle, false, arcPaint)

        // progress (white arc)
        arcPaint.color = progressColor
        val fraction = (value - minValue).toFloat() / (maxValue - minValue).toFloat()
        canvas.drawArc(rect, startAngle, sweepAngle * fraction, false, arcPaint)

        // indicator line (single white line inside)
        val angleRad = Math.toRadians((startAngle + sweepAngle * fraction).toDouble())
        val lineLen = radius * 0.7f
        val sx = cx - cos(angleRad).toFloat() * lineLen * 0.3f
        val sy = cy - sin(angleRad).toFloat() * lineLen * 0.3f
        val ex = cx + cos(angleRad).toFloat() * lineLen
        val ey = cy + sin(angleRad).toFloat() * lineLen

        val indicatorPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = progressColor
            strokeWidth = radius * 0.12f
            strokeCap = Paint.Cap.ROUND
        }
        canvas.drawLine(sx, sy, ex, ey, indicatorPaint)

        // label text under knob
        labelPaint.color = labelColor
        val textY = cy + radius + 40f
//        canvas.drawText(label, cx, textY, labelPaint)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
                val dx = event.x - cx
                val dy = event.y - cy
                val touchAngle =
                    (Math.toDegrees(atan2(dy.toDouble(), dx.toDouble())) + 360.0) % 360.0

                // map angle only if inside active arc
                val angle = ((touchAngle - startAngle) + 360.0) % 360.0
                if (angle > sweepAngle) {
                    // ignore touches outside the arc (top dead zone)
                    return true
                }

                val fraction = (angle / sweepAngle).coerceIn(0.0, 1.0)
                val newValue = (minValue + fraction * (maxValue - minValue)).toInt()
                if (newValue != value) {
                    value = newValue
                }
                return true
            }
        }
        return super.onTouchEvent(event)
    }
    fun setValue(floatValue: Float) {
        value = (floatValue * maxValue).toInt().coerceIn(minValue, maxValue)
    }

    fun getValue(): Float = value.toFloat() / maxValue.toFloat()

    fun setOnKnobChangeListener(listener: (Float) -> Unit) {
        onValueChanged = { intVal ->
            listener(intVal.toFloat() / maxValue.toFloat())
        }
    }
}