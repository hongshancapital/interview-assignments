//
//  LoadingMoreView.swift
//  SwiftDeveloper
//
//  Created by zhe wu on 2022/10/19.
//

import SwiftUI

struct LoadingMoreView: View {
    var isFinished = false

    var body: some View {
        HStack(alignment: .center) {
            Spacer()

            if isFinished {
                Text("No more data.")
            } else {
                ProgressView().padding(.horizontal, 4)
                Text("Loading...")
            }

            Spacer()
        }
        .padding()
    }
}
