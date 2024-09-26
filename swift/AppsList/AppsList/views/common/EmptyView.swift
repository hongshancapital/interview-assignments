//
//  EmptyView.swift
//  AppsList
//
//  Created by 余晨正 on 2022/2/15.
//

import SwiftUI

struct EmptyView: View {
    
    private var message: String
    private let action: () -> Void
    @Binding private var refreshing: Bool
    
    init(message: String, refreshing: Binding<Bool>, action: @escaping () -> Void) {
        self.message = message
        _refreshing = refreshing
        self.action = action
    }
    
    var body: some View {
        VStack.init(alignment: .center, spacing: 10) {
            Text(message)
            if refreshing {
                ProgressView()
            } else {
                Button.init("重新加载") {
                    action()
                }
            }
        }
    }
    
}
