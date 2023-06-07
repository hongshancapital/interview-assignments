//
//  AppListHome.swift
//  SwiftUIAssignments
//
//  Created by zcj on 2023/6/5.
//

import SwiftUI

struct AppListHome: View {

    @StateObject private var viewModel = AppListViewModel()

    // MARK: - view
    var body: some View {
        NavigationView {
            Group {
                if viewModel.isInitial {
                    ProgressView()
                } else if viewModel.isAppEmpty {
                    emptyView
                } else {
                    appListView
                }
            }
            .navigationTitle("App")
            .navigationBarTitleDisplayMode(.automatic)
            .defaultAlter("Error", isPresented: $viewModel.isShowAlert) {
                Text(viewModel.alertMessage)
            }
            .task {
                // init list
                await viewModel.initAppList()
            }
        }
    }
}

// MARK: - subviews
private extension AppListHome {

    /// when app list is empty
    var emptyView: some View {
        VStack(spacing: 5) {
            Text("No Data")
                .foregroundColor(.gray)
            Button("Retry") {
                Task(priority: .userInitiated) {
                    await viewModel.initAppList()
                }
            }
        }
    }

    /// app list
    var appListView: some View {
        List {
            
            ForEach(viewModel.appModels) { appModel in
                AppCellView(appModel)
                    .onTapGesture {
                        viewModel.like(appModel)
                    }
            }

            // footer view
            RefreshFooterView(state: viewModel.footerState)
                .listRowSeparator(.hidden)
                .listRowBackground(Color.clear)
                .task {
                    // load more data
                    await viewModel.loadMore()
                }
        }
        .refreshable {
            // refresh
            await viewModel.refresh()
        }
    }
}

// MARK: - preview
struct AppListHome_Previews: PreviewProvider {
    static var previews: some View {
        AppListHome()
    }
}
