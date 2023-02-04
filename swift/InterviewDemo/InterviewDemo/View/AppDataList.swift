//

//
//  AppDataList.swift
//  InterviewDemo
//
//  Created by 霍橙 on 2023/2/1.
//  
//
    

import SwiftUI

struct AppDataList: View {
    
    @ObservedObject var viewModel = AppDataListViewModel()
    
    var body: some View {
        if !viewModel.isDataLoaded { // 未加载显示加载页面
            ProgressView()
                .frame(minWidth: 0, maxWidth: .infinity, minHeight: 0, maxHeight: .infinity, alignment: .center)
        } else { // 加载完成显示列表
            ScrollView {
                LazyVStack {
                    ForEach (viewModel.appDataList) { appData in
                        AppDataRow(viewModel: appData)
                            .listRowBackground(Color.clear)
                            .listRowInsets(EdgeInsets(top: 10, leading: 0, bottom: 0, trailing: 0))
                    }
                }
                .padding(EdgeInsets(top: 5, leading: 15, bottom: 0, trailing: 15))
            }
            .refreshHeader(isRefresh: $viewModel.isRefreshing)
            .refreshFooter(isRefreshing: $viewModel.isLoadingMore, noMoreText: viewModel.isNoMore ? "No more data." : nil)
        }
    }
}

struct AppDataList_Previews: PreviewProvider {
    static var previews: some View {
        HomeView()
    }
}
