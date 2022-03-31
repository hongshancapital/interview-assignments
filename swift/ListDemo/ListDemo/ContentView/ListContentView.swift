//
//  ListContentView.swift
//  ListDemo
//
//  Created by mac on 2022/3/18.
//

import SwiftUI
import BBSwiftUIKit

struct ListContentView: View {
    
    //正在刷新
    @State var isRefreshing: Bool = false
    //正在加载
    @State var isLoadingMore: Bool = false
    //刷新数据
    @State var isReloadData: Bool = true
    
    @ObservedObject var viewModel = ListContentModel()

    init() {
        self.viewModel.refreshData()
    }
    
    var body: some View {
        NavigationView {
            BBTableView(self.viewModel.listData) { model in
                ListContentCell(model: model)
            }
           .bb_pullDownToRefresh(isRefreshing: $isRefreshing) {
               DispatchQueue.main.asyncAfter(deadline: .now() + 1) {
                   self.viewModel.refreshData()
                   self.isRefreshing = false
               }
           }
           .bb_pullUpToLoadMore(bottomSpace: 40) {
               if self.isLoadingMore{return}
               self.isLoadingMore = true
               DispatchQueue.main.asyncAfter(deadline: .now() + 1) {
                   self.viewModel.loadMore()
                   self.isLoadingMore = false
               }
           }
           .bb_reloadData($isReloadData)
           .listStyle(GroupedListStyle())
           .navigationBarTitle(Text("app"))
       }
    }
}

struct ListContentView_Previews: PreviewProvider {
    static var previews: some View {
        ListContentView()
    }
}
