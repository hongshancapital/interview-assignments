//
//  AppView.swift
//  ListViewDemo
//
//  Created by HL on 2022/3/15.
//

import SwiftUI
import Refresh

struct LVAppView: View {
    
    @ObservedObject var viewModel = LVAppViewModel()
    
    var body: some View {
        NavigationView {
            ScrollView {
                if viewModel.apps.count > 0 {
                    RefreshHeader(refreshing: $viewModel.isRefreshing) {
                        viewModel.reload()
                    } label: { offset in
                        if viewModel.isRefreshing {
                            RefreshingView(text: "Refreshing...")
                        }else {
                            ReadyToRefreshView(offset: offset)
                        }
                    }
                }
//                List(viewModel.apps, id: \.id) { app in
//                    LVAppRow(app: app)
//                        .cornerRadius(8)
//                }
                ForEach(viewModel.apps) { app in
                    LVAppRow(app: app)
                        .cornerRadius(8)
                }
                if viewModel.apps.count > 0 {
                    RefreshFooter(refreshing: $viewModel.isLoadingMore) {
                        viewModel.loadMore()
                    } label: {
                        if viewModel.noMore {
                            Text("No more data.")
                        }else {
                            RefreshingView(text: "Loading...")
                                .padding()
                        }
                    }
                    .noMore(viewModel.noMore)
                    .preload(offset: 50)
                }
            }
            .enableRefresh()
            .overlay(Group {
                if viewModel.apps.count == 0 {
                    ActivityIndicator(style: .medium)
                }else {
                    EmptyView()
                }
            })
            .onAppear(perform: {
                viewModel.requestSearchApp()
            })
            .background(Color("background"))
            .navigationTitle(Text("App"))
        }
    }
    
}

struct LVAppView_Previews: PreviewProvider {
    static var previews: some View {
        LVAppView()
    }
}
