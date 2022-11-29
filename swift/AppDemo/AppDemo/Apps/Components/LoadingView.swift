//
//  LoadingView.swift
//  AppDemo
//
//  Created by jaly on 2022/11/28.
//

import SwiftUI

struct LoadingView: UIViewRepresentable {
    typealias UIViewType = UIActivityIndicatorView
    
    var style: UIActivityIndicatorView.Style
    init(style: UIActivityIndicatorView.Style = .medium ) {
        self.style = style
    }
    func makeUIView(context: Context) -> UIActivityIndicatorView {
        let view = UIActivityIndicatorView(style: style)
        view.startAnimating()
        return view
    }
    
    func updateUIView(_ uiView: UIActivityIndicatorView, context: Context) {
        
    }
}

struct LoadingView_Previews: PreviewProvider {
    static var previews: some View {
        LoadingView()
    }
}
