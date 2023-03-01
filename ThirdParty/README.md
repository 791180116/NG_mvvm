#第三方开放平台集成
##友盟
### 1.如何集成
#### 首次使用友盟+的用户，需先创建应用获取Appkey。Appkey是您的应用在友盟+的唯一标识ID。https://devs.umeng.com/
#### 在工程 build.gradle 配置脚本中 buildscript 和 allprojects 段中添加 sdk maven 仓库地址
------
buildscript {
    repositories {
        google()
        jcenter()
        maven { url 'https://repo1.maven.org/maven2/' }
    }
    dependencies {
        classpath 'com.android.tools.build:gradle:3.4.0'
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}
allprojects {
    repositories {
        google()
        jcenter()
        maven { url 'https://repo1.maven.org/maven2/' }
    }
}
------