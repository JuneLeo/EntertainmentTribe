#EntertainmentTribe

clone后请先下载react-native 包，具体操作为

1.install node.js

2.进入项目根目录（package.json文件所在目录），在命令中输入   npm install 即可安装react-native相关包（这个包maven中本地仓）根目录的gradle中

maven {
            // All of React Native (JS, Android binaries) is installed from npm
            url "$rootDir/node_modules/react-native/android"
        }
上面即为react-native的包，项目中涉及react-native的类都在maven本地仓中



解释：package.json 中为   react-native的一些配置环境，里面包含了你要下载的react-native包的版本。

assets/index.android.bundle  为启动项目的入口，在react-native项目中编译生成，编译方法：在react-native的根目录下输入下面命令

react-native bundle --platform android --dev false --entry-file index.android.js --bundle-output G:/bighero/EntertainmentTribe/app/src/main/assets/index.android.bundle --assets-dest G:/bighero/EntertainmentTribe/app/src/main/res

完毕!