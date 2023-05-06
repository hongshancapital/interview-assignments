//
//  EmptyView.swift
//  SocialAppList
//
//  Created by 刘飞 on 2023/4/29.
//

import SwiftUI

struct PlaceholderView<Content: View>: View {
    private var action: (() -> Void)?
    private let content: Content
    
    init(action: (() -> Void)? = nil, @ViewBuilder content: () -> Content) {
        self.action = action
        self.content = content()
    }
    
    var body: some View {
        ZStack {
            if let action = action {
                Color.white
                    .ignoresSafeArea()
                    .onTapGesture {
                        action()
                    }
            } else {
                Color.white.ignoresSafeArea()
            }
            
            content
        }
        .frame(maxWidth: .infinity, maxHeight: .infinity)
    }
}
