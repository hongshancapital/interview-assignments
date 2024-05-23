//
//  ImageWithURL.swift
//  AppList
//
//  Created by 王宁 on 2022/4/2.
//
import Foundation
import SwiftUI

struct ImageWithURL: View {
    
    @ObservedObject var imageLoader: ImageLoaderAndCache

    init(_ url: String) {
        imageLoader = ImageLoaderAndCache(imageURL: url)
    }

    var body: some View {
        if(imageLoader.isDone == true){
            Image(uiImage: UIImage(data: self.imageLoader.imageData) ?? UIImage())
                .resizable()
                .clipped()
                .overlay(
                    RoundedRectangle(cornerRadius: 10, style: .continuous)
                        .stroke(Styles.Colors.border, lineWidth: 1)
                )
                .clipShape(RoundedRectangle(cornerRadius: 10))
        }else{
            ProgressView()
        }
         
    }
}

class ImageLoaderAndCache: ObservableObject {
    
    @Published var imageData = Data()
    @Published var isDone = false

    init(imageURL: String) {
        let cache = URLCache.shared
        let request = URLRequest(url: URL(string: imageURL)!, cachePolicy: URLRequest.CachePolicy.returnCacheDataElseLoad, timeoutInterval: 60.0)
        if let data = cache.cachedResponse(for: request)?.data {
            self.imageData = data
            self.isDone = true
        } else {
            URLSession.shared.dataTask(with: request, completionHandler: { (data, response, error) in
                if let data = data, let response = response {
                let cachedData = CachedURLResponse(response: response, data: data)
                                    cache.storeCachedResponse(cachedData, for: request)
                    DispatchQueue.main.async {
                        self.imageData = data
                        self.isDone = true
                    }
                }
            }).resume()
        }
    }
}
