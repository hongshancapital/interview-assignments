//
//  AppIconImage.swift
//  
//
//  Created by 黄磊 on 2022/4/12.
//

import SwiftUI

struct AppIconImage: View {
    
    let artworkUrl: String
    
    var body: some View {
        AsyncImage(url: URL(string: artworkUrl)) { image in
            image
                .resizable()
                .scaledToFit()
                .cornerRadius(6)
                .clipped()
                .overlay(
                    RoundedRectangle(cornerRadius: 6, style: .circular)
                        .stroke(Color(UIColor.systemGray3), lineWidth: 0.5)
                )
        } placeholder: {
            LoadingView()
        }
    }
}


struct AppIconImage_Previews: PreviewProvider {
    static var previews: some View {
        AppIconImage(artworkUrl: "https://is5-ssl.mzstatic.com/image/thumb/Purple122/v4/c8/54/70/c85470ed-3e6c-f7c4-792e-ef30f4fb5c8d/source/512x512bb.jpg")
    }
}
