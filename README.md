# android-kotlin-temple

for android temple of project write as kotlin

repo: [https://github.com/bridgewwater/android-kotlin-temple](https://github.com/bridgewwater/android-kotlin-temple)

## env

> max `jdk version 1.17`, `android studio 2024`

| item                                      | version |
|:------------------------------------------|:--------|
| jdk                                       | 1.11+   |
| gradle                                    | 7.2     |
| com.android.tools.build:gradle            | 7.1.3   |
| org.jetbrains.kotlin:kotlin-gradle-plugin | 1.7.22  |
| android studio                            | 2024.3  |
| android build tools                       | 30.0.5  |
| android compile sdk                       | 30      |
| android min sdk                           | 23      |
| android target sdk                        | 26      |

- library version

| item                                        | version |
|:--------------------------------------------|:--------|
| androidx.multidex:multidex                  | 2.0.1   |
| androidx.appcompat:appcompat                | 1.2.0   |
| androidx.core:core-ktx                      | 1.3.2   |
| androidx.navigation:navigation-fragment-ktx | 2.3.3   |
| androidx.navigation:navigation-ui-ktx       | 2.3.3   |

- test library version

| item                              | version |
|:----------------------------------|:--------|
| junit:junit                       | 4.13.2  |
| org.robolectric:robolectric       | 4.11.1  |
| org.robolectric:shadows-framework | 4.4     |

more version see [config.gradle](config.gradle)

## dev

task

```bash
# see help
make help
# init project
make init
# check dependency
make dep
# run ci
make ci
```

## warning

if application use [view binding](https://developer.android.com/topic/libraries/view-binding)

if can not found databinding class, just use `File -> Invalidate Caches / Just Restart`
