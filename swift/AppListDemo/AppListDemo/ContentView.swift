//
//  ContentView.swift
//  AppListDemo
//
//  Created by arthur on 2022/10/22.
//

import SwiftUI

struct ContentView: View {
    
    @StateObject var viewModel = AppListViewModel()
    
    var body: some View {
        NavigationView {
            if viewModel.isFirstLoading {
                ProgressView()
                    .progressViewStyle(.circular)
            } else {
                if let str = viewModel.errorString {
                    Text("Error: \(str)")
                } else {
                    listContent
                }
            }
        }
        .task {
            await viewModel.refreshing()
        }
    }
}

extension ContentView {
    
    private var listContent: some View {
        List {
            ForEach(viewModel.dataSource, id: \.name) { info in
                AppListCell(info: info)
                    .listRowBackground(Color.clear)
                    .listRowSeparator(.hidden)
            }
            RefreshFooter(viewModel: viewModel)
                .listRowBackground(Color.clear)
                .listRowSeparator(.hidden)
        }
        .navigationTitle("App")
        .listStyle(PlainListStyle())
        .background(Color(uiColor: .systemGroupedBackground))
        .refreshable {
            await viewModel.refreshing()
        }
    }
}


struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        Group {
            ContentView()
        }
    }
}
