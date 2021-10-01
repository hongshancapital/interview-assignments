## code review
- `HomeView` 中`@Environment(\.isSearching) private var isSearching: Bool`相关代码可以删掉
- `NetworkReponse`中`Data`扩展返回少一个空格。第12行。
- `Data`和`Encodable`扩展是否需要移到别的文件？
- `LocalConnectManager`中`startLocalServer`注释是否可以删除?
