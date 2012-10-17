# Android FixedRatio Library

This library allows to resize a view using a ratio.

## Features

- Resize a view using a ratio
- Resize policies: crop, fit, width or height

## Compatibility

This library is compatible from API 8 (Android 2.2), but it should work on previous versions.

## Usage

Add the View to the layout:

``` xml
<org.pierrre.fixedratio.FixedRatioView xmlns:fixedratio="http://schemas.android.com/apk/res-auto"
    android:layout_width="100dp"
    android:layout_height="100dp"
    fixedratio:ratio="2" >

<!-- Your views here, it's a FrameLayout -->

</org.pierrre.fixedratio.FixedRatioView>
```

You can also define the ratio using both `fixedratio:ratio_horizontal` and `fixedratio:ratio_vertical` attributes. 

There are several resize policies: crop (default), fit, width and height. Change it with the `fixedratio:policy` attribute.


## Contact

- Email: pierredurand@gmail.com
- Twitter: [@pierredurand87](https://twitter.com/pierredurand87)
- Google+: [Pierre Durand](https://plus.google.com/115978530878583279430)
- GitHub: [pierrre](https://github.com/pierrre)
