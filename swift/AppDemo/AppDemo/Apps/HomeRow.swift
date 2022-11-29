//
//  HomeRow.swift
//  AppDemo
//
//  Created by jaly on 2022/11/28.
//

import SwiftUI

struct HomeRow: View {
    
    var model: AppModel
    @State
    var isFavorite: Bool = false
    
    var favoriteButton : some View {
        Button {
            self.isFavorite.toggle()
        } label: {
            Image(
                uiImage: UIImage(
                    systemName: isFavorite ? "heart.fill" : "heart"
                )!
            )
            .renderingMode(.template)
            .foregroundColor(isFavorite ? .red : .gray)
        }
    }
    
    var body: some View {
        HStack {
            ZStack {
                Color.red
                Image("")
            }
            .frame(width: 60, height:60)
            VStack(alignment: .leading) {
                Text(model.trackCensoredName ?? "")
                    .font(.headline)
                    .lineLimit(1)
                Spacer()
                    .frame(height: 5)
                Text(model.description ?? "")
                    .font(.subheadline)
                    .lineLimit(2)
            }
            favoriteButton
        }
        .padding(.leading, 8)
        .padding(.trailing, 8)
    }
}

struct HomeRow_Previews: PreviewProvider {
    static let model = AppModel(
        bundleId: "com.google.chat",
        trackCensoredName: "Google Chat",
        artworkUrl60: "https://is3-ssl.mzstatic.com/image/thumb/Purple122/v4/4c/5d/14/4c5d1486-6977-4cf7-668b-c223465d572b/logo_chat_2020q4_color-0-1x_U007emarketing-0-0-0-6-0-0-85-220.png/60x60bb.jpg",
        description: "Google Chat is an intelligent and secure communication and collaboration tool, built for teams. From ad-hoc messaging to topic-based workstream collaboration, Chat makes it easy to get work done where the conversation is happening.\n\n• Group collaboration that allows Google Workspace content creation and sharing (Docs, Sheets, Slides), without having to worry about granting permissions\n• Side by side editors, one click meetings, scheduling, document creation, and shared files, tasks, and events make it easy to get work done\n• Google search functionality, with options to filter for conversations and content that you’ve shared\n• Ready for Enterprise, with the full benefits of Google Workspace security and access controls including Data Loss Prevention, Compliance, Admin Settings, Vault Retention, Holds, Search, and Export")
    
    static var previews: some View {
        HomeRow(model: model)
    }
}
