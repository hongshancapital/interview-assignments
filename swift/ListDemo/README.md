####  Demo 按照实际工作标准开发测试
* 项目整体采用 MVVM 设计模式
* 网络组件采用 Alamofire + Combine 开发
* DataModel 与 SearchListViewModel 包含对应的 UnitTest

#####  目录结构如下
```
├── ListDemo
│   ├── Common
│   │   ├── Data  Model 层
│   │   │   ├── DataBase.swift
│   │   │   └── DataModel.swift
│   │   ├── Extension 
│   │   │   ├── Color+Hex.swift
│   │   │   ├── Logger+Extension.swift
│   │   │   └── URLRequest+curl.swift
│   │   ├── NetWork 网络组件
│   │   │   ├── ApiConstants.swift
│   │   │   ├── ApiRequest.swift
│   │   │   └── SearchAppApi.swift
│   │   └── Views 公共 Views 组件
│   │       ├── CachedAsyncImage.swift
│   │       └── ErrorView.swift
│   ├── ContentView.swift
│   ├── Features
│   │   ├── View
│   │   │   ├── AppItemCell.swift
│   │   │   └── SearchListView.swift
│   │   └── ViewModel
│   │       ├── SearchListViewModel.swift
│   │       └── ViewModelFactory.swift
│   ├── ListDemoApp.swift
```

 ##### API 说明 
 ```
 https://itunes.apple.com/search?entity=software&limit=30&offset=0&term=chat 
 ```
* 分页请求，采用 offset 控制，单页 limit 为 30
* 项目中当前 term 为 Chat ，该条目 App 返回较多，多次分页请求比较难触发 No More Data 场景

* 当使用 term 为  "wuchan"，测试该条目最大返回 100 +，可以触发 loadMore 后 No More Data 场景，已录屏 Search_wuhan.mov 附在文件中

