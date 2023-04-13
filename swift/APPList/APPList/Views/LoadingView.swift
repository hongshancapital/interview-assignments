//
//  LoadingView.swift
//  APPList
//
//  Created by three on 2023/4/10.
//

import SwiftUI

struct LoadingView: View {
    @Binding var loadingState: LoadingState
    
    var body: some View {
        if loadingState == .loading {
            HStack(spacing: 10) {
                ProgressView()
                Text("Loading...").foregroundColor(.gray)
            }
        } else if loadingState == .noMoreData {
            Text("No more data.").foregroundColor(.gray)
        } else if loadingState == .failed {
            HStack(spacing: 10) {
                Text("Network error.").foregroundColor(.gray)
            }
        }
    }
}
