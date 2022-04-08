import 'package:hs_demo/app/viewmodel/post_action.dart';
import 'package:hs_demo/domain/api/post_repository.dart';
import 'package:hs_demo/domain/data/post_data_provider.dart';
import 'package:hs_demo/domain/model/post.dart';
import 'package:rxdart/rxdart.dart';

// 页面数据状态管理
class PostListStat {
  final bool isLoading;
  final List<Post> data;
  final int currentPage;
  final bool hasMore;
  final String? errMsg;

  PostListStat(
      this.isLoading, this.data, this.currentPage, this.hasMore, this.errMsg);

  factory PostListStat.reload() {
    return PostListStat(true, [], 0, false, "");
  }

  factory PostListStat.data(List<Post> data) {
    return PostListStat(false, data, 0, false, null);
  }
}

abstract class PostAction {}


// 用户输入部分
abstract class PostListInputViewModel {
  // 重新加载数据
  reload();

  // 加载更多
  loadMore();

  // 收藏或者取消收藏
  // param post 收藏的条目实体
  toggleCollect(Post post);
}

// 业务逻辑输出
abstract class PostListOutputViewModel {
  // 数据输出
  Stream<PostListStat> get data;
}

abstract class PostListViewModel
    implements PostListOutputViewModel, PostListInputViewModel {}

// 列表页面默认VM
class PostListViewModelImp extends PostListViewModel {
  final PostDataProvider postDataProvider;

  PostListViewModelImp({required this.postDataProvider});

  final _postIndexSubject = PublishSubject<int>();
  final _collectSubject = PublishSubject<Post>();

  // 页码从0开始
  int _currentPageIndex = 0;

  @override
  loadMore() {
    _currentPageIndex += 1;
    _postIndexSubject.sink.add(_currentPageIndex);
  }

  @override
  reload() {
    _currentPageIndex = 0;
    _postIndexSubject.sink.add(_currentPageIndex);
  }

  @override
  Stream<PostListStat> get data => Rx.merge([
        _postIndexSubject
            .flatMap<PageablePost>((value) =>
                Stream.fromFuture(postDataProvider.list(pageIndex: value)))
            .map((event) =>
                PostLoadAction(event.currentPage, event.hasMore, event.posts, errMsg: event.errMsg)),
        _collectSubject
            .flatMap(
                (value) => Stream.fromFuture(postDataProvider.collect(value)))
            .map((event) => PostCollectAction(event))
      ]).scan((accumulated, value, index) {
        if (value is PostLoadAction) {
          return PostListStat(
              false,
              value.pageIndex > 0
                  ? [...accumulated.data, ...value.data]
                  : value.data,
              value.pageIndex,
              value.hasMore,
              value.errMsg);
        }
        if (value is PostCollectAction) {
          final index = accumulated.data.indexOf(value.post);
          if (index >= 0) {
            accumulated.data[index] = value.post;
          }
          return accumulated;
        }
        return accumulated;
      }, PostListStat.reload());

  dispose() {
    _postIndexSubject.sink.close();
  }

  @override
  toggleCollect(Post post) {
    _collectSubject.sink.add(post);
  }
}
