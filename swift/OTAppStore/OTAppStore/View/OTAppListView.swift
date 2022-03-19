//
//  OTAppListView.swift
//  OTAppStore
//
//  Created by liuxj on 2022/3/18.
//

import SwiftUI

struct OTAppListView: View {
    
    @StateObject var viewModel = OTAppViewModel()
    
    var body: some View {
        NavigationView {
            Group {
                if viewModel.appModelList.count == 0 {
                    ProgressView()
                        .padding(.all)
                        .onAppear { viewModel.refreshData() }
                } else {
                    List {
                        ForEach(viewModel.appModelList) { appModel in
                            OTAppRow(appModel: appModel)
                            {
                                viewModel.favoriteApp(id: appModel.id)
                            }
                                .listRowSeparator(.hidden)
                                .listRowInsets(EdgeInsets())
                                .listRowBackground(Color.clear)
                        }
                        OTLoadMoreView(hasMore: viewModel.hasMoreData)
                        {
                            viewModel.loadMoreData()
                        }
                        .listRowBackground(Color.clear)
                    }
                }
            }
            .alert(viewModel.errorMessage ?? "error occur",
                   isPresented: $viewModel.hasError,
                   actions: {})
            .refreshable { viewModel.refreshData() }
            .navigationTitle("App")
        }
        .navigationViewStyle(.stack)
    }
}

struct OTAppListView_Previews: PreviewProvider {
    static var previews: some View {
        OTAppListView()
    }
}
