//
//  ActivityIndicator.swift
//  ListViewDemo
//
//  Created by HL on 2022/3/16.
//

import SwiftUI

struct ActivityIndicator: UIViewRepresentable {
    
    typealias UIViewType = UIActivityIndicatorView
    
    
    let style: UIActivityIndicatorView.Style
    
    func makeUIView(context: Context) -> UIActivityIndicatorView {
        return UIActivityIndicatorView(style: style)
    }
    
    func updateUIView(_ uiView: UIActivityIndicatorView, context: Context) {
        uiView.startAnimating()
    }
}
