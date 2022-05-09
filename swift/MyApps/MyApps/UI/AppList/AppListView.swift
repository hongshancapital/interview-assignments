//
//  AppListView.swift
//  MyApps
//
//  Created by liangchao on 2022/4/16.
//

import SwiftUI

struct AppListView: View {
    @StateObject var listViewModel = AppListViewModel(service: AppInfoService())
    
    var body: some View {
        NavigationView {
            ZStack {
                // not loaded data yet, show the loading view
                if isInitialLoading {
                    ActivityIndicatorView(style: .large)
                        .onAppear {
                            listViewModel.loadMyApps()
                        }
                } else {
                    List {
                        // app view
                        if let viewModels = listViewModel.viewModels {
                            ForEach(viewModels) { viewModel in
                                AppInfoView(viewModel: viewModel)
                                    .listRowBackground(EmptyView())
                                    .listRowSeparator(.hidden)
                            }
                        }
                        // bottom view: loaidng more or no data tip
                        if listViewModel.state == .noMoreData {
                            NoMoreDataView()
                                .listRowBackground(EmptyView())
                                .listRowSeparator(.hidden)
                        } else {
                            FooterLoadingView()
                                .listRowBackground(EmptyView())
                                .listRowSeparator(.hidden)
                                .onAppear {
                                listViewModel.loadMoreApps()
                            }
                        }
                    }
                    .listStyle(.plain)
                    .refreshable {
                        // pull to refresh, reload apps data
                        listViewModel.loadMyApps()
                    }
                }
            }
            .frame(maxWidth: .infinity, maxHeight: .infinity)
            .background(Color(UIColor.systemGray6))
            .navigationTitle("App")
            .onAppear {
                listViewModel.loadMyApps()
            }
        }
        .navigationViewStyle(.stack)
    }
    
    var isInitialLoading: Bool {
        return listViewModel.state == .notRequested || listViewModel.viewModels == nil
    }
}

struct AppListView_Previews: PreviewProvider {
    static var previews: some View {
        AppListView(listViewModel:AppListViewModel(service: MockedAppInfoService()))
    }
}
