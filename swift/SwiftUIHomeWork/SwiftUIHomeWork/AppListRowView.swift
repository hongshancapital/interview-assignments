//
//  APPListRowView.swift
//  SwiftUIHomeWork
//
//  Created by Yu jun on 2022/6/25.
//

import SwiftUI
import Combine
struct AppListRowView: View {
    var appmessageModel:AppMessageModel
    @EnvironmentObject var publicData: PublicData
    var appIndex: Int {
        publicData.showAppList.firstIndex(where: { $0.id == appmessageModel.id }) ?? -1
    }
    var body: some View {
        HStack {
            AsyncImage(url: URL(string: self.appmessageModel.artworkUrl60), content: { image in
                image.resizable()
            }, placeholder: {
                HWIndicatorView(style: .medium, isShowing: true)
                    .padding()
            })
            .frame(width: 60, height: 60, alignment: .leading)
            .cornerRadius(6)
            VStack {
                HStack {
                    Text(self.appmessageModel.trackName)
                        .font(.headline)
                    Spacer()
                }
                HStack {
                    Text(self.appmessageModel.description)
                        .font(.subheadline)
                        .fixedSize(horizontal: false, vertical: true)
                        .lineLimit(2)
                    Spacer()
                }
            }
            Spacer()
            Button {
                if self.appIndex >= 0 {
                    self.publicData.showAppList[appIndex].isFavorite.toggle()
                    APIClient.shareClient.selectedFavoriteToAll(model: self.publicData.showAppList[appIndex]) { error in
                        
                    }
                }
                
            } label: {
                if  self.appIndex >= 0 {
                    if self.publicData.showAppList[appIndex].isFavorite {
                        Image(systemName: "heart.fill")
                            .foregroundColor(.red)
                            .imageScale(.large)
                    } else {
                        Image(systemName: "heart")
                            .imageScale(.medium)
                            .foregroundColor(.gray)
                    }
                } else {
                    Image(systemName: "heart")
                        .imageScale(.medium)
                        .foregroundColor(.gray)
                }
                
            }
        }
        .padding()
    }
}

struct APPListRowView_Previews: PreviewProvider {
    static var previews: some View {
        
        AppListRowView(appmessageModel: AppMessageModel(id: 382617920, trackName: "Viber Messenger: Chats & Calls", artworkUrl60: "https://is4-ssl.mzstatic.com/image/thumb/Purple112/v4/cb/c7/94/cbc79405-0d10-6dcb-af1b-4f74be6d2d6d/AppIcon-0-0-1x_U007emarketing-0-0-0-7-0-0-sRGB-0-0-0-GLES2_U002c0-512MB-85-220-0-0.png/60x60bb.jpg", description: "Viber is a secure, private, fun messaging and calling app, connecting over a billion people worldwide!\n\nWith group chats, disappearing messages, reminders, and more, you can do it all with Viber!\n\nMake Free Audio and Video Calls\nEnjoy unlimited Viber-to-Viber calls with up to 50 people and make crystal-clear audio and video calls to anyone in the world. ")).environmentObject(PublicData())
    }
}
