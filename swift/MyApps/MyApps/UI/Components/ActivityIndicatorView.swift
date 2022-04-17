//
//  ActivityIndicatorView.swift
//  MyApps
//
//  Created by liangchao on 2022/4/16.
//

import SwiftUI

struct ActivityIndicatorView: UIViewRepresentable {
    var style: UIActivityIndicatorView.Style = UIActivityIndicatorView.Style.medium
    
    func makeUIView(context: UIViewRepresentableContext<ActivityIndicatorView>) -> UIActivityIndicatorView {
        return UIActivityIndicatorView(style: style)
    }

    func updateUIView(_ uiView: UIActivityIndicatorView, context: UIViewRepresentableContext<ActivityIndicatorView>) {
        uiView.startAnimating()
    }
}

