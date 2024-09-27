//
//  ImageLoader.swift
//  AppList
//
//  Created by haojiajia on 2022/7/9.
//

import Foundation
import SwiftUI
import Combine

struct ImageLoaderView<I : View, P : View> : View {
    
    @ObservedObject var imageLoader: ImageLoader
    
    private var url: String?
    private let content: (Image) -> I
    private let placeholder: () -> P
    private let completion: ((UIImage) -> Void)?
    
    init(url: String?,
         @ViewBuilder content: @escaping (Image) -> I,
         @ViewBuilder placeholder: @escaping () -> P,
         completion: ((UIImage) -> Void)? = nil) {
        self.url = url
        self.content = content
        self.placeholder = placeholder
        self.completion = completion
        self.imageLoader = ImageLoader(url: url ?? "http://apple.com")
    }
    
    var body: some View {
        if let image = imageLoader.image {
            content(Image(uiImage: image))
        } else {
            placeholder()
        }
    }
    
}

class ImageLoader: ObservableObject {
    
    let url: String
    @Published var image: UIImage?
    private let folderName = "AppListImages"
    private let imageName: String
    
    init(url: String) {
        self.url = url
        self.imageName = url.md5
        downloadImage()
    }
    
    private func loadImage() {
        if let image = FileHelper.imageExists(folderName: folderName, imageName: imageName) {
            self.image = image
        } else {
            downloadImage()
        }
    }
    
    private func downloadImage() {
        let token = SubscriptionToken()
        DispatchQueue.global().asyncAfter(deadline: .now() + 1.5) {
            ImageLoadRequest.publisher(self.url)
                .sink { complete in
                    if case .failure(let error) = complete {
                        Log(error)
                    }
                    token.unseal()
                } receiveValue: { [weak self] value in
                    Log("Download image success image data \(value)")
                    guard let self = self, let image =  UIImage(data: value) else {
                        return
                    }
                    self.image = image
                    FileHelper.saveImage(image, folderName: self.folderName, imageName: self.imageName)
                }
                .seal(in: token)
        }
    }
    
}
