//
//  LoadingView.swift
//  InterviewPreWork
//
//  Created by jeffy on 2022/5/22.
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

struct ListFooterView: View {
    var noMoreData: Bool
    
    var body: some View {
        content
            .font(.subheadline)
            .foregroundColor(.gray)
    }
    
    @ViewBuilder
    var content: some View {
        if noMoreData {
            HStack {
                Spacer()
                Text("No More Data.")
                Spacer()
            }
        } else {
            HStack {
                Spacer()
                ActivityIndicatorView()
                Text("Loading...")
                Spacer()
            }
        }
    }
}

struct ListFooterView_Previews: PreviewProvider {
    static var previews: some View {
        ListFooterView(noMoreData: false)
        ListFooterView(noMoreData: true)
    }
}
