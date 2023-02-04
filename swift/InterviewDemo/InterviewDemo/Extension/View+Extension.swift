//

//
//  View+Extension.swift
//  InterviewDemo
//
//  Created by 霍橙 on 2023/2/2.
//  
//
    

import SwiftUI

extension View {
    // 添加下拉刷新
    func refreshHeader(isRefresh: Binding<Bool>, action: (() -> ())? = nil) -> some View {
        self.background(RefreshHeader(isRefreshing: isRefresh, action: action))
    }
    
    // 添加上拉加载更多
    func refreshFooter(isRefreshing: Binding<Bool>, loadingText: String? = nil, noMoreText: String? = nil, action: (() -> ())? = nil) -> some View {
        self.background(RefreshFooter(isRefreshing: isRefreshing, loadingText: loadingText, noMoreText: noMoreText, action: action))
    }
}

