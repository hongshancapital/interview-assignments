//
//  ImageLoader.swift
//  AppsList
//
//  Created by 余晨正 on 2022/2/15.
//

import Foundation
import UIKit

class ImageLoader {
    
    static let shared = ImageLoader()
    let store = ImageStore()
    
    func loadImage(with url: String) async -> (UIImage?, ImageLoaderError?) {
        if let image = store.get(key: url) {
            return (image, nil)
        }
        guard let url = URL.init(string: url) else {
            return (nil, ImageLoaderError.init(code: .invalidUrl, message: "非法的图片地址"))
        }
        let request = URLRequest.init(url: url)
        do {
            let (data, _) = try await URLSession.shared.data(for: request)
            guard let image = UIImage.init(data: data) else {
                return (nil, ImageLoaderError.init(code: .invalidImageData, message: "非法的图片数据"))
            }
            store.set(image: image, for: url.absoluteString)
            return (image, nil)
        } catch {
            print("图片下载失败")
        }
        return (nil, ImageLoaderError.init(code: .requestFail, message: "请求失败"))
    }
}

class ImageLoaderError: Error {
    
    enum Code: Int {
        case invalidUrl
        case invalidImageData
        case requestFail
    }
    
    var message: String
    
    init(code: Code, message: String) {
        self.message = message
    }
    
}

class ImageStore {
    
    var cache = [String: UIImage]()
    
    func set(image: UIImage, for key: String) {
        cache[key] = image
    }
    
    func get(key: String) -> UIImage? {
        return cache[key]
    }
    
}
