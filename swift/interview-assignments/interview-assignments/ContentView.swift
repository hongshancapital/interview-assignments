//
//  ContentView.swift
//  interview-assignments
//
//  Created by Pedro Pei on 2022/5/23.
//

import SwiftUI

struct ContentView: View {
    
    @StateObject var vm = ApplyListViewModel()
    
    
    @State private var items: [ApplyListModel.Results] = []
    
    
    @State private var headerRefreshing: Bool = false
    @State private var footerRefreshing: Bool = false
    @State private var listState = ListState()
    
    var body: some View {
        
        NavigationView{
            
            headerFooterRefresh
                .navigationTitle("App")
                .navigationBarTitleDisplayMode(.large)
                .onAppear(perform: loadData)
            
        }
        
    }
    // 下拉刷新、上拉加载
    var headerFooterRefresh: some View {
        ScrollView {
            PullToRefreshView(header: RefreshDefaultHeader(), footer: RefreshDefaultFooter()) {
                AppListView(items: items)
            }.environmentObject(listState)
        }
        .addPullToRefresh(
            isHeaderRefreshing: $headerRefreshing, onHeaderRefresh: reloadData,
            isFooterRefreshing: $footerRefreshing, onFooterRefresh: loadMoreData
        )
    }
    
    
}

// MARK: - network
private extension ContentView {
    func loadData() {
        if vm.appListData.count == 0{
            vm.fetchData()
        }
        
    }
    
    private func reloadData() {
        
        DispatchQueue.main.asyncAfter(deadline: .now() + 2) {
            var tempItems: [ApplyListModel.Results] = []
            for index in 0 ..< 6 {
                if index >= vm.appListData.count {
                    // 如果已经没有数据，则终止添加
                    listState.setNoMore(true)
                    break
                }
                let item = vm.appListData[index]
                
                tempItems.append(item)
            }
            self.items = tempItems
            headerRefreshing = false
            print(self.items.count)
        }
    }
    
    private func loadMoreData() {
        
        DispatchQueue.main.asyncAfter(deadline: .now() + 2) {
            let startIndex = items.count
            for index in 0 ..< 5 {
                let finalIndex = startIndex + index
                if finalIndex >= vm.appListData.count {
                    // 如果已经没有数据，则终止添加
                    listState.setNoMore(true)
                    break
                }
                let item = vm.appListData[finalIndex]
                
                self.items.append(item)
            }
            footerRefreshing = false
            print(self.items.count)
        }
    }
}

#if DEBUG
struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
#endif
