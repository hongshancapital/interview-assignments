//
//  AsyncImageLoader.swift
//  AppDemo
//
//  Created by jaly on 2022/11/28.
//

import Foundation
import UIKit

class AsyncImageLoader {
    var url: URL?
    var task: URLSessionDataTask?
    
    init(url: URL?) {
        self.url = url
    }
    
    func loadImage(completion: @escaping (UIImage?) -> ()) {
        guard let requestURL = url else {
            completion(nil)
            return
        }
        self.task = URLSession.shared.dataTask(with: requestURL) { data, response, error in
            if let data = data {
                if let image = UIImage(data: data) {
                    completion(image)
                }
            } else {
                completion(nil)
            }
        }
        self.task?.resume()
    }
    
    func cancel() {
        self.task?.cancel()
    }
}
