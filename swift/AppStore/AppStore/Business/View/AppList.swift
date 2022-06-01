//
//  AppList.swift
//  AppStore
//
//  Created by liuxing on 2022/4/11.
//

import SwiftUI

struct AppList: View {
    
    @StateObject var viewModel = AppListViewModel()
    
    var body: some View {
        NavigationView {
            Group {
                if viewModel.appModelList.count == 0 {
                    ProgressView()
                } else {
                    List {
                        ForEach(viewModel.appModelList) { appModel in
                            AppRow(appModel: appModel, tapFavoriteAction: {
                                viewModel.favoriteApp(id: appModel.id)
                            })
                            .listRowInsets(EdgeInsets(top: 8, leading: 0, bottom: 8, trailing: 0))
                            .listRowSeparator(.hidden)
                            .listRowBackground(Color.clear)
                        }
                        
                        LoadMoreView(hasMoreData: viewModel.hasMoreData) {
                            Task {
                                try? await viewModel.loadMore()
                            }
                        }
                        .listRowBackground(Color.clear)
                    }
                    .refreshable {
                        try? await viewModel.refresh()
                    }
                }
            }
            .navigationTitle("App")
            .task {
                try? await viewModel.refresh()
            }
            .navigationViewStyle(.stack)
        }
    }
}

struct AppList_Previews: PreviewProvider {
    static var previews: some View {
        return AppList()
    }
}
