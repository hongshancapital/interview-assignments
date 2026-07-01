//
//  HomeView.swift
//  AppList
//
//  Created by 钟逊超 on 2022/8/1.
//

import SwiftUI

struct HomeView: View {
    
    @ObservedObject var viewModel = HomeViewModel()
    
    private var nextSeq: Int = 0
    
    var body: some View {
        if viewModel.isLoading {
            ActivityIndicatorView(style: .medium).onAppear {
                DispatchQueue.main.asyncAfter(deadline: .now() + 2.0) {
                    self.viewModel.fetchSubsequentAppList()
                }
            }
        } else {
            ScrollView {
                PullToRefreshView(header: RefreshDefaultHeader(), footer: RefreshDefaultFooter()) {
                    ScrollView {
                        ForEach(viewModel.appList) { appModel in
                            AppCell(model: appModel)
                        }
                    }
                }
                .environmentObject(viewModel.listState)
                
                Spacer().frame(height: 60)
            }
            .background(Color(red: 241/255.0, green: 241/255.0, blue: 245/255.0))
            .addPullToRefresh(isHeaderRefreshing: $viewModel.isHeaderRefreshing,
                              onHeaderRefresh: {
                viewModel.fetchPreviousAppList()
            },
                              isFooterRefreshing: $viewModel.isFooterRefreshing,
                              onFooterRefresh: {
                viewModel.fetchSubsequentAppList()
            })
            .navigationBarTitle("App")
        }
    }
}

struct HomeView_Previews: PreviewProvider {
    static var previews: some View {
        HomeView()
    }
}
