
## 环境：
* Xcode 13
* iOS15以上

## 实现功能：
1. 读取本地文件的 mock 数据
2. 数据翻页功能
3. like 状态本地存储

## 踩的坑：
1. List + AsyncImage 有图片加载提前取消的 [bug](https://developer.apple.com/forums/thread/682498)，不得已自己做一步图片
2. 下拉刷新体验问题
3. 点赞做局部刷新，不需要刷整个 list


