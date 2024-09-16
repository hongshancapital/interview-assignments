//
//  ErrorView.swift
//  SwitUI实战
//
//

import SwiftUI

struct ErrorView: View {
    @ObservedObject var artworkFetcher: ArtWorkFetcher
    var body: some View {
        VStack {
            Text("😿")
                .font(.system(size: 80))

            Text(artworkFetcher.errorMessage ?? "")

            Button {
                artworkFetcher.fetchArtWorks()
            } label: {
                Text("Try again")
            }
        }
    }
}
