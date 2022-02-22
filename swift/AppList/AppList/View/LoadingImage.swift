//
//  LoadingImage.swift
//  AppList
//
//  Created by jay on 2022/2/22.
//

import SwiftUI
import Kingfisher

struct LoadingImage: View {
    
    @State var loading = true
    
    let imageUrl: String
    let imageSize = CGSize(width: 56, height: 56)
    
    var body: some View {
        ZStack {
            KFImage.url(URL(string: imageUrl)!)
                .onSuccess { _ in
                    loading = false
                }
                .onFailure { _ in
                    loading = false
                }
                .resizable()
                .frame(width: imageSize.width, height: imageSize.height)
                .cornerRadius(8)
                .overlay(
                    RoundedRectangle(cornerRadius: 8)
                        .stroke(Color("border"), lineWidth: 1)
                )
                .opacity(loading ? 0.0 : 1.0)
            
            if loading {
                ProgressView()
                    .frame(width: imageSize.width, height: imageSize.height)
            }
        }
    }
}

struct LoadingImage_Previews: PreviewProvider {
    static var previews: some View {
        LoadingImage(imageUrl: "https://is2-ssl.mzstatic.com/image/thumb/Purple116/v4/ed/ef/ca/edefcae0-f7fd-9531-2218-f9ab82519acb/source/100x100bb.jpg")
    }
}

