//
//  HSAppCell.swift
//  HSAppStore
//
//  Created by Sheng ma on 2022/5/15.
//

import SwiftUI

struct HSAppCell: View {
    var appModel: HSAppModel
    var clickLikeAction: (() -> Void)?
    let defaultName = "HSAppName"
    let defaultDescription = "HSAppName is a default appDescription"
    
    var body: some View {
        Group {
            HStack {
                if appModel.artworkUrl60.isEmpty {
                    HSDeafultImageView()
                } else {
                    HSImageView(appModel.artworkUrl60)
                        .frame(width: 50, height: 50)
                        .clipShape(RoundedRectangle(cornerRadius: 8))
                        .overlay{
                            RoundedRectangle(cornerRadius: 8)
                                .stroke(Color.black, lineWidth:0.2)
                        }
                }
                
                VStack(alignment: .leading) {
                    Text(appModel.trackName.isEmpty ? defaultName : appModel.trackName)
                        .font(.title3)
                        .lineLimit(1)
                    
                    Text(appModel.description.isEmpty ? defaultDescription : appModel.description)
                        .font(.caption)
                        .lineLimit(2)
                }
                
                Spacer()
    
                Image(systemName: appModel.isFavorite ? "heart.fill" : "heart")
                    .foregroundColor(appModel.isFavorite ? .red : .gray)
                    .scaleEffect(appModel.isFavorite ? 1.1 : 1.0)
                    .animation(.default, value: appModel.isFavorite)
                    .onTapGesture {
                        clickFavoriteAction()
                    }
            }.frame(height: 80)
        }.padding(.top, 10)
    }
    
    private func clickFavoriteAction() {
        if let clickLikeAction = clickLikeAction {
            clickLikeAction()
        }
    }
}

struct HSDeafultImageView: View {
    var body: some View {
        Group {
            Image(uiImage: UIImage(imageLiteralResourceName: "defaultImage.jpg"))
                .resizable()
        }
        .frame(width: 50, height: 50)
        .clipShape(RoundedRectangle(cornerRadius: 8))
        .overlay{
            RoundedRectangle(cornerRadius: 8)
                .stroke(Color.black, lineWidth:0.2)
        }
    }
}

struct HSAppCell_Previews: PreviewProvider {
    static let appModel1 = HSAppModel(
        id: 1163852619,
        artworkUrl60: "https://is4-ssl.mzstatic.com/image/thumb/Purple116/v4/b1/87/9a/b1879a2c-6790-a031-cc66-f644bd3dc76c/source/60x60bb.jpg",
        trackName: "Google Chat",
        description: "Google Chat is an intelligent and secure communication and collaboration tool")
    
    static let appModel2 = HSAppModel(
        id: 414478124,
        artworkUrl60: "https://is4-ssl.mzstatic.com/image/thumb/Purple112/v4/40/13/03/401303ec-6693-39b5-71ae-561933261372/AppIcon-0-0-1x_U007emarketing-0-0-0-4-0-0-sRGB-0-0-0-GLES2_U002c0-512MB-85-220-0-0.png/60x60bb.jpg",
        trackName: "WeChat",
        description: "WeChat is more than a messaging and social media app!")
    
    static let appModels = [appModel1, appModel2]
    
    static var previews: some View {
        Group {
            List (appModels) { appModel in
                HSAppCell(appModel: appModel)
                    .listRowSeparator(.hidden)
                    .listRowInsets(EdgeInsets())
                    .listRowBackground(Color.clear)
            }
            .previewLayout(PreviewLayout.fixed(width: 390, height: 300))
        }
    }
}
