// 条目行为
import 'package:hs_demo/domain/model/post.dart';

// Post列表行为鸡肋
abstract class PostAction {

}

// 加载Post行为
class PostLoadAction extends PostAction {
  // 需要加载的页码
  final int pageIndex;
  // 是否还有更多
  final bool hasMore;
  // 加载完成后的数据
  final List<Post> data;
  // 加载错误码
  final String? errMsg;

  PostLoadAction(this.pageIndex,this.hasMore, this.data,{this.errMsg});

}

// 收藏Post行为
class PostCollectAction extends PostAction {
  // 收藏的Post
  final Post post;

  PostCollectAction(this.post);

}