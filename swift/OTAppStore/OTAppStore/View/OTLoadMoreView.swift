//
//  OTLoadMoreView.swift
//  OTAppStore
//
//  Created by liuxj on 2022/3/19.
//

import SwiftUI

struct OTLoadMoreView: View {
    var hasMore: Bool
    var loadMoreAction: (() -> Void)?
    
    var body: some View {
        HStack(spacing: 6.0) {
            Spacer()
            if hasMore {
                ProgressView()
            }
            Text(hasMore ? "Loading..." : "No more Data.")
                .foregroundColor(.gray)
            Spacer()
        }
        .frame(height: 30)
        .onAppear {
            if hasMore,
            let loadMoreAction = loadMoreAction {
                loadMoreAction()
            }
        }
    }
}

struct OTLoadMoreView_Previews: PreviewProvider {
    static var previews: some View {
        Group {
            OTLoadMoreView(hasMore:false)
            OTLoadMoreView(hasMore:true)
        }
        .previewLayout(PreviewLayout.fixed(width: 390, height: 75))
    }
}
