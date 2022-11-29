//
//  LoadingView.swift
//  AppDemo
//
//  Created by jaly on 2022/11/28.
//

import SwiftUI

struct LoadingView: UIViewRepresentable {
    typealias UIViewType = UIActivityIndicatorView
    
    var loading: Bool
    var style: UIActivityIndicatorView.Style
    init(loading: Bool = true, style: UIActivityIndicatorView.Style = .medium ) {
        self.loading = loading
        self.style = style
    }
    func makeUIView(context: Context) -> UIActivityIndicatorView {
        UIActivityIndicatorView(style: style)
    }
    
    func updateUIView(_ uiView: UIActivityIndicatorView, context: Context) {
        loading ? uiView.startAnimating() : uiView.stopAnimating()
    }
}

struct LoadingView_Previews: PreviewProvider {
    static var previews: some View {
        LoadingView()
    }
}
