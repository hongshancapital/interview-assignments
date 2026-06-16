import 'package:hs_demo/domain/api/post_repository.dart';
import 'package:hs_demo/domain/model/post.dart';

// 数据提供者 中间一层 场景运用于保存数据到本地之类
abstract class PostDataProvider {
  // 按照页码获取条目列表
  Future<PageablePost> list({int pageIndex = 0});

  // 收藏或者取消收藏
  Future<Post> collect(Post post);
}