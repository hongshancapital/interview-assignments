//
//  AppRow.swift
//  DemoApp
//
//  Created by liang on 2022/5/19.
//

import SwiftUI

struct AppRow: View {
    @State var appModel: AppModel
    var body: some View {
        HStack {
            AsyncImage(url: URL(string: appModel.iconUrl)) { image in
                image.resizable()
            } placeholder: {
                ProgressView()
            }
            .frame(width: 50, height: 50, alignment: .center)
            .cornerRadius(8)
            VStack(alignment: .leading) {
                Text(appModel.name)
                    .font(.headline)
                    .lineLimit(1)
                Text(appModel.description)
                    .font(.subheadline)
                    .lineLimit(2)
            }
            Spacer()
            Image(systemName: appModel.isFavorite ? "heart.fill" : "heart")
                .foregroundColor(appModel.isFavorite ? .red : .black)
                .onTapGesture {
                    appModel.isFavorite.toggle()
                    FavoriteManager
                        .shared
                        .setFavorite(
                            isFavorite: appModel.isFavorite,
                            by: appModel.id
                        )
                }
                .onAppear {
                    appModel.isFavorite =
                    FavoriteManager
                        .shared
                        .isFavorite(by: appModel.id)
                }
        }
        .padding(10)
        .background(Color(uiColor: .white))
        .cornerRadius(8)
    }
}

struct AppRow_Previews: PreviewProvider {
    static var previews: some View {
        let appModel = AppModel(
            id: 1163852619,
            iconUrl:"""
            https://is4-ssl.mzstatic.com/image/thumb/\
            Purple126/v4/e7/a6/28/e7a62890-5938-b2c0-bf38-60d787419d5c/\
            logo_chat_2020q4_color-0-1x_U007emarketing-0-0-0-6-0-0-85-220.png/\
            60x60bb.jpg
            """,
            name: "Google Chat",
            description: """
            Google Chat is an intelligent and secure communication and\
            collaboration tool, built for teams.
            """
        )
        AppRow(appModel: appModel)
            .previewLayout(.fixed(width: 300, height: 70))
    }
}
