//
//  ImageManager.swift
//  AppStore
//
//  Created by huyanling on 2022/5/13.
//

import Foundation
import UIKit

class ImageCache {
    private var imageDict = [String: UIImage]()
    private var imageCacheKeyList = [String]()
    var limitSize: CGFloat = 100 // unit M
    var limitCount: Int = 100
    private var currentSize: CGFloat = 0
    private var lock = NSRecursiveLock()

    func fetchImage(url: String) -> UIImage? {
        // find memory cache
        if imageCacheKeyList.contains(url), let uiImage = imageDict[url] {
            updateCacheList(url: url)
            return uiImage
        }
        // find diskCache
        return nil
    }

    func updateCacheList(url: String) {
        lock.lock()
        imageCacheKeyList.remove(item: url)
        imageCacheKeyList.insert(url, at: 0)
        lock.unlock()
    }

    func addImage(image: UIImage, forKey url: String) {
        lock.lock()
        checkCacheLimit(newImage: image)
        imageDict[url] = image
        imageCacheKeyList.insert(url, at: 0)
        currentSize += imageSize(image: image)
        lock.unlock()
    }

    func removeImage(image: UIImage, forKey url: String) {
        lock.lock()
        imageDict[url] = nil
        imageCacheKeyList.remove(item: url)
        currentSize -= imageSize(image: image)
        lock.unlock()
    }

    func checkCacheLimit(newImage: UIImage) {
        if imageCacheKeyList.count + 1 > limitCount {
            removeCount(count: 1)
        }
        let imageSize = imageSize(image: newImage)
        while currentSize + imageSize > limitSize {
            removeCount(count: 1)
        }
    }

    func imageSize(image: UIImage) -> CGFloat {
        let imageSize = (image.size.width * image.size.height * 4) / (1024 * 1024)
        return imageSize
    }

    func removeSize(size: CGFloat) {
        var removedSize: CGFloat = 0
        for url in imageCacheKeyList.reversed() {
            if let image = imageDict[url] {
                let imageSize = imageSize(image: image)
                removedSize += imageSize
                removeImage(image: image, forKey: url)
            }
            if removedSize >= size {
                break
            }
        }
    }

    func removeCount(count: Int) {
        lock.lock()
        let totalCount = imageCacheKeyList.count
        for i in 0 ..< count {
            if let url = imageCacheKeyList[safe: totalCount - 1 - i],
               let image = imageDict[url] {
                removeImage(image: image, forKey: url)
            }
        }
        lock.unlock()
    }
}

class ImageManager {
    static let shared: ImageManager = ImageManager()

    private let imageCache: ImageCache = ImageCache()

    var timeout: TimeInterval = 30

    func fetchImage(url: String) async throws -> UIImage? {
        // find cache
        if let image = imageCache.fetchImage(url: url) {
            return image
        }
        if let imageUrl = URL(string: url) {
            print("download image \(url)")
            var request = URLRequest(url: imageUrl)
            request.httpMethod = "GET"
            request.timeoutInterval = timeout
            let (imageData, _) = try await URLSession.shared.data(for: request)
            // decode image ?
            if let image = UIImage(data: imageData) {
                imageCache.addImage(image: image, forKey: url)
                return image
            } else {
                print("download error...")
            }
        }
        return nil
    }
}
