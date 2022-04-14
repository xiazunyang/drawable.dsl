### drawable-dsl

用于构建常用`drawable`实例的`kotlin-dsl`框架，包括`shape`、`ripple`以及`selector`的`drawable`实例；顺便也支持创建颜色相关的`selector`实例。

### 安装方法

在app模块的build.gradle文件的适当位置添加以下代码：
```
implementation 'cn.numeron:drawable.dsl:1.0.0'
```

### 使用方法

* shape(GradientDrawable)
```kotlin
val gradientDrawable = shape(Shape.RECTANGLE) {
    //corners(8f)
    corners {
        topLeft = 8f
        topRight = 8f
    }
    //padding(8)
    padding {
        top = 8
        bottom = 8
    }
    //solid(Color.parseColor("#FAF0E6"))
    gradient {
        startColor = Color.RED
        centerColor = Color.GREEN
        endColor = Color.BLUE
    }
}

```

* ripple(RippleDrawable)
```kotlin
val rippleDrawable = ripple {
    color(Color.WHITE)
    //content = AppCompatResources.getDrawable(context, R.drawable.ic_launcher_background)
    content {
        AppCompatResources.getDrawable(context, R.drawable.ic_launcher_background)!!
    }
}
```

* selector(StateListDrawable)
```kotlin
val stateListDrawable = selector {
    // 一种状态
    addState(State.PRESSED) {
        shape {
            corners(8f)
            solid(Color.parseColor("#FFA500"))
        }
    }
    // 多种状态的组合，可通过`非`和`加`操合并更多状态
    addState(State.SELECTED + !State.ENABLED) {
        shape {
            corners(8f)
            solid(Color.parseColor("#FAF0E6"))
        }
    }
    // 默认状态
    defState {
        shape {
            corners(8f)
            solid(Color.parseColor("#FF8C00"))
        }
    }
}
```

* colorSelector(ColorStateList)
```kotlin
// 与StateListDrawable相同，可通过操作符组合更多状态
val colorStateList = colorSelector {
    addState(!State.ENABLED, Color.GRAY)
    addState(State.PRESSED, Color.WHITE)
    defState(Color.BLACK)
}
```
