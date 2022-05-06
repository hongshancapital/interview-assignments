//
//  RefreshDefaultViews.swift
//  Demo
//
//  Created by 盼 on 2022/4/15.
//

import SwiftUI

struct RefreshDefaultHeader: View {
    var body: some View {
        ProgressView()
            .padding()
    }
}

struct RefreshDefaultFooter: View {
    var hasMore: Bool = true
    
    var body: some View {
        HStack(alignment: .center, spacing: 10) {
            if hasMore {
                ProgressView()
            }
            Text(hasMore ? "Loading" : "No more Data")
                .foregroundColor(.gray)
        }
        .frame(height: 52)
    }
}
