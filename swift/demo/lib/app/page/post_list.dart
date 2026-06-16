import 'package:flutter/material.dart';
import 'package:hs_demo/app/widget/post_item.dart';
import 'package:pull_to_refresh/pull_to_refresh.dart';

import '../viewmodel/page_list_vm.dart';

// 列表页面
class PostList extends StatefulWidget {
  final PostListViewModel viewModel;

  const PostList(this.viewModel, {Key? key}) : super(key: key);

  @override
  State<PostList> createState() => _PostListState();
}

class _PostListState extends State<PostList> {
  final _refreshController = RefreshController();

  @override
  Widget build(BuildContext context) {
    Future.microtask(() => widget.viewModel.reload());
    return Scaffold(
        appBar: AppBar(
          title: const Text("应用列表"),
        ),
        body: StreamBuilder<PostListStat>(
          stream: widget.viewModel.data,
          initialData: PostListStat.reload(),
          builder: (context, snapshot) {
            final data = snapshot.data!;
            _updateRefresh(data);
            if (data.isLoading) {
              if (data.currentPage == 0) {
                return const Center(
                  child: CircularProgressIndicator(),
                );
              } else {
                return const Text('');
              }
            } else {
              if (data.errMsg!=null) {
                return const Center(child: Text("发生错误啦"),);
              }
              return SmartRefresher(
                controller: _refreshController,
                footer:const ClassicFooter(),
                onRefresh: () => widget.viewModel.reload(),
                onLoading: () => widget.viewModel.loadMore(),
                enablePullUp: true,
                child: ListView.builder(
                  itemBuilder: (context, index) =>
                      PostItem(data.data[index], onFavorite: ()=> widget.viewModel.toggleCollect(data.data[index]),),
                  itemCount: data.data.length,
                ),
              );
            }
          },
        ));
  }

  _updateRefresh(PostListStat stat) {
    if (stat.isLoading) {
    } else {
      if (stat.currentPage >0) {
      } else {
        _refreshController.refreshCompleted();
      }
      if (stat.hasMore) {
        _refreshController.loadComplete();
      } else {
        _refreshController.loadNoData();
      }
    }
  }

  @override
  void dispose() {
    _refreshController.dispose();
    super.dispose();
  }
}
