//
//  ListFooterView.swift
//  SwiftUIDemo
//
//  Created by banban on 2022/5/23.
//

import Foundation
import SwiftUI

/// List底部上拉加载更多model
struct ListFooterView : View {
    
    let status : ListViewLoadDataStatus
    
    var body: some View {
        HStack(alignment: .center) {
            Spacer()
            switch (status) {
            case .normal :
                Spacer()
            case .loading:
                LoadingProgressView()
                Spacer().frame(width:10)
                GrayText("加载中，请稍后...")
            case .failed:
                GrayText("数据加载失败！")
            case .noMoreData:
                GrayText("没有更多数据了...")
            }
            Spacer()
        }
        .background(.clear)
    }
}
