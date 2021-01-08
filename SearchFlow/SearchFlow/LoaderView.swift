//
//  LoaderView.swift
//  SearchFlow
//
//  Created by evan on 2021/1/7.
//

import SwiftUI

struct LoaderView : UIViewRepresentable {
    let style: UIActivityIndicatorView.Style
    
    func makeUIView(context: Context) -> some UIView {
        let loader = UIActivityIndicatorView(style: style)
        loader.hidesWhenStopped = true
        loader.startAnimating()
        return loader
    }
    
    func updateUIView(_ uiView: UIViewType, context: Context) {}
}
