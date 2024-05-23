//
//  HMRefreshHeaderFooterView.swift
//  SwiftHomework-ZWC
//
//  Created by 油菜花 on 2022/2/16.
//

import SwiftUI

struct RefreshHeaderView: View {
    @Environment(\.headerRefreshData) private var headerRefreshData
    
    var body: some View {
        let refreshState = headerRefreshData.refreshState
        switch refreshState {
        case .stopped:
                ProgressView("下拉刷新")
                    .frame(height: 60)

        case .triggered:
                ProgressView("松手加载")
                    .frame(height: 60)
            
        case .loading:
            ProgressView("加载中...")
                .frame(height: 60)
            
        case .invalid:
            Spacer()
                .padding()
                .frame(height: 60)
        }
    }
}

struct RefreshFooterView: View {
    @Environment(\.footerRefreshData) private var footerRefreshData
    
    @Binding var isHaveMorData: Bool
    var body: some View {
        let state = footerRefreshData.refreshState
        switch state {
        case .invalid:
            Spacer()
                .padding()
                .frame(height: 60)
            
        case .stopped:
            if isHaveMorData {
                    Text("上拉加载更多")
                        .font(.system(size: 18))
                        .padding()
                        .frame(height: 60)
            } else {
                    Text("没有更多了")
                        .font(.system(size: 18))
                        .padding()
                        .frame(height: 60)
            }
            
        case .triggered:
                Text("松手加载")
                    .font(.system(size: 18))
                    .padding()
                    .frame(height: 60)
            
        case .loading:
            ProgressView("加载中...")
                .padding()
                .frame(height: 60)
                
        }
    }
}
