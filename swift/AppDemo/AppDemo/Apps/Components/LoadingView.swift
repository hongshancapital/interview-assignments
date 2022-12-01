//
//  LoadingView.swift
//  AppDemo
//
//  Created by jaly on 2022/11/28.
//

import SwiftUI

struct LoadingView: UIViewRepresentable {
    typealias UIViewType = UIActivityIndicatorView
    
    @Binding var isAnimating: Bool
    let style: UIActivityIndicatorView.Style = .medium
 
    func makeUIView(context: Context) -> UIActivityIndicatorView {
        let view = UIActivityIndicatorView(style: style)
        return view
    }
    
    func updateUIView(_ uiView: UIActivityIndicatorView, context: Context) {
        isAnimating ? uiView.startAnimating() : uiView.stopAnimating()
    }
}

struct LoadingView_Previews: PreviewProvider {
    static var previews: some View {
        LoadingView(isAnimating: .constant(true))
    }
}
