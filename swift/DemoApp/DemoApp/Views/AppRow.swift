//
//  AppRow.swift
//  DemoApp
//
//  Created by Gao on 2022/7/9.
//

import SwiftUI

struct AppRow: View {
    @State var appModel: AppModel
    var body: some View {
        HStack{
            AsyncImage(url: URL(string: appModel.iconUrl)){ image in
                    image.resizable()
            }placeholder: {
                ProgressView()
            }
            .frame(width: 50, height: 50, alignment: .center)
            .cornerRadius(8)
            
            VStack(alignment: .leading) {
                Text(appModel.name)
                    .font(.title)
                    .lineLimit(1)
                Text(appModel.description)
                    .font(.subheadline)
                    .lineLimit(2)
            }
            
            Spacer()
            FavoriteButton(isSet: $appModel.isFavorite)
        }
        .padding(10)
        .background(Color(uiColor: .white))
        .cornerRadius(8)
    }
}

struct AppRow_Previews: PreviewProvider {
    static var previews: some View {
        AppRow(appModel: AppModel(
            id: 1,
            iconUrl: "https://is5-ssl.mzstatic.com/image/thumb/Purple126/v4/34/59/8b/34598b2a-4c6d-cec4-a583-da1418c6ba1e/source/60x60bb.jpg",
            name: "LiveMe – Live Stream & Go Live",
            description: "LiveMe is a popular live streaming social network. It allows you to live stream your special moments"))
            .previewLayout(.fixed(width: 300, height: 70))
    }
}
