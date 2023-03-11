//
//  ContentView.swift
//  Homework
//
//  Created by forever on 2023/2/26.
//

import SwiftUI

struct ContentView: View {

    let artworks: [Artwork]

    var body: some View {
        ArtworkList(artworks: artworks)
    }
}

struct ContentView_Previews: PreviewProvider {
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
        ContentView(artworks: artworks)
    }
}
