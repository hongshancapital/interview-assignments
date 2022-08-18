//
//  AppListPage.swift
//  AppListDemo
//
//  联系方式：微信(willn1987)
//
//  Created by HQ on 2022/8/18.
//
//

import SwiftUI

/// 应用列表页面
struct AppListPage: View {
    @Environment(\.colorScheme) var colorScheme
    
    @ObservedObject var viewModel = AppListViewModel()
    
    var body: some View {
        NavigationView {
            ZStack {
                // loading视图
                if viewModel.listData.count == 0 {
                    ProgressView()
                }
                
                // 列表
                List(0 ..< viewModel.listData.count, id: \.self) { index in
                    AppListRowView(appInfo: $viewModel.listData[index]) {
                        viewModel.updateLikedState(index: index, appId: viewModel.listData[index].appId)
                    }
                    
                    if index + 1 == viewModel.listData.count {
                        loadMoreView()
                    }
                }
                .listStyle(.plain)
                .refreshable {
                    print("下拉刷新")
                    viewModel.refreshData()
                }
            }
            .navigationTitle(viewModel.listData.count == 0 ? "" : "微信:willn1987")
            .background(colorScheme == .dark ? Color(red: 40.0 / 255.0, green: 40.0 / 255.0, blue: 40.0 / 255.0) : Color(red: 240.0 / 255.0, green: 240.0 / 255.0, blue: 240.0 / 255.0))
            .toast(show: $viewModel.showToast, message: viewModel.toastMsg ?? "", systemImage: "xmark", alignment: .left)
        }
    }
    
    ///  "加载更多"视图, 加载列表底部
    /// - Returns: view
    private func loadMoreView() -> some View {
        HStack(alignment: .center, spacing: 5, content: {
            if viewModel.haveMore == true {
                ProgressView()
            }
            
            Text(viewModel.haveMore == true ? "正在加载数据" : "没有更多数据")
                .fontWeight(.light)
                .font(.system(size: 15))
                .onAppear(perform: {
                    viewModel.loadMoreData()
                })
        })
        .listRowSeparator(.hidden)
        .listRowBackground(Color.clear)
        .frame(maxWidth: .infinity, maxHeight: 30, alignment: .center)
    }
}

struct AppListPage_Previews: PreviewProvider {
    static var previews: some View {
        AppListPage().preferredColorScheme(.dark)
    }
}
