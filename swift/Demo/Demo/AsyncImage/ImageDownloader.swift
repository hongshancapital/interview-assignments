//
//  ImageDownloader.swift
//  Demo
//
//  Created by csmac05 on 2023/2/7.
//

import SwiftUI
import Foundation

actor ImageDownloader {
    
    private enum CacheEntry {
        case inProgress(Task<UIImage, Error>)
        case ready(UIImage)
    }
    
    static let `default` = ImageDownloader()
    
    private var cache: [URL: CacheEntry] = [:]
        
    init() {
        URLCache.shared.diskCapacity = 1024*1024*500
        URLCache.shared.memoryCapacity = 1024*1024*500
    }
    
    func image(from url: URL) async throws -> UIImage {
        if let cached = cache[url] {
            switch cached {
            case .ready(let image):
                return image
            case .inProgress(let task):
                return try await task.value
            }
        }
        
        let task = Task<UIImage, Error> {
            try await downloadImage(from: url)
        }
        
        cache[url] = .inProgress(task)
        
        do {
            let image = try await task.value
            cache[url] = .ready(image)
            return image
        } catch {
            cache.removeValue(forKey: url)
            throw error
        }
    }
    
    private func downloadImage(from url: URL) async throws -> UIImage {
        try await withCheckedThrowingContinuation { continuation in
            
            let parseImage: (Data) -> Void = { data in
                if let image = UIImage(data: data) {
                    continuation.resume(returning: image)
                } else {
                    continuation.resume(throwing: NSError(domain: "", code: -1, userInfo: [NSLocalizedDescriptionKey: "图片解码失败"]))
                }
            }
            
            let request = URLRequest(url: url, cachePolicy: .returnCacheDataElseLoad, timeoutInterval: 30)
            if let data = URLCache.shared.cachedResponse(for: request)?.data {
                parseImage(data)
            } else {
                URLSession.shared.dataTask(with: request) { data, response, error in
                    if let data = data, let response = response {
                        let cacheData = CachedURLResponse(response: response, data: data)
                        URLCache.shared.storeCachedResponse(cacheData, for: request)
                        parseImage(data)
                    } else {
                        continuation.resume(throwing: error!)
                    }
                }.resume()
            }
        }
    }
}
