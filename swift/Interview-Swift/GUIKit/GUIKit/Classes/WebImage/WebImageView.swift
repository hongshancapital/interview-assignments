//
//  WebImageView.swift
//  ProduceImageType
//
//  Created by lizhao on 2022/9/16.
//

import Combine
import SwiftUI


public enum WebImageViewState {
    case displaying, error, loading
}

public enum WebImageError: Error {
    case uiImageDecodeFailed
    case dataToImageConvertionFailed
    case unexpected
    case failedForTest
    case failed
}

public struct WebImageView<Content: View>: View {
    public typealias Builder = (UIImage?, WebImageViewState) -> Content
    
    let cache: WebImageCache
    let image: WebImageType?
    @ViewBuilder let contentBuilder: Builder
    public init(image: WebImageType?, cache: WebImageCache? = nil, contentBuilder: @escaping Builder) {
        self.image = image
        self.contentBuilder = contentBuilder
        self.cache = cache ?? WebImageCache.shared
    }
    
    @State private var uiImage: UIImage?
    @State private var viewState: WebImageViewState = .loading
    @State private var canceler: Cancellable?
    
    public var body: some View {
        contentBuilder(uiImage, self.viewState)
            .onChange(of: image?.eraseToAnyWebImage()) { newImage in
                fetchImage(newImage, cache: cache)
            }
            .onAppear {
                fetchImage(image, cache: cache)
            }
    }

    private func fetchImage(_ image: WebImageType?, cache: WebImageCache) {
        viewState = .loading
        self.canceler?.cancel()
        Task {
            do {
                if let image = image {
                    let (uiImage, publisher) = try await cache.getAndSubscribeTo(image)
                    self.uiImage = uiImage
                    
                    self.canceler = publisher.sink { _ in
                        fetchImage(self.image, cache: cache)
                    } receiveValue: { _ in
                        fetchImage(self.image, cache: cache)
                    }
                } else {
                    self.uiImage = nil
                }
                viewState = .displaying
            } catch {
                uiImage = nil
                viewState = .error
            }
        }
    }
}

