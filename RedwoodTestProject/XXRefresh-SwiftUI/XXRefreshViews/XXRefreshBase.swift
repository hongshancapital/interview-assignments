//
//  XXRefreshView.swift
//  RedwoodTestProject
//
//  Created by 徐栋 on 2023/2/8.
//

import SwiftUI
@available(iOS 13.0, OSX 10.15, tvOS 13.0, watchOS 6.0, *)
final class XXRefreshHandler {
    var moreAction: (() async -> Void)?
}
@available(iOS 13.0, macOS 10.15, *)
struct XXActivityIndicator: UIViewRepresentable {

    @Binding var isAnimating: Bool
    let style: UIActivityIndicatorView.Style

    func makeUIView(context: UIViewRepresentableContext<XXActivityIndicator>) -> UIActivityIndicatorView {
        return UIActivityIndicatorView(style: style)
    }

    func updateUIView(_ uiView: UIActivityIndicatorView, context: UIViewRepresentableContext<XXActivityIndicator>) {
        isAnimating ? uiView.startAnimating() : uiView.stopAnimating()
    }
}

