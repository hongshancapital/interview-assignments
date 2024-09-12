//
// Homework
// ImageLoader.swift
//
// Created by wuyikai on 2022/4/27.
// 
// 

import Foundation

let imageCache = AutoPurgingImageCache()

class ImageDownLoader: ObservableObject {
    @MainActor @Published var imageData: Data? = nil
    
    init(url: String) {
        Task {
            await download(with: url)
        }
    }
    
    private func download(with urlString: String) async {
        guard let url = URL(string: urlString) else {
            debugPrint("图片链接不正确", urlString)
            return
        }
        
        if let cachedImageData = imageCache.imageData(with: urlString) {
            await setup(cachedImageData)
            return
        }
        // 模拟网络延迟
        try? await Task.sleep(nanoseconds: 1 * 1_000_000_000)
        let request = URLRequest(url: url)
        do {
            let result = try await URLSession.shared.data(for: request, delegate: nil)
            imageCache.addImageData(result.0, with: urlString)
            await setup(result.0)
        } catch {
            debugPrint(error)
        }
    }
    
    @MainActor private func setup(_ data: Data) {
        imageData = data
    }
}
