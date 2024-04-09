//
//  Cache.swift
//  Demo
//
//  Created by 葬花桥 on 2023/3/16.
//

import UIKit

/// 简单图片内存缓存示例，不做任何过期处理
class DemoCache {
    static let shared = DemoCache()
    
    private var cache = Dictionary<String, UIImage>()
    
    public func set(key: String, image: UIImage) {
        cache[key] = image
    }
    
    public func image(key: String) -> UIImage? {
        cache[key]
    }
}
