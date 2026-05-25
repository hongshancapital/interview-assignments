//
//  ActivityIndicator.swift
//  Sequoia
//
//  Created by 徐锐 on 2024/4/11.
//

import Foundation
import SwiftUI

struct ActivityIndicator: UIViewRepresentable {
    let style: UIActivityIndicatorView.Style
    
    func makeUIView(context: Context) -> UIActivityIndicatorView {
        return UIActivityIndicatorView(style: style)
    }
    
    func updateUIView(_ uiView: UIActivityIndicatorView, context: Context) {
        uiView.startAnimating()
    }
}
