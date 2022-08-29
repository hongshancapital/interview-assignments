//
//  AppList.swift
//  AppList
//
//  Created by 大洋 on 2022/8/21.
//

import SwiftUI

struct AppList: View {
    @EnvironmentObject var viewModel: AppListViewModel
    
    private var refreshListener: some View {
        LoadingView(noMore: $viewModel.settings.noMore)
            .frame(height: 60)
            .background(.clear)
    }

    private var retryView: some View {
        RetryView {
            viewModel.loadMoreAppList(true)
        }
        .frame(height: 60)
        .padding()
    }

    var body: some View {
        return Group {
            if viewModel.appList.isEmpty {
                retryView
            } else {
                List {
                    ForEach(viewModel.appList) { listModel in
                        AppListCell(info: listModel)
                            .listRowBackground(Color.clear)
                            .listRowSeparator(.hidden)
                            .onAppear {
                                if listModel == viewModel.appList.last {
                                    viewModel.loadMoreAppList()
                                }
                            }
                    }
                    if !viewModel.appList.isEmpty {
                        switch viewModel.settings.loadState {
                        case .loadMoreError:
                            RetryView {
                                viewModel.loadMoreAppList(true)
                            }
                            .frame(height: 60)
                            .listRowBackground(Color.clear)
                        default:
                            refreshListener
                                .listRowBackground(Color.clear)
                        }
                    }
                }
                .refreshable {
                    viewModel.reloadAppList()
                }
                .accessibilityIdentifier("applist")
            }
        }
    }
}

struct AppList_Previews: PreviewProvider {
    static var previews: some View {
        AppList()
    }
}



