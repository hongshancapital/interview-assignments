//
//  IndicatorImage.swift
//  Assignment
//
//  Created by Yikai Deng on 2022/6/25.
//

import SwiftUI
import Kingfisher

struct IndicatorImageView: View {
    
    var imageURL: String
    let imageSize = CGSize(width: 64, height: 64)
    
    @State var loadState = true
    
    var body: some View {
        ZStack {
            KFImage(URL(string: imageURL))
                .onSuccess { _ in
                    loadState = false
                }
                .onFailure { _ in
                    loadState = false
                }
                .resizable(resizingMode: .stretch)
                .frame(width: imageSize.width, height: imageSize.height)
                .cornerRadius(8)
                .overlay(
                    RoundedRectangle(cornerRadius: 8, style: .continuous)
                        .stroke(Color("border"), lineWidth: 1)
                )
            
            if loadState {
                ProgressView()
                    .frame(width: imageSize.width, height: imageSize.height)
            }
        }
    }
}

struct IndicatorImageView_Previews: PreviewProvider {
    static var previews: some View {
        IndicatorImageView(imageURL: "https://is4-ssl.mzstatic.com/image/thumb/Purple112/v4/cb/c7/94/cbc79405-0d10-6dcb-af1b-4f74be6d2d6d/AppIcon-0-0-1x_U007emarketing-0-0-0-7-0-0-sRGB-0-0-0-GLES2_U002c0-512MB-85-220-0-0.png/100x100bb.jpg")
    }
}
