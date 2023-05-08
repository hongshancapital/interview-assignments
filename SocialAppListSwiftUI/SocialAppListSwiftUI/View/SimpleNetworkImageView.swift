//
//  SimpleNetworkImageView.swift
//  SocialAppListSwiftUI
//
//  Created by luobin on 2022/3/25.
//

import SwiftUI
import Kingfisher

struct SimpleNetworkImageView: View {
            
    private(set) var url: URL
    
    @State private var isImageLoaded = false
    
    init(url: URL) {
        self.url = url
    }

    var body: some View {
        KFImage(url)
            .placeholder({ process in
                ProgressView()
            })
            .resizable()
            .onSuccess({ result in
                isImageLoaded = true
            })
            .onFailureImage(UIImage(systemName: "photo"))
            .clipShape(RoundedRectangle(cornerRadius: isImageLoaded ? 10 : 0))
            .overlay(content: {
                if isImageLoaded {
                    RoundedRectangle(cornerRadius: 10).stroke(Color("ImageRectangleColor"), lineWidth: 1)
                }
            })
    }

}

struct NetworkImageView_Previews: PreviewProvider {
    static var previews: some View {
        SimpleNetworkImageView(url: URL(string: "https://is5-ssl.mzstatic.com/image/thumb/Purple116/v4/ae/e4/8b/aee48bf2-61a9-e3dc-1412-07d2b4d81d47/source/100x100bb.jpg")!)
    }
}
