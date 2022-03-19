//
//  AppListView.swift
//  AppsDemo
//
//  Created by changcun on 2022/3/5.
//

import SwiftUI

@available(iOS 15.0, *)
@MainActor
struct AppList: View {
    @StateObject private var viewModel = AppsViewModel()
    
    var body: some View {
        NavigationView {
            List {
                ForEach(viewModel.apps.indices, id:\.self) { index in
                    AppInfoRow(appInfo: $viewModel.apps[index], index: index) { index in
                        do {
                            try viewModel.collect(index: index)
                        } catch {
                            debugPrint(error.localizedDescription)
                        }
                    }
                    .listRowSeparator(.hidden)
                    .listRowBackground(Color.clear)
                    .listRowInsets(EdgeInsets.init(top: preferredPadding,
                                                   leading: preferredPadding,
                                                   bottom: 0,
                                                   trailing: preferredPadding))
                }
                if viewModel.headerRefreshing {
                    EmptyView()
                } else {
                    RefreshFooter(hasMoreData: viewModel.hasMoreData)
                        .task {
                            await loadMore()
                        }
                        .listRowBackground(Color.clear)
                }
            }
            .listStyle(.plain)
            .background(Color.hefeff4)
            .emptyPlaceholder($viewModel.emptyState)
            .navigationBarTitle("App", displayMode: .automatic)
            .refreshable {
                await reload()
            }
            .task {
                /// 此处延时1秒是为了看到菊花加载过程，在生产环境里不应该延时
#if DEBUG
                try? await Task.sleep(nanoseconds: 1_000_000_000)
#endif
                await reload()
            }
        }
        .navigationViewStyle(StackNavigationViewStyle())
    }
}

// MARK: - Load data

extension AppList {
    func reload() async {
        guard viewModel.canReload else {
            return
        }
        
        defer {
            viewModel.headerRefreshing = false
        }
        
        viewModel.headerRefreshing = true
        
        do {
            try await viewModel.fetchNewApps()
        } catch {
            debugPrint(error.localizedDescription)
        }
    }
    
    func loadMore() async {
        guard viewModel.canLoadMore else {
            return
        }
        
        viewModel.footerRefreshing = true
        
        /// 此处延时1秒是为了看到 loadmore 过程，在生产环境里不应该延时
#if DEBUG
        try? await Task.sleep(nanoseconds: 1_000_000_000)
#endif
        do {
            try await viewModel.fetchNextPageApps()
            
            viewModel.footerRefreshing = false
        } catch {
            debugPrint(error.localizedDescription)
        }
    }
}

struct AppListView_Previews: PreviewProvider {
    static var previews: some View {
        ForEach(["iPhone SE", "iPhone XS Max"], id: \.self) { deviceName in
            AppList()
                .previewDevice(PreviewDevice(rawValue: deviceName))
                .previewDisplayName(deviceName)
        }
    }
}
