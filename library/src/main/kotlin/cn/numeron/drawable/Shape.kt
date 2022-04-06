package cn.numeron.drawable

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Build

enum class Shape {
    RECTANGLE,
    OVAL,
    LINE,
    RING;
}

enum class GradientType {
    LINEAR,
    RADIAL,
    SWEEP;
}

class ShapeBuilder(private val shape: Shape) {

    private var sizeBuilder = SizeBuilder()
    private var strokeBuilder = StrokeBuilder()
    private var cornersBuilder = CornersBuilder()
    private var paddingBuilder = PaddingBuilder()
    private var gradientBuilder = GradientBuilder()

    var solid: ColorStateList? = null
    var orientation = GradientDrawable.Orientation.TOP_BOTTOM

    fun solid(color: Int): ShapeBuilder {
        this.solid = ColorStateList.valueOf(color)
        return this
    }

    fun corners(builder: CornersBuilder.() -> Unit): ShapeBuilder {
        cornersBuilder.builder()
        return this
    }

    fun corners(corners: Float): ShapeBuilder {
        cornersBuilder.topLeft = corners
        cornersBuilder.topRight = corners
        cornersBuilder.bottomLeft = corners
        cornersBuilder.bottomRight = corners
        return this
    }

    fun padding(builder: PaddingBuilder.() -> Unit): ShapeBuilder {
        paddingBuilder.builder()
        return this
    }

    fun padding(padding: Int): ShapeBuilder {
        paddingBuilder.left = padding
        paddingBuilder.top = padding
        paddingBuilder.right = padding
        paddingBuilder.bottom = padding
        return this
    }

    fun stroke(builder: StrokeBuilder.() -> Unit): ShapeBuilder {
        strokeBuilder.builder()
        return this
    }

    fun size(width: Int, height: Int): ShapeBuilder {
        sizeBuilder.width = width
        sizeBuilder.height = height
        return this
    }

    fun size(builder: SizeBuilder.() -> Unit): ShapeBuilder {
        sizeBuilder.builder()
        return this
    }

    fun gradient(builder: GradientBuilder.() -> Unit): ShapeBuilder {
        gradientBuilder.builder()
        return this
    }

    fun build(): GradientDrawable {
        val gradientDrawable = GradientDrawable()

        gradientDrawable.orientation = orientation
        val colors = gradientBuilder.colors
        if (colors.size < 2) {
            gradientDrawable.color = solid
        } else {
            gradientDrawable.colors = colors
        }
        gradientDrawable.shape = shape.ordinal
        gradientDrawable.useLevel = gradientBuilder.useLevel
        gradientDrawable.gradientType = gradientBuilder.type.ordinal
        gradientDrawable.gradientRadius = gradientBuilder.radius
        gradientDrawable.setGradientCenter(gradientBuilder.centerX, gradientBuilder.centerY)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            gradientDrawable.setPadding(
                paddingBuilder.left,
                paddingBuilder.top,
                paddingBuilder.right,
                paddingBuilder.bottom
            )
        }

        gradientDrawable.setStroke(
            strokeBuilder.width,
            strokeBuilder.color,
            strokeBuilder.dashWidth,
            strokeBuilder.dashGap
        )

        gradientDrawable.cornerRadii = floatArrayOf(
            cornersBuilder.topLeft,
            cornersBuilder.topLeft,
            cornersBuilder.topRight,
            cornersBuilder.topRight,
            cornersBuilder.bottomLeft,
            cornersBuilder.bottomLeft,
            cornersBuilder.bottomRight,
            cornersBuilder.bottomRight,
        )

        return gradientDrawable
    }

    class SizeBuilder {
        var width: Int = 0
        var height: Int = 0
    }

    class CornersBuilder {
        var topLeft: Float = 0f
        var topRight: Float = 0f
        var bottomLeft: Float = 0f
        var bottomRight: Float = 0f
    }

    class PaddingBuilder {
        var left: Int = 0
        var top: Int = 0
        var right: Int = 0
        var bottom: Int = 0
    }

    class StrokeBuilder {
        var width: Int = 0
        var color: Int = Color.TRANSPARENT
        private var dash: DashBuilder = DashBuilder()

        var dashWidth: Float by dash::width

        var dashGap: Float by dash::gap

        fun dash(builder: DashBuilder.() -> Unit) {
            dash.builder()
        }

        class DashBuilder {
            var width: Float = 0f
            var gap: Float = 0f
        }

    }

    class GradientBuilder {
        private var color = ColorBuilder()
        var type = GradientType.LINEAR
        var radius: Float = 0f
        var centerX: Float = 0f
        var centerY: Float = 0f
        var useLevel: Boolean = false
        var endColor: Int? by color::end
        var startColor: Int? by color::start
        var centerColor: Int? by color::center

        val colors: IntArray
            get() = listOfNotNull(startColor, centerColor, endColor).toIntArray()

        fun color(color: Int) = color(color, color)

        fun color(start: Int, end: Int, center: Int? = null) {
            color.end = end
            color.start = start
            color.center = center
        }

        fun color(builder: ColorBuilder.() -> Unit) {
            color.builder()
        }

        class ColorBuilder {

            var start: Int? = null
            var center: Int? = null
            var end: Int? = null

        }
    }

}
