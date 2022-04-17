//
//  WebImageLoader.swift
//  MyApps
//
//  Created by liangchao on 2022/4/16.
//

import Foundation

let CacheTimeOutInterval: TimeInterval = 7*24*3600
///  get image from cache ffirst, if not find, download then and cache
class ImageLoader: ObservableObject {
    
    @Published var imageData = Data()
    
    init(imageURL: String) {
        guard let url = URL(string: imageURL) else { return }
        
        let cache = URLCache.shared
        let request = URLRequest(url: url, cachePolicy: URLRequest.CachePolicy.returnCacheDataElseLoad, timeoutInterval: CacheTimeOutInterval)
        if let data = cache.cachedResponse(for: request)?.data {
            self.imageData = data
        } else {
            URLSession.shared.dataTask(with: request, completionHandler: { (data, response, error) in
                if let data = data, let response = response {
                let cachedData = CachedURLResponse(response: response, data: data)
                                    cache.storeCachedResponse(cachedData, for: request)
                    DispatchQueue.main.async {
                        self.imageData = data
                    }
                }
            }).resume()
        }
    }
}
