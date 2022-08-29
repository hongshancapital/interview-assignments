//
//  CustomView.swift
//  AppList
//
//  Created by 大洋 on 2022/8/22.
//

import SwiftUI
import Combine

struct LoadingView: View {
    @Binding var noMore: Bool
    
    var body: some View {
        HStack(spacing: 10) {
            if noMore {
                Spacer()
                Text("No more data.")
                    .font(.subheadline)
                    .foregroundColor(.gray)
                Spacer()
            } else {
                Spacer()
                ProgressView()
                Text("Loading...")
                    .font(.subheadline)
                    .foregroundColor(.gray)
                Spacer()
            }
        }
    }
}

struct RetryView: View {
    let retryAction: () -> Void
    
    var body: some View {
        Button {
            retryAction()
        } label: {
            HStack(spacing: 10) {
                Spacer()
                Image(systemName: "gobackward")
                    .foregroundColor(.gray)
                Text("Retry")
                    .font(.subheadline)
                    .foregroundColor(.gray)
                Spacer()
            }
            .background(
                Capsule()
                    .stroke(.gray)
                    .padding(-10)
            )
            .frame(minHeight: 70)
            .padding(.horizontal)
        }
    }
}
