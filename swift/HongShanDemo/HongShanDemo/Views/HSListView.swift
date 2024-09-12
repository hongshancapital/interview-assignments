//
//  HSListView.swift
//  HongShanDemo
//
//  Created by 木木 on 2022/5/12.
//

import SwiftUI

struct HSListView: View {
    
    @State private var itemAll: [HSModel] = []
    @Environment(\.colorScheme) var scheme
    
    @StateObject var viewModel = HSViewModel()
    
    @State private var headerRefreshing: Bool = false
    @State private var footerRefreshing: Bool = false
    
    @State private var listState = ListState()
    
    var body: some View {
//        NavigationView {
//            List(items) { model in
//                NavigationLink {
//                    HSDetail(model: model)
//                } label: {
//                    HSRow(model: model)
//                }
//            }
//            .navigationTitle("HSModel")
//        }
        NavigationView{
            headerFooterRefresh
                .navigationTitle("HS Demo")
                .navigationBarTitleDisplayMode(.inline)
                .onAppear {
                    loadData()
                }
        }
    }
    
    var headerFooterRefresh: some View {
        ScrollView {
            PullToRefreshView(header: RefreshDefaultHeader(), footer: RefreshDefaultFooter()) {
                ForEach(itemAll) { model in
                    HSRow(model: model).onTapGesture {
                        replaceModel(model: model)
                    }
                }
            }.environmentObject(listState)
        }
        .addPullToRefresh(isHeaderRefreshing: $headerRefreshing, onHeaderRefresh: reloadData,
                          isFooterRefreshing: $footerRefreshing, onFooterRefresh: loadMoreData)
        
    }
    
    private func replaceModel(model: HSModel){
        for index in 0..<itemAll.count {
            var tempModel = itemAll[index]
            if tempModel.id == model.id {
                tempModel.isFavorite = !tempModel.isFavorite
                itemAll[index] = tempModel
            }
        }
    }
    
    private func loadData() {
        var tempItems: [HSModel] = []
        for index in 0..<2 {
            if index >= hsArr.count {
                // 如果已经没有数据，则终止添加
                listState.setNoMore(true)
                break
            }
            var item = hsArr[index]
            item.isFavorite = false;
            NSLog("+++++++++%d",item.isFavorite)
            tempItems.append(item)
        }
        self.itemAll = tempItems
    }
    
    private func reloadData() {
        print("begin refresh data ...\(headerRefreshing)")
        DispatchQueue.main.asyncAfter(deadline: .now() + 2) {
            loadData()
            headerRefreshing = false
            print("end refresh data ...\(headerRefreshing)")
        }
    }
    
    private func loadMoreData() {
        print("begin load more data ... \(footerRefreshing)")
        DispatchQueue.main.asyncAfter(deadline: .now() + 2) {
            let startIndex = itemAll.count
            for index in 0..<10 {
                let finalIndex = startIndex + index
                if finalIndex >= hsArr.count {
                    // 如果已经没有数据，则终止添加
                    listState.setNoMore(true)
                    break
                }
                let item = hsArr[finalIndex]
                self.itemAll.append(item)
            }
            footerRefreshing = false
            print("end load more data ... \(footerRefreshing)")
        }
    }
}

struct HSListView_Previews: PreviewProvider {
    static var previews: some View {
        HSListView()
    }
}
