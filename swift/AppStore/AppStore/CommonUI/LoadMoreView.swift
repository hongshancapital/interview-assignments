//
//  LoadMoreView.swift
//  AppStore
//
//  Created by liuxing on 2022/4/11.
//

import SwiftUI

struct LoadMoreView: View {
    var hasMoreData: Bool
    var loadMoreAction: (() -> Void)?
    
    var body: some View {
        Group {
            if hasMoreData {
                HStack(spacing: 10) {
                    ProgressView()
                    Text("Loading...")
                        .foregroundColor(.gray)
                }
            } else {
                Text("No more data.")
                    .foregroundColor(.gray)
            }
        }
        .frame(height: 40)
        .frame(maxWidth: .infinity)
        .onAppear {
            if hasMoreData, let loadMoreAction = loadMoreAction {
                loadMoreAction()
            }
        }
    }
}

struct LoadMoreView_Previews: PreviewProvider {
    
    static var previews: some View {
        LoadMoreView(hasMoreData: true)
    }
}
