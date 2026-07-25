//
// Homework
// LoadingView.swift
//
// Created by wuyikai on 2022/4/29.
// 
// 

import SwiftUI

struct ActivityIndicatorView: View {
    var body: some View {
        ActivityIndicatorRepresenter()
    }
}

private struct ActivityIndicatorRepresenter: UIViewRepresentable {
    typealias UIViewType = UIActivityIndicatorView
    
    func makeUIView(context: Context) -> UIActivityIndicatorView {
        let indicator = UIActivityIndicatorView(style: .medium)
        indicator.startAnimating()
        return indicator
    }
    
    func updateUIView(_ uiView: UIActivityIndicatorView, context: Context) {
    }
}

struct ActivityIndicatorView_Previews: PreviewProvider {
    static var previews: some View {
        ActivityIndicatorView()
    }
}
