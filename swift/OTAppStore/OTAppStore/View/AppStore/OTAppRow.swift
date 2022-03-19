//
//  OTAppRow.swift
//  OTAppStore
//
//  Created by liuxj on 2022/3/18.
//

import SwiftUI

struct OTAppRow: View {
    var appModel: AppModel
    var tapFavoriteAction: (() -> Void)?
    
    var body: some View {
        Group {
            HStack {
                OTWebImage(appModel.artworkUrl60)
                    .frame(width: 55, height: 55)
                    .clipShape(RoundedRectangle(cornerRadius: 8))
                    .overlay{
                        RoundedRectangle(cornerRadius: 8)
                            .stroke(Color.black, lineWidth:0.2)
                    }
                
                VStack(alignment: .leading) {
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
                    .scaleEffect(appModel.isFavorite ? 1.2 : 1.0)
                    .animation(.default, value: appModel.isFavorite)
                    .onTapGesture {
                        if let tapFavoriteAction = tapFavoriteAction {
                            tapFavoriteAction()
                        }
                    }
            }.frame(height: 83)
                .padding(.horizontal, 14)
                .background(.white)
                .cornerRadius(8)
        }.padding(.top, 10)
    }
}

struct OTAppRow_Previews: PreviewProvider {
    static let appModel1 = AppModel(
        id: 1163852619,
        artworkUrl60: "https://is4-ssl.mzstatic.com/image/thumb/Purple116/v4/b1/87/9a/b1879a2c-6790-a031-cc66-f644bd3dc76c/source/60x60bb.jpg",
        trackName: "Google Chat",
        description: "Google Chat is an intelligent ")
    
    static let appModel2 =
    AppModel(
        id: 382617920,
        artworkUrl60: "https://is2-ssl.mzstatic.com/image/thumb/Purple126/v4/91/6f/8a/916f8a02-6467-51a7-516e-bad1a86203bc/source/60x60bb.jpg",
        trackName: "Viber Messenger: Chats & Calls",
        description: "Viber is the FREE, simple, fast, and secure messaging and calling app. It’s the messenger of choice for hundreds of millions of users worldwide!")
    
    static let appModels = [appModel1, appModel2, appModel1, appModel2]
    
    static var previews: some View {
        List (appModels) {appModel in
            OTAppRow(appModel: appModel)
                .listRowSeparator(.hidden)
                .listRowInsets(EdgeInsets())
                .listRowBackground(Color.clear)
        }
        .previewLayout(PreviewLayout.fixed(width: 390, height: 600))
    }
}

