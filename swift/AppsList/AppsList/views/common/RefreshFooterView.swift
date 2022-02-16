//
//  RefreshFooterView.swift
//  AppsList
//
//  Created by 余晨正 on 2022/2/15.
//

import SwiftUI

struct RefreshFooterView: View {
    
    @Binding var hasMore: Bool
    @Binding var loading: Bool
        
    init(hasMore: Binding<Bool>, loading: Binding<Bool>) {
        _hasMore = hasMore
        _loading = loading
    }
    
    var body: some View {
        GeometryReader { geo in
            HStack(alignment: .center, spacing: 10) {
                if !hasMore {
                    Text("No more data")
                } else if !loading {
                    Text("load more")
                } else {
                    ProgressView()
                    Text("loading...")
                }
            }
            .frame(width: geo.size.width, height: geo.size.height)
        }
    }
}
