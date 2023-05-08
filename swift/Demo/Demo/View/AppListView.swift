//
//  AppListView.swift
//  Demo
//
//  Created by jyt on 2023/3/21.
//

import SwiftUI
import Combine

struct AppListView: View {
    @StateObject private var viewModel = AppListViewModel()
    var body: some View {
        Group {
            if viewModel.appList.resultCount == 0 {
                ProgressView()
                    .task {
                        await viewModel.refreshData()
                    }
            } else {
                List {
                    ForEach(viewModel.appList.results) { item in
                        AppListRow(item: item)
                            .frame(height: 80)
                            .listRowSeparator(.hidden)
                            .listRowBackground(
                                RoundedRectangle(cornerRadius: 15)
                                    .foregroundColor(.white)
                                    .padding(5))
                    }
                    
                    AppListBottomView(isLoading: viewModel.isMore) {
                        viewModel.loadMore()
                    }
                    .listRowBackground(Color.clear)
                }
                .refreshable {
                    await viewModel.refreshData()
                }
            }
        }.environmentObject(viewModel)
    }
}

struct AppListView_Previews: PreviewProvider {
    static var previews: some View {
        AppListView()
        AppListBottomView(isLoading: false) {
            
        }
    }
}

struct AppListBottomView: View {
    var isLoading: Bool
    var action: () -> Void
    
    var body: some View {
        HStack {
            Spacer()
            HStack(spacing: 10) {
                if isLoading {
                    ProgressView()
                        .onAppear {
                            action()
                        }
                }
                
                Text(isLoading ? "Loading..." : "No more data.").foregroundColor(.secondary)
            }
            Spacer()
        }
    }
}


