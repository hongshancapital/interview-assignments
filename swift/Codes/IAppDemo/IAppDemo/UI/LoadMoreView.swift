//
//  LoadMoreView.swift
//  IAppDemo
//
//  Created by lee on 2023/3/2.
//

import Foundation
import SwiftUI

struct LoadMoreView: View {
    
    @State  var noMoreData: Bool
    fileprivate var startLoading: (() async -> Void)? = nil
    init(noMoreData: Bool, startLoading: (() async -> Void)? = nil) {
        self.noMoreData = noMoreData
        self.startLoading = startLoading
    }
    
    var body: some View {
        Group {
            if noMoreData {
                Text("no more data")
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
                Task { await startLoading?() }
            }
        }
    }
}

struct LoadMoreView_Previews: PreviewProvider {
    static var previews: some View {
        VStack(spacing: 20) {
            LoadMoreView(noMoreData: true) {
                logger.debug("LoadMoreView start loading")
            }
            LoadMoreView(noMoreData: false)
        }
    }
}
