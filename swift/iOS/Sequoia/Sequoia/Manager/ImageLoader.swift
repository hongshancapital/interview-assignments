//
//  ImageLoader.swift
//  Sequoia
//
//  Created by 徐锐 on 2024/4/11.
//

import Combine
import UIKit

class ImageLoader: ObservableObject {
    @Published var image: UIImage?
    
    private(set) var isLoading = false
    private let url: URL
    private var cache: ImageCache?
    private var cancellable: AnyCancellable?
    
    private static let imageProcessingQueue = DispatchQueue(label: "image-loader")
    
    init(image: UIImage? = nil, isLoading: Bool = false, url: URL, cache: ImageCache? = nil, cancellable: AnyCancellable? = nil) {
        self.image = image
        self.isLoading = isLoading
        self.url = url
        self.cache = cache
        self.cancellable = cancellable
    }
    
    init(url: URL, cache: ImageCache? = nil) {
        self.url = url
        self.cache = cache
    }
    
    deinit {
        cancel()
    }
    public func cancel() {
        cancellable?.cancel()
    }
    
    public func load() {
        guard !isLoading else {
            return
        }
        
        if let image = cache?[url] {
            self.image = image
            return
        }
        
        cancellable = URLSession.shared.dataTaskPublisher(for: url)
            .map { UIImage(data: $0.data) }
            .replaceError(with: nil)
            .handleEvents(receiveSubscription: { [weak self] _ in self?.onStart() },
                          receiveOutput: { [weak self] in self?.cache($0) },
                          receiveCompletion: { [weak self] _ in self?.onFinish() },
                          receiveCancel: { [weak self] in self?.onFinish() })
            .subscribe(on: Self.imageProcessingQueue)
            .receive(on: DispatchQueue.main)
            .sink { [weak self] in self?.image = $0 }
    }
    
    private func onStart() {
        isLoading = true
    }
    
    private func onFinish() {
        isLoading = false
    }

    private func cache(_ image: UIImage?) {
        image.map { cache?[url] = $0 }
    }
    
}
