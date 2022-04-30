//
// Homework
// WebImageView.swift
//
// Created by wuyikai on 2022/4/29.
// 
// 

import SwiftUI

struct WebImageView: View {
    @ObservedObject var imageDownLoader: ImageDownLoader
    
    init(_ url: String) {
        imageDownLoader = ImageDownLoader(url: url)
    }
    
    var body: some View {
        if let data = self.imageDownLoader.imageData,
            let uiImage = UIImage(data: data) {
            Image(uiImage: uiImage)
                .resizable()
                .aspectRatio(contentMode: .fit)
        } else {
            ActivityIndicatorView()
        }
    }
}

struct WebImageView_Previews: PreviewProvider {
    static var previews: some View {
        WebImageView(.sampleImageURL)
    }
}
