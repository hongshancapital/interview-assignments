# 应用列表Demo

用Flutter完成的，真是抱歉，现在在老家，用的是一台旧的PC， 身边没有苹果电脑，真是抱歉，抱歉。
SwiftUI项目中很少使用，不过上手会很快，语言和模式都不是问题。
再次抱歉，抱歉，抱歉 希望能给个机会 [视频链接](demo.mp4)

## 功能介绍

1. 模拟远端获取数据 默认延时3秒数据获取
``` dart post_data_mock 文件中设置
final _delayTime =const Duration(seconds: 3);
```
2. 界面支持上拉加载更多和下拉刷新
3. 每个条目支持点击收藏和取消收藏，默认是内存操作，没有做持久化
4. 各种情况数据错误已经捕捉

## 使用的第三方库

1. [rxdart 函数式编程](https://pub.flutter-io.cn/packages/rxdart)
2. [extended_image 图片网络库](https://pub.flutter-io.cn/packages/extended_image)
3. [pull_to_refresh 刷新工具](https://pub.flutter-io.cn/packages/pull_to_refresh)

## 应用程式模式

1. DDD 领域驱动设计，不过这个功能太小，没有体现出DDD的优势
2. MVVM 表现模式，集中管控输入输出逻辑，使得界面更简洁一些
3. Infra 基础建设层，方便模拟数据，切换数据提供者


