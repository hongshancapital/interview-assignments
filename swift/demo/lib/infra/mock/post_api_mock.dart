import 'package:hs_demo/domain/api/post_repository.dart';
import 'package:hs_demo/domain/model/post.dart';

class PostApiMockRepository extends PostRepository {




  @override
  Future<PageablePost> list({int pageIndex = 0}) {
    return Future.value(PageablePost(0,false,[Post.tempPost()]));
  }

  @override
  Future<Post> collect(Post post) {
    return Future.value(post.copy(!post.isFavorite));
  }

}