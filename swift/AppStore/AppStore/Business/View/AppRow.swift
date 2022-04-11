//
//  AppRow.swift
//  AppStore
//
//  Created by liuxing on 2022/4/11.
//

import SwiftUI

struct AppRow: View {
    
    var appModel: AppModel
    var tapFavoriteAction: (() -> Void)?
    
    var body: some View {
        HStack {
            WebImage(imageUrl: appModel.artworkUrl60)
                .frame(width: 60, height: 60)
            
            VStack(alignment: .leading, spacing: 5) {
                Text(appModel.trackName)
                    .font(.title3)
                    .lineLimit(1)
                
                Text(appModel.description)
                    .font(.caption)
                    .lineLimit(2)
            }
            
            Spacer()
            
            Image(systemName: appModel.isFavorite ? "heart.fill" : "heart")
                .foregroundColor(appModel.isFavorite ? .red : .gray)
                .scaleEffect(appModel.isFavorite ? 1.2 : 1)
                .animation(.default, value: appModel.isFavorite)
                .onTapGesture {
                    if let tapFavoriteAction = tapFavoriteAction {
                        tapFavoriteAction()
                    }
                }
        }
        .padding(12)
        .background(.white)
        .cornerRadius(12)
    }
}

struct AppRow_Previews: PreviewProvider {
    static var previews: some View {
        AppRow(appModel: AppModel(
            id: 1163852619,
            artworkUrl60: URL(string: "https://is3-ssl.mzstatic.com/image/thumb/Purple112/v4/73/df/36/73df361c-aa5a-7498-540a-0a83e57dfdf0/source/60x60bb.jpg")!,
            trackName: "Viber Messenger: Chats &amp; Calls",
            description: "Viber is the FREE, simple, fast, and secure messaging and calling app.")
        )
    }
}
