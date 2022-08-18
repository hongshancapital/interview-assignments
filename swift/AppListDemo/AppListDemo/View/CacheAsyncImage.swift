//
//  CacheAsyncImage.swift
//  AppListDemo
//
//  Created by HQ on 2022/8/19.
//

import SwiftUI

/// 简单对AsyncImage进行二次封装，增加图片缓存功能；
/// 图片读取优先级： 内存缓存 > 磁盘缓存 > 网络读取；
/// 图片网络下载成功后写入缓存中；
struct CacheAsyncImage<Content> : View where Content : View {

    private let url: URL?
    private let transaction: Transaction
    private let content: (AsyncImagePhase) -> Content

    init(url: URL?,
         scale: CGFloat = 1,
         transaction: Transaction = Transaction(),
         @ViewBuilder content: @escaping (AsyncImagePhase) -> Content) {

        self.url = url
        self.transaction = transaction
        self.content = content
    }

    var body: some View {
        
        if let cachedImage = findCachedImage(url) {
            content(.success(cachedImage))
        } else {
            AsyncImage(url: url, scale: 1.0, transaction: transaction) { phase in
                handleAsyncImagePhase(phase)
            }
        }
    }
    
    /// 处理异步图片操作
    /// - Parameter phase: 异步图片加载操作的当前阶段
    /// - Returns: 返回当前阶段
    private func handleAsyncImagePhase(_ phase: AsyncImagePhase) -> some View {
        
        // 成功加载图片，将图片写入缓存
        if case .success (let image) = phase {
            cacheImage(image)
        }
        
        return content(phase)
    }
    
    /// 缓存图片（内存和磁盘）
    /// - Parameter image: 图片数据
    private func cacheImage(_ image: Image) {
        guard let url = url else {
            return
        }
        
        // 内存缓存
        ImageCache[url] = image
        
        // 磁盘缓存
    }
    
    /// 在缓存中查找图片
    /// - Parameter url: 图片的key
    /// - Returns: 返回图片
    private func findCachedImage(_ url: URL?) -> Image? {
        guard url != nil else {
            return nil
        }
        
        // 内存查找
        if let cached = ImageCache[url!] {
            return cached
        }
        
        // 磁盘查找
        
        return nil
    }
}

/// 图片的内存缓存
fileprivate class ImageCache {
    
    static private var cache: [URL: Image] = [:]

    static subscript(url: URL) -> Image? {
        get{
            ImageCache.cache[url]
        }
        set{
            ImageCache.cache[url] = newValue
        }
    }
}

struct CacheAsyncImage_Previews: PreviewProvider {

    static var previews: some View {
        CacheAsyncImage(url: URL(string: "https://c-ssl.duitang.com/uploads/item/202007/15/20200715230728_LQEQc.jpeg")) { phase in
            switch phase {
            case .success (let image):
                image
                    .resizable()
                    .scaledToFill()
                    .cornerRadius(10)
                    .overlay {
                        RoundedRectangle(cornerRadius: 10)
                            .stroke(.gray, lineWidth: 1.0)
                    }
            case .empty:
                ProgressView()
            case .failure(_):
                Image(systemName: "exclamationmark.circle")
                    .resizable()
                    .scaledToFill()
                    .overlay {
                        RoundedRectangle(cornerRadius: 10)
                            .stroke(.gray, lineWidth: 1.0)
                    }
            @unknown default:
                Image(systemName: "exclamationmark.circle")
                    .resizable()
                    .scaledToFill()
                    .overlay {
                        RoundedRectangle(cornerRadius: 10)
                            .stroke(.gray, lineWidth: 1.0)
                    }
            }
        }
        .frame(width: 60, height: 60, alignment: .center)
    }
}
