//
//  AppCell.swift
//  AppCollections
//
//  Created by Guang Lei on 2022/3/11.
//

import SwiftUI
import Kingfisher

struct AppCell: View {
    
    let app: AppModel
    let tapFavor: (AppModel) -> Void
    
    var body: some View {
        HStack {
            KFImage.url(app.artworkUrl60)
                .placeholder { ProgressView() }
                .retry(maxCount: 3, interval: .seconds(5))
                .resizable()
                .scaledToFit()
                .frame(width: 60, height: 60)
                .cornerRadius(8)
            VStack(alignment: .leading, spacing: 5) {
                Text(app.trackName)
                    .lineLimit(1)
                    .font(.title2)
                Text(app.description)
                    .lineLimit(2)
                    .font(.footnote)
            }
            Spacer()
            Image(systemName: app.isFavorite ? "heart.fill" : "heart")
                .foregroundColor(app.isFavorite ? .red : .gray)
                .scaleEffect(app.isFavorite ? 1.4 : 1.2)
                .frame(width: 44, height: 44)
                .animation(.default, value: app.isFavorite)
                .onTapGesture { tapFavor(app) }
        }
        .padding(.horizontal, 12)
        .frame(height: 90)
        .background(Color.white)
        .cornerRadius(8)
    }
}

struct AppCell_Previews: PreviewProvider {
    static var previews: some View {
        let app1 = AppModel(
            trackId: 1163852619,
            trackName: "Google Chat",
            description: "Google Chat is an intelligent and secure communication and collaboration tool, built for teams.",
            artworkUrl60: URL(string: "https://is4-ssl.mzstatic.com/image/thumb/Purple116/v4/b1/87/9a/b1879a2c-6790-a031-cc66-f644bd3dc76c/source/60x60bb.jpg")!,
            isFavorite: false
        )
        let app2 = AppModel(
            trackId: 382617920,
            trackName: "Viber Messenger: Chats & Calls",
            description: "Viber is the FREE, simple, secure messaging and calling app.",
            artworkUrl60: URL(string: "https://is2-ssl.mzstatic.com/image/thumb/Purple116/v4/06/8c/e5/068ce5a0-8a33-41ee-488a-95067d2b241a/source/60x60bb.jpg")!,
            isFavorite: true
        )
        VStack {
            AppCell(app: app1, tapFavor: { _ in })
            AppCell(app: app2, tapFavor: { _ in })
        }
    }
}
