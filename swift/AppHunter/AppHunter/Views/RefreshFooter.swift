//
//  RefreshFooter.swift
//  AppHunter
//
//  Created by zhang shijie on 2023/5/24.
//

import SwiftUI

struct RefreshFooter: View {
    @Binding var noMoreData: Bool
    let loadingAction: () async -> Void
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
        .foregroundColor(.gray).onAppear {
            if !noMoreData {
                DispatchQueue.main.asyncAfter(deadline: .now() + .seconds(1)) {
                    // delayed action
                    Task { await loadingAction() }
                }
            }
        }
    }
}

// struct RefreshFooter_Previews: PreviewProvider {
//    static var previews: some View {
//      RefreshFooter(noMoreData: false) {
//
//      }
//    }
// }
