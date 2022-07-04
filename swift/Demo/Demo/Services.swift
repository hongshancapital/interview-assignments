//
//  Services.swift
//  Demo
//
//  Created by 李永杰 on 2022/7/4.
//
 
import Foundation
import Combine
import SwiftUI

class ImageLoader: ObservableObject {
    
    let url: String?
    
    @Published var image: UIImage? = nil
    @Published var isLoading: Bool = false
    
    init(url: String?) {
        self.url = url
    }
    
    func load() {
        
        guard image == nil && !isLoading else { return }
        
        guard let _ = url, let url = URL(string: url!) else {
            return
        }
        let policy: URLRequest.CachePolicy = url.pathExtension.isEmpty ? .reloadRevalidatingCacheData: .returnCacheDataElseLoad
        let request = URLRequest(
            url: url,
            cachePolicy: policy,
            timeoutInterval: 20
        )
        
        isLoading = true
        let task = URLSession.shared.dataTask(with: request) { (data, response, error) in
            DispatchQueue.main.async {
                self.isLoading = false
                guard
                    let httpURLResponse = response as? HTTPURLResponse, httpURLResponse.statusCode == 200,
                    let mimeType = response?.mimeType, mimeType.hasPrefix("image"),
                    let data = data, error == nil,
                    let uImage = UIImage(data: data)
                else {
                    return
                }
                self.image = uImage
            }
        }
        task.resume()
    }
}
