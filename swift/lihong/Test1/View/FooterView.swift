//
//  FooterView.swift
//  Test1
//
//  Created by Hong Li on 2022/10/19.
//

import Foundation
import SwiftUI

struct FooterView: View {
    // Action to trigger
    let action: () -> Void

    // Get states from outside
    @Binding var refreshing: Bool
    @Binding var flagNoMore: Bool
    @Binding var flagTrigger: Bool

    public init(refreshing: Binding<Bool>, flagNoMore: Binding<Bool>, flagTrigger: Binding<Bool>, action: @escaping () -> Void) {
        self._refreshing = refreshing
        self._flagNoMore = flagNoMore
        self._flagTrigger = flagTrigger

        self.action = action
    }

    public var body: some View {
        if !flagNoMore && !refreshing && flagTrigger {
            DispatchQueue.main.async {
                self.refreshing = true

                self.action()
            }
        }

        return VStack(alignment: .center, spacing: 0) {
            if refreshing {
                FooterRefreshingView()
            } else if flagNoMore {
                Text("No more data.")
                    .foregroundColor(Color(.lightGray))
                    .padding()
            } else {
                EmptyView()
            }

            Spacer()
        }
        .frame(maxWidth: .infinity)
    }
}
