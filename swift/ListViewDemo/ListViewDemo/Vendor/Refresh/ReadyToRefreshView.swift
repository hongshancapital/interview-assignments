//
//  ReadyToRefreshView.swift
//  ListViewDemo
//
//  Created by HL on 2022/3/16.
//

import SwiftUI

struct ReadyToRefreshView: View {
    
    let offset: CGFloat
    
    var body: some View {
        Text(offset < 1 ? "继续下拉刷新" : "松手立即刷新")
    }
}

struct ReadyToRefreshView_Previews: PreviewProvider {
    static var previews: some View {
        ReadyToRefreshView(offset: 0)
    }
}
