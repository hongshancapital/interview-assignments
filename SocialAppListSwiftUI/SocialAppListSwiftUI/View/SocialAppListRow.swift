//
//  SocialAppListRow.swift
//  SocialAppListSwiftUI
//
//  Created by luobin on 2022/3/25.
//

import SwiftUI

struct SocialAppListRow: View {
    
    @Binding var appModel: SocialAppModel
            
    var body: some View {
        HStack {
            SimpleNetworkImageView(url: appModel.artworkUrl60)
                .frame(width: 50, height: 50)
            VStack(alignment: .leading, spacing: 5) {
                Text(appModel.trackName)
                    .lineLimit(1)
                    .font(.system(size: 15).bold())
                Text(appModel.description)
                    .lineLimit(2)
                    .font(.system(size: 12))
            }
            Spacer()
            Button {
                appModel.isFavorite.toggle()
            } label: {
                ZStack {
                    // Favorite animation
                    Image(systemName: "heart.fill")
                        .imageScale(.medium)
                        .scaleEffect(appModel.isFavorite ? 1.2 : 1)
                        .foregroundColor(.accentColor)
                        .opacity(appModel.isFavorite ? 1 : 0)
                        .animation(.easeOut, value: appModel.isFavorite)
                    Image(systemName: "heart")
                        .imageScale(.medium)
                        .foregroundColor(Color("TextSecondaryColor"))
                        .opacity(appModel.isFavorite ? 0 : 1)
                        .animation(.easeOut, value: appModel.isFavorite)
                }
            }
            .frame(width: 30, height: 30)
        }
        .padding(13)
        .background(Color("RowBackgroundColor"))
        .clipShape(RoundedRectangle(cornerRadius: 10))
    }
}

struct SocialAppListRow_Previews: PreviewProvider {
    @State static var appModel: SocialAppModel = {
        let url = URL(string: "https://is5-ssl.mzstatic.com/image/thumb/Purple116/v4/ae/e4/8b/aee48bf2-61a9-e3dc-1412-07d2b4d81d47/source/100x100bb.jpg")!
        let model = SocialAppModel(trackName: "LoopChat: College Chats+Social", description: "LoopChat is the #1 plug for all things college", artworkUrl60: url)
        return model
    }()

    static var previews: some View {
        SocialAppListRow(appModel: $appModel)
            .previewLayout(.fixed(width: 400, height: 70))

    }
}
