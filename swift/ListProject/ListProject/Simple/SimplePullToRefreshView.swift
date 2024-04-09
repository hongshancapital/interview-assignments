//
//  SimplePullToRefreshView.swift
//  ListProject
//
//  Created by shencong on 2022/6/8.
//

import SwiftUI

struct SimplePullToRefreshView: View {
    let progress: CGFloat
    
    var body: some View {
        Text("下拉刷新")
    }
}

struct SimplePullToRefreshView_Previews: PreviewProvider {
    static var previews: some View {
        SimplePullToRefreshView(progress: 0)
    }
}
