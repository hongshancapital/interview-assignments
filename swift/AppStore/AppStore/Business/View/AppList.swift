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
                            
                        }
                        .listRowBackground(Color.clear)
                        .task {
                            //延时，模拟上拉加载loading效果
//                            try? await Task.sleep(nanoseconds: 1000000000)
                            try? await viewModel.loadMore()
                        }
                    }
                    .refreshable {
                        //延时，模拟下拉刷新loading效果
//                        try? await Task.sleep(nanoseconds: 1000000000)
                        try? await viewModel.refresh()
                    }
                }
            }
            .navigationTitle("App")
            .task {
                //延时，模拟启动进入loading效果
//                try? await Task.sleep(nanoseconds: 1000000000)
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
