//
//  WebImageView.swift
//  MyApps
//
//  Created by liangchao on 2022/4/16.
//

import SwiftUI

struct WebImageView: View {
    @ObservedObject var imageLoader: ImageLoader
    init(_ url: String) {
        imageLoader = ImageLoader(imageURL: url)
    }
    var body: some View {
        if let img = UIImage(data: self.imageLoader.imageData) {
            Image(uiImage: img)
                .resizable()
                .clipped()
        } else {
            ActivityIndicatorView()
        }
    }
}

struct WebImageView_Previews: PreviewProvider {
    static var previews: some View {
        WebImageView("https://avatars.githubusercontent.com/u/75100086?s=200&v=4")
    }
}
