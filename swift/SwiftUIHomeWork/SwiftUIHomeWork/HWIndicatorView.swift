//
//  HWIndicatorView.swift
//  SwiftUIHomeWork
//
//  Created by Yu jun on 2022/6/26.
//

import SwiftUI

struct HWIndicatorView: UIViewRepresentable {
    let style: UIActivityIndicatorView.Style
    var isShowing = false
    func makeUIView(context: Context) -> UIActivityIndicatorView {
        return UIActivityIndicatorView(style: style)
    }
    
    func updateUIView(_ uiView: UIActivityIndicatorView, context: Context) {
        isShowing ? uiView.startAnimating() : uiView.stopAnimating()
    }
    
    
    
    typealias UIViewType = UIActivityIndicatorView
    
}


