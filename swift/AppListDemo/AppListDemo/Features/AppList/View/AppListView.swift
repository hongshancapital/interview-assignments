//
//  AppListView.swift
//  AppListDemo
//
//  Created by Kimi on 2023/3/7.
//

import SwiftUI

struct AppListView: View {
    @StateObject private var viewModel = AppListViewModel(pageCount: 10)
    
    var body: some View {
        NavigationView {
            Group {
                if (viewModel.appModels.isEmpty) {
                    if viewModel.isFirstLoading {
                        ProgressView()
                            .progressViewStyle(.circular)
                            .offset(y: -30)
                    } else {
                        AppListPlaceholder()
                            .offset(y: -30)
                    }
                } else {
                    AppList()
                }
            }
            .navigationTitle("App")
        }
        .toast(isShow: viewModel.isShow, message: viewModel.toastMessage)
        .environmentObject(viewModel)
        .task {
            await viewModel.firstLoad()
        }
    }
}

private struct AppList: View {
    @EnvironmentObject private var viewModel: AppListViewModel
    
    var body: some View {
        List {
            ForEach($viewModel.appModels) { appModel in
                AppListRow(appModel: appModel)
                    .listRowSeparator(.hidden)
                    .listRowBackground(Color.clear)
                    .listRowInsets(EdgeInsets(top: 10, leading: 15, bottom: 0, trailing: 15))
                    .frame(height: 80)
            }

            if !viewModel.isFirstLoading {
                let isLoadingFaild = (viewModel.loadingMoreStatus == .fail)
                ListRefreshFooter(hasMoreData: viewModel.hasMoreData, isLoadingFaild: isLoadingFaild)
                    .frame(height: 30)
                    .listRowSeparator(.hidden)
                    .listRowBackground(Color.clear)
                    .task {
                        if viewModel.hasMoreData {
                            await viewModel.loadMore()
                        }
                    }
            }
        }
        .background(Color("color_background"))
        .listStyle(.plain)
        .refreshable {
            await viewModel.refresh()
        }
        .simultaneousGesture(DragGesture().onChanged({ _ in
            if viewModel.loadingMoreStatus == .fail {
                Task {
                    await viewModel.loadMore()
                }
            }
        }))
    }
}

private struct AppListPlaceholder: View {
    @EnvironmentObject private var viewModel: AppListViewModel
    
    var body: some View {
        VStack {
            Button {
                Task {
                    await viewModel.firstLoad()
                }
            } label: {
                Text("Refresh")
                    .font(.subheadline)
                    .foregroundColor(.white)
            }
            .padding(EdgeInsets(top: 6, leading: 10, bottom: 6, trailing: 10))
            .background(.blue)
            .clipShape(RoundedRectangle(cornerRadius: 6))
            
            Text("Nothing here now")
                .foregroundColor(.secondary)
        }
    }
}

struct AppListView_Previews: PreviewProvider {
    static var previews: some View {
        AppListView()
    }
}
