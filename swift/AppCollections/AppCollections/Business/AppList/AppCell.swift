//
//  AppCell.swift
//  AppCollections
//
//  Created by Guang Lei on 2022/3/11.
//

import SwiftUI
import Kingfisher

struct AppCell: View {
    
    let item: AppListModel.Item
    
    var body: some View {
        HStack {
            KFImage.url(item.artworkUrl60)
                .placeholder { ProgressView() }
                .retry(maxCount: 3, interval: .seconds(5))
                .resizable()
                .scaledToFit()
                .frame(width: 60, height: 60)
                .cornerRadius(8)
            VStack(alignment: .leading, spacing: 5) {
                Text(item.trackName)
                    .lineLimit(1)
                    .font(.title2)
                Text(item.description)
                    .lineLimit(2)
                    .font(.footnote)
            }
            Spacer()
            Image(systemName: "heart")
                .foregroundColor(.gray)
                .scaleEffect(1.2)
                .frame(width: 44, height: 44)
        }
        .padding(.horizontal, 12)
        .frame(height: 90)
        .background(Color.white)
        .cornerRadius(8)
    }
}

struct AppCell_Previews: PreviewProvider {
    static var previews: some View {
        let item = AppListModel.Item(
            trackId: 1163852619,
            trackName: "Google Chat",
            description: "Google Chat is an intelligent and secure communication and collaboration tool, built for teams.",
            artworkUrl60: URL(string: "https://is4-ssl.mzstatic.com/image/thumb/Purple116/v4/b1/87/9a/b1879a2c-6790-a031-cc66-f644bd3dc76c/source/60x60bb.jpg")!
        )
        return AppCell(item: item)
    }
}
