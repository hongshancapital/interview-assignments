//
//  AppListView.swift
//  MyApps
//
//  Created by liangchao on 2022/4/16.
//

import SwiftUI

struct AppListView: View {
    @ObservedObject var listViewModel = AppListViewModel(service: AppInfoService())
    
    var body: some View {
        NavigationView {
            ZStack {
                // not loaded data yet, show the loading view
                if listViewModel.state == .notRequested || listViewModel.state == .loading {
                    ActivityIndicatorView(style: .large)
                        .onAppear {
                            listViewModel.loadMyApps()
                        }
                } else {
                    List {
                        // app view
                        ForEach(listViewModel.viewModels) { viewModel in
                            AppInfoView(viewModel: viewModel)
                                .listRowBackground(EmptyView())
                                .listRowSeparator(.hidden)
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
            .onAppear {
                listViewModel.loadMyApps()
            }
            .background(Color(UIColor.systemGray6))
            .navigationTitle("App")
        }
        .navigationViewStyle(.stack)
    }
}

struct AppListView_Previews: PreviewProvider {
    static var previews: some View {
        AppListView(listViewModel:AppListViewModel(service: MockedAppInfoService()))
    }
}
