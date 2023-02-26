//
//  ArtworkListRow.swift
//  Homework
//
//  Created by forever on 2023/2/26.
//

import SwiftUI

struct ArtworkListRow: View {

    @State var isFavorite: Bool = false

    var artwork: Artwork

    var body: some View {
        HStack {
            ArtworkImage(url: artwork.artworkUrl60)
                .cornerRadius(8)
                .frame(width: 50, height: 50)
                .padding(.trailing)

            VStack(alignment: .leading) {
                Text(artwork.trackCensoredName)
                    .font(.subheadline)
                Text(artwork.trackCensoredName)
                    .font(.body)
            }.lineLimit(1)
            Spacer()
            FavoriteButton(isSet: $isFavorite)
        }
        .cornerRadius(10)
    }
}

struct ArtworkListRow_Previews: PreviewProvider {

    static var artworks: [Artwork] {
        [
            Artwork(artworkUrl60: "https://is5-ssl.mzstatic.com/image/thumb/Purple126/v4/7a/49/dd/7a49dd34-3a75-0dc8-9f9c-4531bbfe9fe6/AppIcon-0-0-1x_U007emarketing-0-7-0-0-85-220.png/60x60bb.jpg",
                    trackCensoredName: "trackCensoredName-trackCensoredName",
                    artistId: 1,
                    artistName: "dsf"),
            Artwork(artworkUrl60: "https://is5-ssl.mzstatic.com/image/thumb/Purple126/v4/7a/49/dd/7a49dd34-3a75-0dc8-9f9c-4531bbfe9fe6/AppIcon-0-0-1x_U007emarketing-0-7-0-0-85-220.png/60x60bb.jpg",
                    trackCensoredName: "trackCensoredName-trackCensoredName",
                    artistId: 1,
                    artistName: "dsf"),
            Artwork(artworkUrl60: "https://is5-ssl.mzstatic.com/image/thumb/Purple126/v4/7a/49/dd/7a49dd34-3a75-0dc8-9f9c-4531bbfe9fe6/AppIcon-0-0-1x_U007emarketing-0-7-0-0-85-220.png/60x60bb.jpg",
                    trackCensoredName: "trackCensoredName-trackCensoredName",
                    artistId: 1,
                    artistName: "dsf")
        ]
    }

    static var previews: some View {
        Group {
            ArtworkListRow(artwork: artworks[0])
            ArtworkListRow(artwork: artworks[1])
            ArtworkListRow(artwork: artworks[2])
        }
        .previewLayout(.fixed(width: 200, height: 70))
    }
}
