//
//  ImageCache.swift
//  Sequoia
//
//  Created by 徐锐 on 2024/4/11.
//

import UIKit
import Foundation

protocol ImageCache {
    subscript(_ url: URL) -> UIImage? { get set }
}

struct TemporarImageCache: ImageCache {
    private let cache: NSCache<NSURL, UIImage> = {
        let cache = NSCache<NSURL, UIImage>()
        cache.countLimit = 100
        cache.totalCostLimit = 1024 * 1024 * 100
        return cache
    }()
    
    subscript(url: URL) -> UIImage? {
        get {
            cache.object(forKey: url as NSURL)
        }
        
        set {
            let keyUrl: NSURL = url as NSURL
            newValue == nil ? cache.removeObject(forKey: keyUrl) : cache.setObject(newValue!, forKey: keyUrl)
        }
    }
}
