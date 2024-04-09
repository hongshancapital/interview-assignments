//
//  SimpleList.swift
//  ListProject
//
//  Created by shencong on 2022/6/8.
//

import SwiftUI
import Refresh

struct SimpleList: View {
    // 总列表
    var allLists: [ItemModel]
    @EnvironmentObject var listData: ListData
    @StateObject var request = RequestWrapper.mockModel()
    
    @State private var currPage: Int = 0
    @State private var headerRefreshing: Bool = false
    @State private var footerRefreshing: Bool = false
    @State private var noMore: Bool = false
    
    var body: some View {
        ScrollView {
            // 头部下拉刷新区域
            if listData.items.count > 0 {
                RefreshHeader(refreshing: $headerRefreshing, action: {
                    self.reload()
                }) { progress in
                    if self.headerRefreshing {
                        SimpleRefreshingView()
                    } else {
                        SimplePullToRefreshView(progress: progress)
                    }
                }
            }
            
            // 中间cell内容区域
            ForEach(listData.items) { item in
                SimpleCell(item: item)
            }
             
            // 底部上拉加载更多区域
            if listData.items.count > 0 {
                RefreshFooter(refreshing: $footerRefreshing, action: {
                    self.loadMore()
                }) {
                    if self.noMore {
                        Text("No more data.").foregroundColor(.secondary).padding()
                    } else {
                        SimpleRefreshingView().padding()
                    }
                }
                .noMore(noMore)
                .preload(offset: 50)
            }
        }
        .enableRefresh()
        .overlay(Group {
            // 刚开始没有数据居中显示菊花
            if listData.items.count == 0 {
                ActivityIndicator(style: .medium)
            } else {
                EmptyView()
            }
        })
        .onAppear { self.reload() }
        .background(RGB(244, 244, 247))
    }
    
    // 下拉刷新
    func reload() {
        DispatchQueue.main.asyncAfter(deadline: .now() + 1) {
            //这里只做网络请求，数据依然适用mock
            getListFromService()
            
            self.currPage = 0;
            listData.items = getListData(page: 0)
            self.headerRefreshing = false
            self.noMore = false
        }
    }
    
    // 上拉加载更多
    func loadMore() {
        DispatchQueue.main.asyncAfter(deadline: .now() + 1) {
            self.currPage += 1
            listData.items += getListData(page: self.currPage)
            self.footerRefreshing = false
            self.noMore = listData.items.count >= allLists.count
        }
    }
}

extension SimpleList {
    /// 加载指定分页数据
    /// - Parameters:
    ///   - page: 页数
    /// - Returns: 指定页数的数据
    ///
    func loadData(page: Int) -> [ItemModel] {
        var results: [ItemModel] = []
        let pagesCount = 10

        let startIndex = pagesCount*page
        
        for (index,element) in allLists.enumerated() {
            if index >= startIndex && index < startIndex+10 {
                results.append(element)
            }
         }
        return results
    }
    
    // 请求列表数据
    func getListData(page: Int) -> [ItemModel]  {
        var model: [ItemModel] = RequestHelper.requestMock(.resourceList, page)
//        for index in 0..<model.count {
//            model[index].isCollect = listData.allFavorite[model[index].id] ?? false
//        }
        model.indices.forEach { index in
            model[index].isCollect = listData.allFavorite[model[index].id] ?? false
        }
        return model
    }
    
    // 请求列表数据 getListFromService
    func getListFromService() -> Void  {
        request.getRequest()
    }
}
