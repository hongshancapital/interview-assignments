//
//  CustomAsyncImage.swift
//  Demo
//
//  Created by csmac05 on 2023/2/7.
//

import SwiftUI
import Combine

struct CustomAsyncImage<Content, Placeholder>: View where Content: View, Placeholder: View {
    
    private enum LoadState {
        case loading(URL)
        case success(UIImage)
        case noUrl
        case failure
    }
    
    
    private let placeholder: Placeholder
    private let image: (Image) -> Content
    @State private var state: LoadState
    
    init(
        url: URL?,
        @ViewBuilder image: @escaping (Image) -> Content,
        placeholder: () -> Placeholder) {
            if let url = url {
                _state = State(wrappedValue: .loading(url))
            } else {
                _state = State(wrappedValue: .noUrl)
            }
            self.placeholder = placeholder()
            self.image = image
    }
    
    var body: some View {
        Group {
            switch self.state {
            case .loading(_):
                placeholder
            case .success(let uiImage):
                image(Image(uiImage: uiImage))
            case .noUrl:
                Text("NoUrl")
            case .failure:
                Text("失败")
            }
        }.task {
            guard case .loading(let url) = self.state else {
                return
            }
            do {
                let img = try await ImageDownloader.default.image(from: url)
                self.state = .success(img)
            } catch {
                self.state = .failure
            }
        }
    }
    
}
