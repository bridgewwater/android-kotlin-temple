# android-kotlin-temple

for android temple of project write as kotlin

repo: [https://github.com/bridgewwater/android-kotlin-temple](https://github.com/bridgewwater/android-kotlin-temple)

## env

| item           | version |
| :------------- | :------ |
| jdk            | 1.8+    |
| gradle         | 6.5+    |
| android studio | 4.1.3  |
| com.android.tools.build:gradle | 4.1.3   |
| android build tools | 30.0.5 |
| android compile sdk | 30 |
| android min sdk | 21 |
| android target sdk | 26 |

- library version

| item                           | version |
| :----------------------------- | :------ |
| androidx.multidex:multidex     | 2.0.1   |
| androidx.appcompat:appcompat   | 1.2.0   |
| androidx.core:core-ktx         | 1.3.2   |
| androidx.navigation:navigation-fragment-ktx         | 2.3.3   |
| androidx.navigation:navigation-ui-ktx         | 2.3.3   |

- test library version

| item                           | version |
| :----------------------------- | :------ |
| junit:junit:                   | 4.12    |
| org.robolectric:robolectric    | 4.4     |

more version see [package.gradle](package.gradle)

## warning

if application use [view binding](https://developer.android.com/topic/libraries/view-binding)

if can not found databinding class, just use `File -> Invalidate Caches / Just Restart`
