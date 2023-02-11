//
//  LoadingView.swift
//  SwiftDemo
//
//  Created by xz on 2023/2/4.
//

import SwiftUI

enum LoadStatus {
    case loading
    case failed
    case none
}

struct LoadingView: View {
    @Binding var status: LoadStatus
    
    var isAnimating: Binding<Bool> {
        return Binding {
            return status == .loading
        } set: { _, _ in
            //
        }

    }
    
    var clickAction: ()->Void?
    var body: some View {
        if status != .none {
            HStack {
                Spacer()
                if status == .loading {
                    ActivityIndicator(isAnimating: isAnimating, style: .medium)
                    Text("Loading")
                } else if status == .failed {
                    Button(action: {
                        clickAction()
                    }) {
                        HStack {
                            Image(systemName: "exclamationmark.triangle")
                                .font(.headline)
                                .foregroundColor(.brown)
                            Text("加载失败，点击重试")
                                .foregroundColor(.brown)
                        }
                    
                    }
                }
                Spacer()
            }
        } else {
            AnyView(EmptyView())
        }
    }
}

struct ActivityIndicator: UIViewRepresentable {
    @Binding var isAnimating: Bool
    let style: UIActivityIndicatorView.Style

    func makeUIView(context: UIViewRepresentableContext<ActivityIndicator>) -> UIActivityIndicatorView {
        return UIActivityIndicatorView(style: style)
    }

    func updateUIView(_ uiView: UIActivityIndicatorView, context: UIViewRepresentableContext<ActivityIndicator>) {
        isAnimating ? uiView.startAnimating() : uiView.stopAnimating()
    }
}

