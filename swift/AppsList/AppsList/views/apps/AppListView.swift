//
//  AppListView.swift
//  AppsList
//
//  Created by 余晨正 on 2022/2/15.
//

import SwiftUI

struct AppListView: View {
    
    @State private var listParams = Params()
    @State private var refreshing = false
    @State private var loadingMore = false
    @State private var hasMore = false
    @State private var showToast = false
    
    @ObservedObject private var appsObserver = AppsObserver()
    
    var body: some View {
        NavigationView {
            if appsObserver.response == nil {
                ProgressView().navigationTitle("APP")
            } else if appsObserver.apps?.count ?? 0 == 0 {
                EmptyView(message: appsObserver.response?.message ?? "暂无数据", refreshing: $refreshing) {
                    refreshData()
                }
                .navigationTitle("APP")
            } else {
                List {
                    ForEach(appsObserver.apps!) { app in
                        AppCell(app: app)
                            .listRowSeparator(Visibility.hidden)
                            .listRowInsets(EdgeInsets.init(top: 0, leading: 0, bottom: 0, trailing: 0))
                            .background(Color.init("background-color", bundle: nil))
                    }
                    if appsObserver.apps!.count > 0 {
                        RefreshFooterView(hasMore: $hasMore, loading: $loadingMore)
                            .listRowInsets(EdgeInsets.init(top: 0, leading: 0, bottom: 0, trailing: 0))
                            .background(Color.init("background-color", bundle: nil))
                            .onTapGesture {
                                loadMoreData()
                            }
                            .onAppear {
                                loadMoreData()
                            }
                    }
                }
                .navigationTitle("APP")
            }
        }
        .background(content: {
            RefreshHeaderView(refreshing: $refreshing, action: {
                refreshData()
            }).frame(height: 80)
        })
        .toast(show: $showToast, title: appsObserver.response?.message ?? "")
        .onAppear {
            refreshData()
        }
    }
    
    private func refreshData() {
        refreshing = true
        loadData(params: Params())
        refreshing = false
    }
    
    private func loadMoreData() {
        guard hasMore else {
            return
        }
        guard !loadingMore else {
            return
        }
        var params = Params()
        params.pageNum = listParams.pageNum + 1
        loadingMore = true
        loadData(params: params)
        loadingMore = false
    }
    
    private func loadData(params: Params) {
        Task {
            let (apps, response) = await DataService.shared.fetchApps(params: params)
            appsObserver.response = response
            guard let apps = apps else {
                guard response.code == NetworkCode.success else {
                    showToastIfNeed()
                    return
                }
                hasMore = false
                showToastIfNeed()
                return
            }
            appsObserver.apps = params.pageNum > 1 ? appsObserver.apps! + apps : apps
            guard apps.count >= params.pageSize else {
                hasMore = false
                updateListParams(params)
                showToastIfNeed()
                return
            }
            hasMore = true
            updateListParams(params)
            showToastIfNeed()
        }
    }
    
    private func updateListParams(_ params: Params) {
        listParams = params
    }
    
    private func showToastIfNeed() {
        if appsObserver.apps?.count ?? 0 == 0 {
            // 无应用数据时会由EmptyView展示相关信息，此时不需要弹Toast
            return
        }
        guard let response = appsObserver.response else {
            // 网络请求未返回时，不会展示Toast
            return
        }
        guard response.code != NetworkCode.success else {
            // 网络请求成功时，不会展示Toast
            return
        }
        guard !showToast else {
            return
        }
        showToast = true
    }
}

class AppsObserver: ObservableObject {
    @Published var apps: [AppModel]?
    @Published var response: NetworkResponse?
}
