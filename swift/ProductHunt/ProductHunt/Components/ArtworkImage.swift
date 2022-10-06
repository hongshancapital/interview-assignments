//
//  ArtworkImage.swift
//  ProductHunt
//
//  Created by Jinya on 2022/10/6.
//

import SwiftUI

struct ArtworkImage: View {
    let url: URL

    var body: some View {
        AsyncImage(url: url) { image in
            image
                .resizable()
                .scaledToFit()
                .cornerRadius(8, antialiased: true)
                .overlay(
                    RoundedRectangle(cornerRadius: 8, style: .continuous)
                        .stroke(Color(uiColor: .lightGray), lineWidth: 0.5)
                )
        } placeholder: {
            ProgressView()
                .progressViewStyle(.circular)
        }
    }
}

#if DEBUG
struct ArtworkImage_Previews: PreviewProvider {
    static let url = URL(string: "https://is4-ssl.mzstatic.com/image/thumb/Purple126/v4/2b/91/7b/2b917b8b-77cb-e8e8-418b-6fd38e82f814/source/60x60bb.jpg")!

    static var previews: some View {
        HStack {
            ArtworkImage(url: url)
                .frame(width: 100, height: 100)
        }
        .frame(width: 200, height: 200)
        .border(Color.blue)
    }
}
#endif
