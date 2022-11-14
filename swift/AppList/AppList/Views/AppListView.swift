//
//  ContentView.swift
//  AppList
//
//  Created by Xiaojian Duan on 2022/11/13.
//

import SwiftUI

struct AppListView: View {
    
    struct Layout {
        static let spacing: CGFloat = 20
        static let padding: CGFloat = 20
        static let cornerRadius: CGFloat = 10
    }
    
    @ObservedObject var viewModel: AppListViewModel
    
    var body: some View {
        NavigationView {
            Group {
                if viewModel.loadSuccess {
                    list
                } else {
                    ProgressView()
                }
            }
        }
        .onAppear {
            viewModel.refreshData()
        }
    }
    
    var list: some View {
        ScrollView {
            LazyVGrid(columns: [GridItem(.flexible())],
                      spacing: Layout.spacing)
            {
                ForEach(viewModel.apps) { info in
                    AppListCell(info: info, viewModel: viewModel)
                        .listRowSeparator(.hidden)
                        .cornerRadius(Layout.cornerRadius)
                }
                RefreshFooter(viewModel: viewModel)
                    .foregroundColor(Color(.lightGray))
            }
            .listStyle(PlainListStyle())
        }
        .navigationTitle("App")
        .background(Color(uiColor: .systemGroupedBackground))
        .refreshable {
            viewModel.refreshData()
        }
    }
}

struct RefreshFooter: View {
    @StateObject var viewModel: AppListViewModel
    
    var body: some View {
        HStack(alignment: .center, spacing: 20) {
            switch viewModel.state {
            case .hasMoreData:
                Text("Load more")
            case .loading:
                HStack {
                    ProgressView()
                    Text("loading...")
                }
            case .noMoreData:
                Text("No more data.")
            }
        }
        .task {
            viewModel.loadMoreData()
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var vm = AppListViewModel()
    
    static var previews: some View {
        AppListView(viewModel: vm)
    }
}
