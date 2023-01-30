运行环境：
    iOS 15.0及以上 
    Xcode 14.0及以上
    cocoapods 1.11.3 
    swift版本 5.4

说明：
    1. 整体采用MVVM设计，基于响应式编程风格
    2. 以满足需求为准，尽量少的功能扩展，避免过度设计
    3. 点赞功能并没有在AppModel里面增加isLike字段，因为isLike无法上传到服务端，是纯本地操作，故只简单的使用viewModel里面的likedList来记录。相对而言，增加isLike字段性能会稍微好点
