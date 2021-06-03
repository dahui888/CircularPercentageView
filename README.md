# CircularPercentageView

[![Platform](https://img.shields.io/badge/platform-android-green.svg)](https://developer.android.google.cn) [![Licence](https://img.shields.io/badge/Licence-Apache2-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0) [![API](https://img.shields.io/badge/API-16%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=16) [![jitpack](https://jitpack.io/v/zaaach/CircularPercentageView.svg)](https://jitpack.io/#zaaach/CircularPercentageView)

带动画的圆形进度条

# Preview
![gif](https://github.com/zaaach/imgbed/blob/master/arts/circular_percentage_view.gif)

# Install

:mega:项目基于AndroidX，迁移指南：[AndroidX迁移](https://developer.android.google.cn/jetpack/androidx/migrate)

**Step 1：** 项目根目录的build.gradle添加如下配置：

```groovy
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```

**Step 2：** app添加依赖：

```groovy
dependencies {
	 implementation 'com.github.zaaach:CircularPercentageView:x.y.z'
}
```

记得把`x.y.z`替换为[![jitpack](https://jitpack.io/v/zaaach/CircularPercentageView.svg)](https://jitpack.io/#zaaach/CircularPercentageView)中的数字

# How to use

使用简单粗暴

```xml
<com.zaaach.circularpercentageview.CircularPercentageView
        android:id="@+id/percentage_view"
        android:layout_width="180dp"
        android:layout_height="180dp"
        app:cpv_bar_width="16dp"
        tools:cpv_percentage="0.8"
        app:cpv_animation_duration="500"
        app:cpv_interpolator="fast_out_slow_in"
        app:cpv_show_text="true"
        app:cpv_text_color="#bdbdbd"
        app:cpv_text_size="20sp"
        app:cpv_percentage_bar_style="ROUND"/>
```

### 支持的attrs属性：

| Attributes | Format | Description |
| -------- | ---- | ---- |
| cpv_show_text | boolean | 是否显示进度文本，默认false |
| cpv_text_color | color \| reference     | 文本颜色，默认Color.LTGRAY  |
| cpv_text_size | dimension \| reference | 文本大小，默认36px |
| cpv_bar_width | dimension \| reference | 圆环宽度，默认24px |
| cpv_background_bar_color | color \| reference | 背景条颜色，默认#e6e6e6 |
| cpv_percentage_bar_color | color \| reference | 进度条颜色，默认#22DE6A |
| cpv_percentage_bar_style | enum | 进度条样式，默认ROUND |
| cpv_start_angle | integer | 进度起始角度，默认-90° |
| cpv_animation_duration | integer | 动画时长，默认600 |
| cpv_interpolator | enum | 动画加速器，默认Linear |
| cpv_percentage | float | 当前进度，0~1 |

# About me

掘金：[ https://juejin.im/user/56f3dfe8efa6310055ac719f ](https://juejin.im/user/56f3dfe8efa6310055ac719f)

简书：[ https://www.jianshu.com/u/913a8bb93d12 ](https://www.jianshu.com/u/913a8bb93d12)

淘宝店：[ LEON家居生活馆 （动漫摆件）]( https://shop238932691.taobao.com)

![LEON](https://raw.githubusercontent.com/zaaach/imgbed/master/arts/leon_shop_qrcode.png)

:wink:淘宝店求个关注:wink:

# License

```
Copyright (c) 2021 zaaach

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

