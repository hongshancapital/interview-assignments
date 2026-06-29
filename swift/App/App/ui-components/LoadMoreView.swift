//
//  LoadMoreView.swift
//  App
//
//  Created by august on 2022/3/22.
//

import SwiftUI

struct LoadMoreView: View {
    
    @Binding var hasMoreData: Bool
    
    var body: some View {
        Group {
            if hasMoreData {
                HStack(spacing: 8) {
                    ProgressView()
                    Text("loading...")
                }
            } else {
                Text("No more Data.")
            }
        }
        .frame(maxWidth: .infinity, alignment: .center)
    }
}

struct LoadMoreView_Previews: PreviewProvider {
    
    @State static var hasMoreData = false
    
    static var previews: some View {
        LoadMoreView(hasMoreData: $hasMoreData)
    }
}
