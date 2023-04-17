//
//  ActiveIndicationView.swift
//  hongshandemo
//
//  Created by 林纪涛 on 2023/4/13.
//

import Foundation
import SwiftUI
// SwiftUI 嵌入UIKit View
struct ActiveIndicationView: UIViewRepresentable {
    var style: UIActivityIndicatorView.Style 
    init(style: UIActivityIndicatorView.Style = .large) {
        self.style = style
    }
    
    func makeUIView(context: Context) -> UIActivityIndicatorView {
        let view = UIActivityIndicatorView.init(style: style)
        view.hidesWhenStopped = true
        view.startAnimating()
        return view
    }
    
    func updateUIView(_ uiView: UIActivityIndicatorView, context: Context) {
        print("\(context)")
    }
    
    static func dismantleUIView(_ uiView: UIActivityIndicatorView, coordinator: ()) {
        uiView.stopAnimating()
    }
    
    typealias UIViewType = UIActivityIndicatorView
    
    
}
