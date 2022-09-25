//
//  ImageCache.swift
//  Pods-GUIKit_Example
//
//  Created by lizhao on 2022/9/16.
//

import Combine
import UIKit

@MainActor
public class WebImageCache {
    public typealias Publisher = AnyPublisher<Void, Never>
    
    public static let shared = WebImageCache()
    
    private var imagesCache = [WebImageType.ID: ImageCacheContent]()
    private var memoryWarningSubscription: Cancellable?
    
    public init() {
        subscribeToMemoryWarning()
    }
    
    public func remove(_ image: WebImageType) {
        imagesCache.removeValue(forKey: image.id)
    }
    
    public func clear() {
        imagesCache = [:]
    }
    
    public func get(_ image: WebImageType) async throws -> UIImage {
        let imageKey = image.id
        let imgCache = imagesCache[imageKey]
        if imgCache == nil || imgCache!.failed {
            self.cache(image: image)
        }
        
        return try await imagesCache[imageKey]!.getUIImage()
    }
    
    public func getAndSubscribeTo(_ image: WebImageType) async throws -> (UIImage, Publisher) {
        let img = try await get(image)
        guard let publisher = imagesCache[image.id]?.didChangePublisher else {
            throw WebImageError.unexpected
        }
        return (img, publisher)
    }
    
    func cache(image: WebImageType) {
        let imageKey = image.id
        if let content = imagesCache[imageKey] {
            content.image = image
        } else {
            imagesCache[imageKey] = ImageCacheContent(image: image)
        }
    }
    
    private func subscribeToMemoryWarning() {
        let warningNotification = UIApplication.didReceiveMemoryWarningNotification
        let warningPublisher = NotificationCenter.default.publisher(for: warningNotification)
        
        memoryWarningSubscription = warningPublisher.sink { _ in
            self.clear()
        }
    }
}

private class ImageCacheContent {
    var image: WebImageType {
        didSet {
            imageResult.cancel()
            setImageTask(image)
        }
    }
    
    var failed = false
    let didChangePublisher: WebImageCache.Publisher
    
    private(set) var imageResult: Task<WebImageType, Error>! {
        didSet {
            subject.send()
        }
    }
    
    private let subject = PassthroughSubject<WebImageCache.Publisher.Output, WebImageCache.Publisher.Failure>()
    
    init(image: WebImageType) {
        self.image = image
        self.didChangePublisher = subject.share().eraseToAnyPublisher()
        
        setImageTask(image)
    }
    
    
    func getUIImage() async throws -> UIImage {
        return try await imageResult.value.getUIImage()
    }
    
    private func setImageTask(_ image: WebImageType) {
        self.failed = false
        imageResult = Task {
            do {
                return try await image.prepareForDisplay()
            } catch {
                failed = true
                throw error
            }
        }
    }
    
    deinit {
        imageResult.cancel()
        subject.send(completion: .finished)
    }
}
