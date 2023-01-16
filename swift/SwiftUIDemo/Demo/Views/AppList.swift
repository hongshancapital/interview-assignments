//
//  AppList.swift
//  Demo
//
//  Created by 石超 on 2022/6/22.
//

import SwiftUI

struct AppList: View {
    
    @EnvironmentObject var modelData: ModelData
    @State private var footerRefreshState = RefreshState.invalid
    
    var apps: [AppModel] {
        modelData.apps
    }
    
    var body: some View {
        NavigationView {
            GeometryReader { proxy in
                List {
                    ForEach(self.apps) { app in
                        AppRow(app: app)
                    }
                    .listRowSeparator(.hidden)
                    .listRowBackground(Color.clear)
                    .listRowInsets(EdgeInsets(top: 0, leading: 0, bottom: 10, trailing: 0))
                    
                    if apps.count > 0 {
                        FooterView(state: $footerRefreshState, isNoMoreData: $modelData.isNoMoreData)
                        .anchorPreference(key: FooterBoundsPreferenceKey.self,
                                          value: .bounds,
                                          transform: { [.init(bounds: $0)] })
                        .listRowBackground(Color.clear)
                        .listRowInsets(EdgeInsets(top: 0, leading: 0, bottom: 10, trailing: 0))
                    }
                }
                .navigationTitle("App")
                .refreshable {
                    // 加载数据中不能刷新
                    guard !modelData.isLoading else { return }
                    // 正在执行加载动画就返回
                    guard footerRefreshState != .loading else { return }
                    
                    modelData.isRefreshing = true
                    modelData.isNoMoreData = false
                    modelData.updateApps()
                }
                .onAppear() {
                    modelData.isRefreshing = true
                    modelData.updateApps()
                }
                .onChange(of: modelData.isLoading, perform: { value in
                    if !value {
                        self.footerRefreshState = .stopped
                    }
                })
                .backgroundPreferenceValue(FooterBoundsPreferenceKey.self) { value -> Color in
                    DispatchQueue.main.async {
                        updateFooterRefreshState(proxy, value: value)
                    }
                    return Color.clear
                }
            }
        }
        .overlay {
            if apps.count == 0  {
                ProgressView()
            }
        }
    }
}

// MARK: - 更新Footer的刷新状态
extension AppList {
    
    private func updateFooterRefreshState(_ proxy: GeometryProxy, value: [FooterBoundsPreferenceKey.Item]) {
        // 刷新未完成不能加载更多
        guard !modelData.isRefreshing else { return }
        // 已加载全部数据就返回
        guard !modelData.isNoMoreData else { return }
        // 正在加载数据就返回
        guard !modelData.isLoading else { return }
        // 正在执行加载动画就返回
        guard footerRefreshState != .loading else { return }
        guard let bounds = value.last?.bounds else { return }
        
        let footerFrame = proxy[bounds]
        
        let y = footerFrame.minY
        let threshold: CGFloat = 70
        let bottomDistance: CGFloat = 30.0
        
        let scrollViewHeight = proxy.size.height
    
        if abs(y - (scrollViewHeight + threshold)) < 0.001 &&
            footerFrame.width == proxy.size.width &&
            footerRefreshState == .invalid
        {
            footerRefreshState = .stopped
        }
        
        var contentOffset = scrollViewHeight - y

        guard contentOffset > bottomDistance else { return }
        
        contentOffset -= bottomDistance
        
        if contentOffset > threshold &&
           (footerRefreshState == .invalid || footerRefreshState == .stopped) &&
           footerRefreshState != .triggered
        {
            footerRefreshState = .triggered
        }
        
        if contentOffset <= threshold &&
           footerRefreshState == .triggered &&
           footerRefreshState != .loading
        {
            footerRefreshState = .loading
            modelData.isLoading = true
            // 刷新数据
            modelData.updateApps()
        }
    }
}

struct AppList_Previews: PreviewProvider {
    static var previews: some View {
        AppList()
    }
}
