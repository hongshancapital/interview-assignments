import 'dart:convert';
import 'dart:developer';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:hs_demo/app/viewmodel/page_list_vm.dart';
import 'package:hs_demo/infra/mock/post_data_mock.dart';

import '../domain/model/post.dart';
import 'page/post_list.dart';

void main() async {

  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({Key? key}) : super(key: key);
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: PostList(PostListViewModelImp(postDataProvider: PostDataMockProvider())),
    );
  }
}

