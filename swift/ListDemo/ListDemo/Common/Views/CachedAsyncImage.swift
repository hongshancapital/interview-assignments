//
//  CachedAsyncImage.swift
//  ListDemo
//
//  Created by kent.sun on 2023/2/2.
//

import Foundation
import SwiftUI
import os.log

struct CachedAsyncImage<Content>: View where Content: View {
    
    private let url: URL?
    private let scale: CGFloat
    private let transaction: Transaction
    private let content: (AsyncImagePhase) -> Content
    
    init(
        url: URL?,
        scale: CGFloat = 1.0,
        transaction: Transaction = Transaction(),
        @ViewBuilder content: @escaping (AsyncImagePhase) -> Content
    ) {
        self.url = url
        self.scale = scale
        self.transaction = transaction
        self.content = content
    }
    
    var body: some View {
        if let url = url, let cached = ImageCache[url] {
            content(.success(cached))
        } else {
            AsyncImage(url: url, scale: scale, transaction: transaction) { phase in
                cacheAndRender(phase: phase)
            }
        }
    }
    
    func cacheAndRender(phase: AsyncImagePhase) -> some View {
        if let url = url, case .success(let image) = phase {
            ImageCache[url] = image
        }
        
        return content(phase)
    }
}

struct CachedAsyncImage_Previews: PreviewProvider {
    static let sampleUrl = URL(string: "https://is3-ssl.mzstatic.com/image/thumb/Purple116/v4/1b/18/d4/1b18d4df-f2e9-9d16-037c-dc8253a12bc7/AppIcon-0-0-1x_U007emarketing-0-7-0-0-85-220.png/100x100bb.jpg")!

    static var previews: some View {
        VStack{
            CachedAsyncImage(
                url: sampleUrl
            ) { phase in
                switch phase {
                    case .empty:
                        ProgressView()
                    case .success(let image):
                        image
                    case .failure:
                        Image(systemName: "photo")
                    @unknown default:
                        fatalError()
                }
            }
        }
        .previewLayout(.sizeThatFits)
    }
}

fileprivate class ImageCache {
    static private var cache: [URL: Image] = [:]

    static subscript(url: URL) -> Image? {
        get {
            ImageCache.cache[url]
        }
        set {
            ImageCache.cache[url] = newValue
        }
    }
}
