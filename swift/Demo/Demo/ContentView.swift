//
//  ContentView.swift
//  Demo
//
//  Created by 盼 on 2022/4/14.
//

import SwiftUI

struct ContentView: View {
    
    @ObservedObject var homeData = HomeData()
    
    @State private var headerRefreshing: Bool = false
    @State private var footerRefreshing: Bool = false

    var body: some View {
        NavigationView {
            pullToRefreshScrollBody
                .background(Color.bgColor)
                .navigationBarTitle("App")
                .navigationBarColor(Color.bgColor)
        }
    }
    
    var pullToRefreshScrollBody: some View {
        headerFooterRefresh
    }
    
    var headerFooterRefresh: some View {
        ScrollView {
            PullToRefreshView {
                ItemList(items: homeData.listArray, homeData: homeData)
            }
        }
        .addPullToRefresh(isHeaderRefreshing: $headerRefreshing, onHeaderRefresh: {
            DispatchQueue.main.asyncAfter(deadline: .now() + 1.5) {
                homeData.getListData()
                headerRefreshing = false
                footerRefreshing = false
            }
        }, isFooterRefreshing: $footerRefreshing) {
            if homeData.hasMoreData {
                DispatchQueue.main.asyncAfter(deadline: .now() + 1.5) {
                    homeData.getMoreData()
                    footerRefreshing = false
                }
            } else {
                footerRefreshing = true
            }
        }
    }
}

struct ItemList: View {
    let items: [ListModel]
    let homeData: HomeData
    
    let columns = [GridItem(.fixed(SCREEN_WIDTH - 30))]

    var body: some View {
        LazyVGrid(columns: columns, alignment: .center, spacing: 10) {
            ForEach(homeData.listArray) { listModel in
                ListRow(listModel: listModel).environmentObject(homeData)
            }
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
