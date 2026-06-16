//
//  ContentView.swift
//  RedwoodTestProject
//
//  Created by 徐栋 on 2023/2/7.
//

import SwiftUI

struct ContentView: View {
    static let mainBackColor = "#f4f4f7"
    @ObservedObject var viewModel = XXAdsListViewModel()
    
    var body: some View {
        NavigationView {
            List {
                ForEach(viewModel.dataSource) { model in
                    XXAdListitem(model: model)
                        .listRowSeparator(.hidden)
                        .listRowBackground(Color(hex: ContentView.mainBackColor))
                        .cornerRadius(10)
                }
                XXRefreshFooterView(noMoreData: $viewModel.noMoreData)
                    .getMoreAction {
                        viewModel.loadMoreData()
                    }
                    .listRowBackground(Color(hex: ContentView.mainBackColor))
            }
            .navigationTitle("App")
            .background(Color(hex: ContentView.mainBackColor))
            .listStyle(.plain)
            .refreshable {
                // 模拟数据加载时间
                try? await Task.sleep(nanoseconds: 3_500_000_000)
                viewModel.loadData()
            }
        }
        .onAppear {
            viewModel.loadData()
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
