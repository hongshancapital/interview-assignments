//
//  ApplistCell.swift
//  Applist
//
//  Created by santcool on 2023/1/30.
//

import Foundation
import Kingfisher
import SwiftUI

struct ApplistCell: View {
    var viewModel: AppListViewModel
    var appModel: AppModel
    var isLiked: Bool
    var body: some View {
        VStack {
            HStack {
                KFImage(URL(string: "\(appModel.artworkUrl100)"))
                    .placeholder({
                        ProgressView()
                    })
                    .resizable()
                    .aspectRatio(contentMode: .fit)
                    .cornerRadius(10)
                    .frame(width: 51, height: 51, alignment: .center)
                VStack(alignment: .leading) {
                    Text("\(appModel.trackCensoredName)")
                        .lineLimit(1)
                        .font(.headline)
                    Text("\(appModel.description)")
                        .lineLimit(2)
                        .font(/*@START_MENU_TOKEN@*/ .body/*@END_MENU_TOKEN@*/)
                }

                Spacer()
                Button(action: {
                    viewModel.toggleLikedData(appModel: appModel)
                }, label: {
                    Image(systemName: isLiked ? "heart.fill" : "heart")
                        .foregroundColor(isLiked ? .red : .gray)
                        .scaleEffect(isLiked ? 1.3 : 1)
                        .animation(.easeInOut, value: isLiked)
                })
                .buttonStyle(BorderlessButtonStyle())
                .frame(width: 44, height: 44)
            }
            .padding([.top, .leading, .bottom], 10.0) // 按钮给的比较宽，故不给右侧间距
            .background(Color.white)
            .cornerRadius(10)
        }
        .padding(.horizontal, 10.0)
    }
}

struct ApplistCell_Preview: PreviewProvider {
    static var previews: some View {
        let appModel = AppModel(trackId: 382617920, trackCensoredName: "Viber Messenger: Chats & Calls", description: "Viber is a secure, private, fun messaging and calling app", artworkUrl100: "https://is3-ssl.mzstatic.com/image/thumb/Purple116/v4/1b/18/d4/1b18d4df-f2e9-9d16-037c-dc8253a12bc7/AppIcon-0-0-1x_U007emarketing-0-7-0-0-85-220.png/100x100bb.jpg")
        ApplistCell(viewModel: AppListViewModel(page: 0), appModel: appModel, isLiked: true)
    }
}
