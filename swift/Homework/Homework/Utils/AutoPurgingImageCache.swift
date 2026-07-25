//
// Homework
// AutoPurgingImageCache.swift
//
// Created by wuyikai on 2022/4/28.
// 
// 

import Foundation
import UIKit

protocol ImageCache {
    func addImageData(_ imageData: Data, with identifier: String)
    func removeImageData(with identifier: String)
    func removeAllImageData()
    func imageData(with identifier: String) -> Data?
}

class AutoPurgingImageCache: ImageCache {
    
    var cachedImages: [String: Data] = [:]
    let synchronizationQueue = DispatchQueue(label: "com.image.cache-\(UUID())", attributes: .concurrent)
    
    deinit {
        NotificationCenter.default.removeObserver(self)
    }
    
    init() {
        NotificationCenter.default.addObserver(
            self,
            selector: #selector(removeAllImageData),
            name: UIApplication.didReceiveMemoryWarningNotification,
            object: nil)
    }
    
    func addImageData(_ imageData: Data, with identifier: String) {
        synchronizationQueue.async(flags: .barrier) {
            self.cachedImages[identifier] = imageData
        }
    }
    
    func removeImageData(with identifier: String) {
        synchronizationQueue.async {
            self.cachedImages[identifier] = nil
        }
    }
    
    @objc func removeAllImageData() {
        synchronizationQueue.async {
            self.cachedImages.removeAll()
        }
    }
    
    func imageData(with identifier: String) -> Data? {
        synchronizationQueue.sync {
            return self.cachedImages[identifier]
        }
    }
    
}
