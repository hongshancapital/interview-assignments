//
//  ChatAppRow.swift
//  ChatApps
//
//  Created by XuNing on 2022/7/23.
//

import SwiftUI

struct ChatAppRow: View {
  var app: ChatApp
  var onClickFavoriteButton: (() -> Void)?
  
  var body: some View {
    HStack {
      AppIcon(url: app.artworkUrl100)
        .frame(width: 58, height: 58)
        .padding(.vertical, 18)
      
      VStack(alignment: .leading, spacing: 6) {
        Text(app.trackName)
          .font(.headline)
        Text(app.description)
          .font(.system(size: 12))
      }
      .lineLimit(2)
      
      Spacer()
      
      FavoriteButton(isSet: app.isFavorite, onClick: onClickFavoriteButton)
    }
    .padding(.horizontal, 18)
    .background(.white)
    .cornerRadius(10)
  }
}

struct AppRow_Previews: PreviewProvider {
  static let app1 = ChatApp(
    trackId: 1163852619,
    trackName: "Google Chat",
    description: "One-line description.",
    artworkUrl100: "https://is4-ssl.mzstatic.com/image/thumb/Purple112/v4/c1/1a/2c/c11a2c71-c250-d676-50ce-0848888b2c2c/logo_chat_2020q4_color-0-1x_U007emarketing-0-0-0-6-0-0-85-220.png/100x100bb.jpg")
  static let app2 = ChatApp(
    trackId: 985746746,
    trackName: "Discord - Chat, Talk & Hangout",
    description: "Discord is where you can make a home for your communities and friends. Where you can stay close and have fun over text, voice, and video chat.",
    artworkUrl100: "https://is5-ssl.mzstatic.com/image/thumb/Purple112/v4/c4/be/01/c4be01d7-2062-5172-094c-729761a3f876/AppIcon-0-0-1x_U007emarketing-0-0-0-7-0-0-sRGB-0-0-0-GLES2_U002c0-512MB-85-220-0-0.png/100x100bb.jpg",
    isFavorite: true
  )
  
  static var previews: some View {
    Group {
      ChatAppRow(app: app1)
        .previewLayout(.fixed(width: 375, height: 92))
      ChatAppRow(app: app2)
        .previewLayout(.fixed(width: 375, height: 92))
    }
  }
}
