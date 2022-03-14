//
//  AppListView.swift
//  AppList
//
//  Created by Ma on 2022/3/13.
//

import SwiftUI
import UIKit

struct AppList: View {
    var body: some View {
        AppListView(viewModel: .init())
    }
}

struct AppListView: View {
    @ObservedObject var viewModel: AppListViewModel
    
    init(viewModel:AppListViewModel) {
        self.viewModel = viewModel
    }
    
    var body: some View {
        NavigationView {
            switch viewModel.state {
            case .initial:
                ProgressView().navigationTitle("App")
            case .errorState:
                VStack {
                    Text(viewModel.errorMessage)
                    Button(action: {
                        viewModel.excute(.onInitial)
                    }) {
                        Text("retry")
                    }
                }
                .navigationTitle("App")
            default:
                List {
                    ForEach(viewModel.appData.results) { app in
                        AppRow(viewModel: AppItemViewModel(app: app))
                            .listRowSeparator(.hidden)
                            .listRowInsets(EdgeInsets.init(top: 0, leading: 0, bottom: 0, trailing: 0))
                    }
                    RefreshFooterView(hasMore: $viewModel.hasMore, loading: $viewModel.isFooterRefreshing)
                        .onAppear {
                            viewModel.excute(.onLoadMore)
                        }
                }
                .navigationTitle("App")
            }
        }
        .background(content: {
            RefreshHeaderView(
                refreshing: $viewModel.isHeaderRefreshing,
                action: { viewModel.excute(.onRefresh) }
            )
                .frame(height: 80)
        })
        .onAppear {
            viewModel.excute(.onInitial)
        }
    }
}

//struct AppListView_Previews: PreviewProvider {
//    static var previews: some View {
//        AppListView()
//    }
//}
