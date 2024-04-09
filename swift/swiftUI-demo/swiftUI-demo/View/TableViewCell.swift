//
//  TableViewCell.swift
//  swiftUI-demo
//
//  Created by guo changqing on 2023/3/24.
//

import SwiftUI
import Kingfisher

struct TableViewCell: View {
    
    // 绑定的数据
    @Binding var item: AppModel {
        didSet {
            self.isFavorite = item.isFavorite
        }
    }
    
    // 是否收藏，点击收藏后刷新UI使用
    @State var isFavorite = false
    // 点击收藏回调，上层传入
    public var onTapFavorite: ((_ id: String, _ isFavorite: Bool) -> Void)
    
    var body: some View {
        HStack() {
            KFImage.url(URL(string: item.iconUrl), cacheKey: nil)
                .placeholder { ProgressView().offset(x: 15, y: 15).frame(width: 30, height: 30) }
                .frame(width: 60, height: 60, alignment: .topLeading)
                .scaledToFit()
                .cornerRadius(8)
                .overlay(RoundedRectangle(cornerRadius: 8, style: .continuous).strokeBorder(.gray.opacity(0.3), lineWidth: 1))
            VStack(alignment: .leading, spacing: 10) {
                Text(item.title)
                    .lineLimit(1)
                    .font(.system(size: 18)
                    .weight(.medium))
                    .multilineTextAlignment(.leading)
                Text(item.description)
                    .lineLimit(2)
                    .font(.system(size: 14))
            }.padding(.horizontal, 10)
            Spacer()
            Image(systemName: isFavorite ? "heart.fill" : "heart")
                .frame(width: 44, height: 44)
                .foregroundColor(isFavorite ? .red : .gray)
                .onTapGesture {
                    self.isFavorite = !self.isFavorite
                    self.onTapFavorite(item.id, !item.isFavorite)
                }
        }.padding(.vertical)
    }
}

//struct TableViewCell_Previews: PreviewProvider {
//    static var previews: some View {
//        TableViewCell()
//    }
//}
