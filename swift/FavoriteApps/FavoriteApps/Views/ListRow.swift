//
//  ListRow.swift
//  FavoriteApps
//
//  Created by 刘明星 on 2022/5/6.
//

import SwiftUI

struct ListRow: View
{
    var appInfo: AppInfo
    @Binding var isFavorite: Bool
    @State var factor: CGFloat = 1
    
    var body: some View {
        HStack {
            // 加载网络图片
            AsyncImage(url: URL(string: appInfo.artworkUrl60)) { image in
                image.frame(width: 60, height: 60).clipped().cornerRadius(10)
            } placeholder: {
                ProgressView()
            }
            
            // 中间两个文本视图
            VStack(alignment: .leading, spacing: 5) {
                Text(appInfo.trackName)
                    .font(.title3)
                    .lineLimit(1)
                Text(appInfo.description)
                    .font(.caption2)
                    .lineLimit(2)
            }
            
            Spacer()
            
            // 右侧心形图标
            Image(systemName: appInfo.isFavorite ? "heart.fill" : "heart")
                .foregroundColor(appInfo.isFavorite ? Color.red : Color.gray)
                .onTapGesture {
                    isFavorite.toggle()
                    factor = isFavorite ? 1.2 : 1
                }
                .scaleEffect(factor)
                .animation(.spring(), value: factor)
        }
    }
}

