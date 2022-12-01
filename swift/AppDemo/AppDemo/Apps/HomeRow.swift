//
//  HomeRow.swift
//  AppDemo
//
//  Created by jaly on 2022/11/28.
//

import SwiftUI

struct HomeRow: View {
    
    var model: AppModel
    
    @EnvironmentObject
    var store:MainStore<AppState, AppReduce>
    
    var favoriteButton : some View {
        let isFav = store.state.favList.contains(self.model.bundleId)
        return Image(
            uiImage: UIImage(
                systemName: isFav ? "heart.fill" : "heart"
            ) ?? UIImage()
        )
        .renderingMode(.template)
        .foregroundColor(isFav ? .red : .gray)
        .frame(width: 50, height: 50)
        .onTapGesture {
            Task {
                await store.dispatch(action: .toggleFav(self.model.bundleId))
            }
        }
    }
    
    var body: some View {
        HStack {
            ZStack {
                networkImage(url: model.artworkUrl60 ?? "")
            }
            .frame(width: 60, height:60)
            VStack(alignment: .leading) {
                Text(model.trackCensoredName ?? "")
                    .font(.headline)
                    .lineLimit(1)
                    .frame(minWidth: 60, maxWidth: .infinity, alignment: .leading)
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
    
    func networkImage(url: String) -> some View {
        if #available(iOS 15.0, *) {
            return AsyncImage(url: URL(string: url)) { image in
                image.resizable()
            } placeholder: {
                ProgressView()
            }
        } else {
            return NetImage(url: URL(string: url)) { image in
                image.resizable()
            } placeholder: {
                LoadingView(isAnimating: .constant(true))
            }

        }
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
            .environmentObject(MainStore<AppState, AppReduce>())
    }
}
