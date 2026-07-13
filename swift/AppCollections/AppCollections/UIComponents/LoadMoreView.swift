//
//  LoadMoreView.swift
//  AppCollections
//
//  Created by Guang Lei on 2022/3/12.
//

import SwiftUI

struct LoadMoreView: View {
    
    let noMoreData: Bool
    let startLoading: () async -> Void
    
    var body: some View {
        Group {
            if noMoreData {
                Text("No more data.")
            } else {
                HStack(spacing: 8) {
                    ProgressView()
                    Text("Loading...")
                }
            }
        }
        .frame(maxWidth: .infinity)
        .font(.body)
        .foregroundColor(.gray)
        .onAppear {
            if noMoreData == false {
                Task { await startLoading() }
            }
        }
    }
}

struct LoadMoreView_Previews: PreviewProvider {
    static var previews: some View {
        VStack(spacing: 20) {
            LoadMoreView(noMoreData: true, startLoading: {})
            LoadMoreView(noMoreData: false, startLoading: {})
        }
    }
}
