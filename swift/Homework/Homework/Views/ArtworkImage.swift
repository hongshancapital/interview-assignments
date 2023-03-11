//
//  ArtworkImage.swift
//  Homework
//
//  Created by forever on 2023/2/26.
//

import SwiftUI

struct ArtworkImage: View {

    let url: String

    var body: some View {
        AsyncImage(url: URL(string: url)) { image in
            image.resizable()
            image.scaledToFill()
        } placeholder: {
            ProgressView()
        }
        .frame(width: 50, height: 50)
        .cornerRadius(8)
    }
}

struct ArtworkImage_Previews: PreviewProvider {
    static var previews: some View {
        ArtworkImage(url: "")
    }
}
