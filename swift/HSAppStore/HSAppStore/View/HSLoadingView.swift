//
//  HSLoadingView.swift
//  HSAppStore
//
//  Created by Sheng ma on 2022/5/15.
//

import SwiftUI

struct HSLoadingView: View {
    var hasMore: Bool
    var loadMoreAction: (() -> Void)?
    
    var body: some View {
        HStack(spacing: 6.0) {
            Spacer()
            if hasMore {
                ProgressView()
                Text("Loading...")
                    .foregroundColor(.gray)
            } else {
                Text("No More Data")
                    .foregroundColor(.gray)
            }
            
            Spacer()
        }
        .frame(height: 30)
        .onAppear {
            loadMore(hasMore: hasMore)
        }
    }
    
    private func loadMore(hasMore: Bool) {
        if hasMore,
           let loadMoreAction = loadMoreAction {
            loadMoreAction()
        }
    }
}

struct HSLoadingView_Previews: PreviewProvider {
    static var previews: some View {
        Group {
            HSLoadingView(hasMore:false)
            HSLoadingView(hasMore:true)
        }
        .previewLayout(PreviewLayout.fixed(width: 390, height: 75))
    }
}
