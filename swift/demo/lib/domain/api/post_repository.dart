import 'package:hs_demo/domain/model/post.dart';

// 简单分页数据模型
class PageablePost {
  // 当前页
  final int currentPage;
  // 是否有下一页
  final bool hasMore;
  // 当前页数据
  final List<Post> posts;
  // 错误码 默认0 没有错误
  final int errCode;

  PageablePost(this.currentPage ,this.hasMore, this.posts, {this.errCode = 0});

  String?  get errMsg {
    if (errCode == 0) return null;
    return errCode.toString();
}

}

// 条目仓库 一般适用于远程数据拉取
abstract class PostRepository {
  // 获取条目列表
  // @param pageIndex 分页页码
  Future<PageablePost> list({int pageIndex = 0});

  // 收藏或者取消收藏
  // param post 收藏的条目
  Future<Post> collect(Post post);
}