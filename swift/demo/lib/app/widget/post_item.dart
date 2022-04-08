import 'package:extended_image/extended_image.dart';
import 'package:flutter/material.dart';
import 'package:hs_demo/app/viewmodel/page_list_vm.dart';
import 'package:hs_demo/domain/model/post.dart';

class PostItem extends StatelessWidget {
  // 模型实体
  final Post model;
  final PostListInputViewModel input;

  const PostItem(this.model, this.input, {Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Container(
      margin:const EdgeInsets.symmetric(vertical: 8,horizontal: 16),
      color: Colors.white,
      child: Container(
        padding:const EdgeInsets.all(8),
        child: ListTile(
          leading: ExtendedImage.network(
            model.iconUrl,
            width: 50,
            height: 50,
            cacheHeight: 50,
            cacheWidth: 50,
            shape: BoxShape.circle,
          ),
          title: Text(
            model.trackCensoredName,
            maxLines: 2,
          ),
          subtitle: Text(
            model.description,
            maxLines: 2,
          ),
          trailing: IconButton(
            icon:
                Icon(model.isFavorite ? Icons.favorite : Icons.favorite_outline),
            color: model.isFavorite ? Colors.red : Colors.grey,
            onPressed: () => input.toggleCollect(model),
          ),
        ),
      ),
    );
  }
}
