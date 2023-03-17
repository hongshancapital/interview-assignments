//
//  LikeButton.swift
//  Store
//
//  Created by 张欣宇 on 2022/11/3.
//

import SwiftUI

struct RefreshFooter: View {
    var state: RefreshState
    
    var body: some View {
        switch state {
        case .normal(let string):
            Text(string)
        case .refreshing(let string):
            HStack(spacing: 10) {
                ProgressView()
                Text(string)
            }
        case .nomoredata(let string):
            Text(string)
        }
    }
}
