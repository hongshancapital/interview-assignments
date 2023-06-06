//
//  RefreshFooterView.swift
//  demo
//
//  Created by shu li on 2023/4/6.
//

import SwiftUI

struct RefreshFooterView: View {
    
    let isLoadingMore: Bool
    
    var body: some View {
        Group {
            HStack(spacing: .Padding.one) {
                if isLoadingMore {
                    ProgressView()
                }
                Text(isLoadingMore ? "Loading..." : "No more data.")
            }
        }
        .foregroundColor(.secondary)
        .frame(maxWidth: .infinity, alignment: .center)
        .listRowSeparator(.hidden)
        .listRowBackground(Color.clear)
    }
        
}
