//
//  PullToRefreshView.swift
//  Memorize
//
//  Created by 费九林 on 2022/9/28.
//

import SwiftUI

struct PullToRefreshView: View {
    let progress: CGFloat
    var body: some View {
        ActivityIndicator(style: .medium)
    }
}

struct PullToRefreshView_Previews: PreviewProvider {
    static var previews: some View {
        PullToRefreshView(progress: 0)
    }
}
