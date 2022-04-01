//
//  AppItemView.swift
//  Demo
//
//  Created by 杨立鹏 on 2022/3/30.
//

import SwiftUI

struct AppItemView: View {
    let item: Item

    var isLiked: Bool

    var likeStateChanged: () -> Void

    var body: some View {
        HStack(spacing: 6) {
            // 图标
            AsyncImage(url: URL(string: item.artworkUrl60)!, content: { image in
                image.resizable()
            }, placeholder: {
                ProgressView()
            })
                .frame(width: 48, height: 48)
                .cornerRadius(10)
                .padding([.vertical, .leading], 16)

            // 标题和描述
            VStack(alignment: .leading, spacing: 8) {
                Text(item.trackCensoredName)
                    .fontWeight(.medium)
                    .lineLimit(1)
                Text(item.description)
                    .lineLimit(2)
                    .font(.system(size: 14))
            }
            .frame(maxWidth: .infinity, alignment: .leading)

            // 爱心按钮
            Button {
                self.likeStateChanged()
            } label: {
                if isLiked {
                    Image(systemName: "heart.fill")
                        .foregroundColor(.red)
                        .padding()

                } else {
                    Image(systemName: "heart")
                        .foregroundColor(.gray)
                        .padding()
                }
            }
            .buttonStyle(.plain)
        }
        .background(.white)
        .cornerRadius(5)
        .listRowInsets(EdgeInsets(top: 0, leading: 16, bottom: 10, trailing: 16))
    }
}

struct AppItemView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
