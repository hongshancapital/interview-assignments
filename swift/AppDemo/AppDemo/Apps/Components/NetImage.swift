//
//  NetImage.swift
//  AppDemo
//
//  Created by jaly on 2022/11/28.
//

import SwiftUI

struct NetImage<Content> : View where Content : View {
    var imageLoader: AsyncImageLoader
    var placeholder: () -> any View
    @State var image: UIImage?
    
    init<I, P>(url: URL?, scale: CGFloat = 1, @ViewBuilder content: @escaping (Image) -> I, @ViewBuilder placeholder: @escaping () -> P) where Content == _ConditionalContent<I, P>, I : View, P : View {
        self.imageLoader = AsyncImageLoader(url: url)
        self.placeholder = placeholder
    }
    
    @ViewBuilder
    var content: some View {
        if let image = image {
            Image(uiImage: image)
        } else {
            AnyView(self.placeholder())
        }
    }
    
    var body: some View {
        content
            .onAppear {
                self.imageLoader.loadImage { image in
                    self.image = image
                }
            }
            .onDisappear {
                self.imageLoader.cancel()
            }
    }
}

struct NetImage_Previews: PreviewProvider {
    static var previews: some View {
        NetImage(url: URL(string: "https://is3-ssl.mzstatic.com/image/thumb/Purple122/v4/4c/5d/14/4c5d1486-6977-4cf7-668b-c223465d572b/logo_chat_2020q4_color-0-1x_U007emarketing-0-0-0-6-0-0-85-220.png/60x60bb.jpg")) { image in
            image.resizable()
        } placeholder: {
            Text("加载中")
        }

    }
}
