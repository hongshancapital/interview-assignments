# 红杉面试demo

## 工程说明
- 工程名称:HongShanDemo


## 主工程目录HongShanDemo
- `ContentView` App列表页SwiftUI实现. 下拉刷新使用内建的refreshable()功能.数据同步使用内建的状态管理ObservableObject系列能力.
    - `ListCell` 单元格View
    - `FootLoadingView` 底部加载更多试图
- `AppListViewModel`  MVVM设计模式中的VM层,提供网络请求,数据处理,json解析能力.
    - `ResponseModel` 网络响应的数据模型.
    - `AppModel`      列表数据模型,仅定义了使用到的字段.
- `ActiveIndicationView`  SwiftUI调用UIKit库中的UIActivityIndicatorView组件,提供加载动效
- `EmptyView` 错误提示页面,网络请求错误或接口无数据时显示. 简单实现.
- `HSColors` 定义常用的颜色值

## 网络请求
  使用https://itunes.apple.com/search?entity=software&limit=50&term=chat 接口,分页请求数据,最大请求页数3页,然后提示无更多数据.

## Test部分
- 提供网络请求测试 `HongShanDemoTests.testDataInterface()`
- 提供网络请求后的json解析测试  `HongShanDemoTests.testprocessModelToList`

## 环境说明
   本工程开发IDE:xCode 14.2
