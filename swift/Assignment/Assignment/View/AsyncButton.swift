//
//  AsyncButton.swift
//  Assignment
//
//  Created by shinolr on 2022/7/29.
//

import SwiftUI

struct AsyncButton<Label: View>: View {
    var action: () async -> Void
    @ViewBuilder var label: () -> Label

    @State private var isPerformingTask = false

    var body: some View {
        Button(
            action: {
                isPerformingTask = true

                Task {
                    await action()
                    isPerformingTask = false
                }
            },
            label: {
                ZStack {
                    label().opacity(isPerformingTask ? 0 : 1)

                    if isPerformingTask {
                        ProgressView()
                    }
                }
            }
        )
        .disabled(isPerformingTask)
    }
}
struct AsyncButton_Previews: PreviewProvider {
    static var previews: some View {
      AsyncButton(action: {
        try? await Task.sleep(nanoseconds: 2_000_000_000)
      }) {
        Text("Tap me")
      }
    }
}
