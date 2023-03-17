//
//  RefreshDefaultViews.swift
//  ListViewDemo
//
//  Created by sky on 2022/9/30.
//

import SwiftUI


struct RefreshDefaultHeader: View {
    
    @Environment(\.headerRefreshData) private var headerRefreshData
    
    let cellheight:CGFloat = 80
    
    var body: some View {
        let state = headerRefreshData.refreshState
        
        if state == .stopped {
            VStack(alignment:.center,spacing: 0){
                Text("下拉刷新")
                        .font(.system(size: 18))
                        .padding()
                        .frame(height: 20)
            }.frame(height: cellheight)
        }
        if state == .triggered {
            VStack(alignment:.center,spacing: 0){
                Text("松手加载")
                        .font(.system(size: 18))
                        .padding()
                        .frame(height: 20)
            }.frame(height: cellheight)
        }
        if state == .loading {
            ProgressView()
                .padding()
                .frame(height: cellheight)
        }
        if state == .invalid {
            Spacer()
                .padding()
                .frame(height: cellheight)
        }
    }
}

struct RefreshDefaultFooter: View {
    
    @Environment(\.footerRefreshData) private var footerRefreshData
    let cellheight:CGFloat = 80
    
    var body: some View {
        let state = footerRefreshData.refreshState
        
        if state == .stopped {

            VStack(alignment:.center,spacing: 0){
                Text("上拉加载更多")
                        .font(.system(size: 18))
                        .padding()
                        .frame(height: 20)
            }.frame(height: cellheight)
        }
        if state == .noMore {

            VStack(alignment:.center,spacing: 0){
                Text("没有更多了")
                        .font(.system(size: 18))
                        .padding()
                        .frame(height: 20)
            }.frame(height: cellheight)
        }
        if state == .triggered {
            VStack(alignment:.center,spacing: 0){
                Text("松手加载")
                        .font(.system(size: 18))
                        .padding()
                        .frame(height: 20)
            }.frame(height: cellheight)
        }
        if state == .loading {
            ProgressView()
                .padding()
                .frame(height: cellheight)
        }
        if state == .invalid {
            Spacer()
                .padding()
                .frame(height: cellheight)
        }
    }
    
}

