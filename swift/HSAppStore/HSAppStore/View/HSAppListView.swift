//
//  HSAppListView.swift
//  HSAppStore
//
//  Created by Sheng ma on 2022/5/15.
//
import SwiftUI

struct HSAppListView: View {
    
    @StateObject var viewModel = HSAppViewModel()
    
    var body: some View {
        NavigationView {
            Group {
                if viewModel.modelList.count == 0 {
                    addProgressView()
                } else {
                    List {
                        ForEach(viewModel.modelList) { appModel in
                            HSAppCell(appModel: appModel) {
                                viewModel.likeApp(id: appModel.id)
                            }
                            .listRowSeparator(.hidden)
                            .listRowInsets(EdgeInsets())
                            .listRowBackground(Color.clear)
                        }
                        HSLoadingView(hasMore: viewModel.hasMore) {
                            loadMoreData()
                        }
                        .listRowBackground(Color.clear)
                    }
                }
            }
            .task { getAppData() }
            .refreshable { getAppData() }
            .navigationTitle("App")
        }
        .navigationViewStyle(.stack)
    }
    
    private func getAppData() {
        Task {
            await viewModel.getAppData()
        }
    }
    
    private func loadMoreData() {
        Task {
            await viewModel.loadMoreData()
        }
    }
}

struct addProgressView: View {
    var body: some View {
        ProgressView()
        .padding(.all)
    }
}

struct HSAppListView_Previews: PreviewProvider {
    static var previews: some View {
        HSAppListView()
    }
}
