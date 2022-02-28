//
//  AppListView.swift
//  Demo
//
//  Created by hbc on 2022/2/19.
//  Copyright © 2022 hbc. All rights reserved.
//

import SwiftUI
import MapKit
 

struct AppListView: View {
    @State private var appList: [AppInfo] = []
    // to adopt for dark mode...
    @Environment(\.colorScheme) var scheme
    
    @State private var headerRefreshing: Bool = false
    @State private var footerRefreshing: Bool = false
    @State private var noMoreRefresh: Bool = false
    
    @State private var listState = ListState()
    
    var body: some View {
        VStack {
            pullToRefreshScrollBody
                .frame(minWidth: 0, maxWidth: .infinity, minHeight: 0, maxHeight: .infinity)
                .background(Color.init(red: 230/255.0, green: 230/255.0, blue: 230/255.0))
            .onAppear(perform: loadData)
        }.edgesIgnoringSafeArea(.all)

    }
    
    var pullToRefreshScrollBody: some View {
        headerFooterRefresh
    }
    
    // 下拉刷新、上拉加载
    var headerFooterRefresh: some View {
        return ScrollView {
            PullToRefreshView(header: RefreshDefaultHeader(), footer: RefreshDefaultFooter()) {
                AppInfoList(appInfoList: appList, headerRefreshing: $headerRefreshing)
            }
            .environmentObject(listState)
        }
        .addPullToRefresh(isHeaderRefreshing: $headerRefreshing,
                          onHeaderRefresh: reloadData,
                          isFooterRefreshing: $footerRefreshing,
                          onFooterRefresh: loadMoreData,
                          noMoreRefresh: $noMoreRefresh)
    }
    
    func loadData() {
        noMoreRefresh = false
        listState.setNoMore(false)
        var manager = DataManager(filePath: "data.txt", pageCount: 20)
        if let rst = manager.reloadAppList()  {
            self.appList = rst
        }
    }
    
    private func reloadData() {
        noMoreRefresh = false
        listState.setNoMore(false)
        DispatchQueue.main.asyncAfter(deadline: .now() + 1) {
            loadData()
            headerRefreshing = false
        }
    }
    
    private func loadMoreData() {
        DispatchQueue.main.asyncAfter(deadline: .now() + 1) {
            var manager = DataManager(filePath: "data.txt", pageCount: 20)
            if let rst = manager.loadMoreAppList(location: self.appList.count)  {
                self.appList.append(contentsOf: rst)
            }else{
                // 如果已经没有数据，则终止添加
                listState.setNoMore(true)
                noMoreRefresh = true
            }
            footerRefreshing = false
        }
    }
}


struct AppInfoList: View {
    let appInfoList: [AppInfo]
    @EnvironmentObject private var listState: ListState
    @Binding var headerRefreshing: Bool
    
    var body: some View {
        let topH = headerRefreshing ? ScreenSafeHead() : ScreenSafeHead()+60.0
        
        VStack {
            Spacer(minLength: topH)
            ForEach(appInfoList) { item in
                BaseRow(appInfo: item)
            }

            if listState.noMore {
                Text("No more data.")
                    .foregroundColor(Color.gray)
                    .padding(.top, 10)
                    .padding(.bottom, 30)
            }
            
            Spacer(minLength: 20)
        }
        
    }
}


func ScreenSafeHead() -> CGFloat {
    guard #available(iOS 11.0, *) else {
        return 60.0
    }

    let isX = UIApplication.shared.windows[0].safeAreaInsets.bottom > 0
    return isX ? 90.0 : 60.0
}


struct AppListView_Previews: PreviewProvider {
    static var previews: some View {
        AppListView()
    }
}
