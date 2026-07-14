//
//  ContentView.swift
//  AppListDemo
//
//  Created by 荣恒 on 2022/2/27.
//

import SwiftUI
import Combine

struct AppListView: View {
    
    private var refreshing: Binding<Bool> {
        .init(
            get: { self.viewModel.isLoading },
            set: { _ in  }
        )
    }
    private var loadingMore: Binding<Bool> {
        .init(
            get: { self.viewModel.isLoading },
            set: { _ in  }
        )
    }
    private var hasMore: Binding<Bool> {
        .init(
            get: { self.viewModel.hasMore },
            set: { _ in  }
        )
    }
    private var showToast: Binding<Bool> {
        .init(
            get: { self.viewModel.showToast },
            set: { _ in  }
        )
    }
    
    /// ViewModel
    @ObservedObject private var viewModel: AppListViewModel = .init()
    
    var body: some View {
        NavigationView {
            if viewModel.showProgress {
                ProgressView().navigationTitle(viewModel.navigationTitle)
            }
            else if viewModel.appList.isEmpty {
                AppEmptyView(message: viewModel.emptyTitle, refreshing: refreshing) {
                    viewModel.refreshData()
                }
                .navigationTitle(viewModel.navigationTitle)
            }
            else {
                List {
                    ForEach(viewModel.appList) { data in
                        AppDataCell(data: data , action: handleLikeEvent(_:))
                            .listRowSeparator(Visibility.hidden)
                            .listRowInsets(EdgeInsets.init(top: 0, leading: 0, bottom: 0, trailing: 0))
                            .background(Color.init("background-color", bundle: nil))

                    }
                    if viewModel.hasMore {
                        AppRefreshFooterView(hasMore: hasMore, loading: loadingMore)
                            .listRowInsets(EdgeInsets.init(top: 0, leading: 0, bottom: 0, trailing: 0))
                            .background(Color.init("background-color", bundle: nil))
                            .onTapGesture {
                                viewModel.loadMoreData()
                            }
                            .onAppear {
                                viewModel.loadMoreData()
                            }
                    }
                }
                .navigationTitle(viewModel.navigationTitle)
            }
        }
        .background(content: {
            AppRefreshHeaderView(
                refreshing: refreshing,
                action: { viewModel.refreshData() }
            )
                .frame(height: 80)
        })
        .toast(show: showToast, title: viewModel.toast)
        .onAppear {
            viewModel.refreshData()
        }
    }
    
}

// MARK: - Event
extension AppListView {
    
    func handleLikeEvent(_ app: AppData) {
        viewModel.handleAppLike(app)
    }
    
}
