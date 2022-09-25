//
//  WebImageViewTest.swift
//  GUIKit_Example
//
//  Created by lizhao on 2022/9/19.
//  Copyright © 2022 CocoaPods. All rights reserved.
//

import SwiftUI
import GUIKit


struct CoverImageView: View {
    let image: WebImageType?
    
    var body: some View {
        WebImageView(image: image) { uiImage, state in
            ZStack {
                switch state {
                case .displaying:
                    if state == .displaying {
                        if let image = uiImage {
                            Image(uiImage: image)
                                .resizable()
                                .aspectRatio(contentMode: .fill)
                        } else {
                            Text("No Image")
                        }
                    }
                case .loading:
                    if let image = uiImage {
                        Image(uiImage: image)
                            .resizable()
                            .aspectRatio(contentMode: .fill)
                            .colorMultiply(.init(red: 0.5, green: 0.5, blue: 0.5))
                    }
                    ProgressView()
                        .progressViewStyle(.circular)
                case .error:
                    Text("There was an error!")
                }
            }
        }
        .frame(width: 64, height: 64)
        .clipShape(RoundedRectangle(cornerRadius: UI.Corner.default))
        .background(
            ZStack {
                RoundedRectangle(cornerRadius: UI.Corner.default)
                    .stroke(Color.gray, style: StrokeStyle(lineWidth: UI.Border.bold))
            }
        )
    }
}


struct WebImageViewTest: View {
 
    let url = URL(string: "https://tse1-mm.cn.bing.net/th/id/OIP-C.NgsJe2XzRQvF4mzHzqghaQHaE7?w=258&h=180&c=7&r=0&o=5&pid=1.7")
    @State var webImage: URLWebImage?
    @State var img: UIImage?
    var body: some View {
        VStack {
            
            CoverImageView(image: webImage)
            if let img = self.img {
                Image(uiImage: img)
            }
            
            Button {
//                if let url = self.url {
//                    webImage = URLWebImage(url)
//                }
                
                Task {
                    self.img = try await  URLWebImage(url!).getUIImage()
                }
                
                
                
            } label: {
                Text("load 0")
            }

        }
    }
}

struct WebImageViewTest_Previews: PreviewProvider {
    static var previews: some View {
        WebImageViewTest()
    }
}
