//
//  ContentView.swift
//  SwiftHomework-ZWC
//
//  Created by 油菜花 on 2022/2/16.
//

import SwiftUI

struct ContentView: View {
    @ObservedObject var appStore: HMAppstore
    @State private var headerRefreshing: Bool = false
    @State private var footerRefreshing: Bool = false
    
    var body: some View {
        NavigationView {
            ScrollView {
                PullToRefreshView(header: RefreshHeaderView(), footer: RefreshFooterView(isHaveMorData: $appStore.isHaveMoreData), isHaveMoreData: $appStore.isHaveMoreData) {
                    switch appStore.netWorkState {
                    case .noData:
                        emptyDataView
                        
                    case .failure:
                        netWorkErrorView
                        
                    case .haveData:
                        applicationListView
                    }
                }
            }
            .onChange(of: appStore.isRequestCompleted, perform: { newValue in
                if newValue == true {                
                    self.headerRefreshing = false
                    self.footerRefreshing = false
                }
            })
            .addPullToRefresh(isHeaderRefreshing: $headerRefreshing, onHeaderRefresh: reloadData, isFooterRefreshing: $footerRefreshing, onFooterRefresh: loadMore,isHaveMoreData: $appStore.isHaveMoreData)
            .navigationTitle("App")
            .background(Color(red: 244/255, green: 243/255, blue: 247/255))
        }
        .navigationViewStyle(StackNavigationViewStyle())
    }
}

// MARK: - UI widgets
extension ContentView {
    
    var fetchingDataView: some View {
        ProgressView("Fetching data")
            .navigationTitle("App")
    }
    
    var netWorkErrorView: some View {
        HMDataErrorView(textMsg: "Sorry, there are something wrong about your network.\nPlease try again later.", imageName: "icon005")
            .navigationTitle("App")
            .padding(.leading, 50)
            .padding(.trailing, 50)
    }
    
    var emptyDataView: some View {
        HMDataErrorView(textMsg: "Sorry, there is no data that you whant.\nPlease try again later.", imageName: "icon004")
            .navigationTitle("App")
    }
    
    var applicationListView: some View {
        VStack(spacing: 15) {
            ForEach(appStore.applicationList) { app in
                CellContentView(app: $appStore.applicationList[appStore.getApplicationIndex(app: app)])
                    .listRowSeparator(.hidden)
                    .padding([.leading, .trailing])
            }
        }
    }
}

// MARK: - Private methonds
extension ContentView {
    
    private func reloadData() {
        if appStore.applicationListRawValue.isEmpty {
            appStore.initializeData()
        } else {        
            DispatchQueue.main.asyncAfter(deadline: .now() + 1) {
                appStore.refrashApplicationList()
                headerRefreshing = false
                footerRefreshing = false
            }
        }
    }
    
    private func loadMore() {
        DispatchQueue.main.asyncAfter(deadline: .now() + 1) {
            appStore.fetchNextPageData()
            footerRefreshing = false
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView(appStore: HMAppstore())
            .previewInterfaceOrientation(.portrait)
    }
}
