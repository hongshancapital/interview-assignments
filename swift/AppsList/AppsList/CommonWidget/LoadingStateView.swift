//
//  LoadingStateView.swift
//  AppsList
//
//  Created by 贺建华 on 2023/4/2.
//

import SwiftUI

enum LoadingState {
    case success, failure, loading
}

struct LoadingStateView: View {
    
    @Binding var loadingState: LoadingState
    private var loadingText: String?
    private var failureText: String?
    private var emptyText: String?
    
    init(state: Binding<LoadingState>,
         loadingText: String? = nil,
         failureText: String? = nil,
         emptyText: String? = nil) {
        self._loadingState = state
        self.loadingText = loadingText
        self.failureText = failureText
        self.emptyText = emptyText
    }
    
    var body: some View {
        if loadingState == .loading {
            HStack(spacing: 10) {
                ProgressView()
                if let loadingText = loadingText {
                    Text(loadingText).foregroundColor(.gray)
                }
            }
        } else if loadingState == .success {
            Text(emptyText ?? "No more data.").foregroundColor(.gray)
        } else if loadingState == .failure {
            HStack(spacing: 10) {
                Text(failureText ?? "Network failed.").foregroundColor(.gray)
            }
        }
    }
    
}
