//
//  AppInfoRowView.swift
//  APPList
//
//  Created by three on 2023/4/10.
//

import SwiftUI

struct AppListItemView: View {
    @State var item: ListItemModel
    
    var favouriteToggle : (Int) -> ()
    
    var body: some View {
        HStack {
            AsyncImage(url: URL(string: item.imagePath)) { image in
                image.resizable()
                    .aspectRatio(contentMode: .fit)
                    .clipShape(RoundedRectangle(cornerRadius: 8))
            } placeholder: {
                ProgressView()
            }
            .overlay(
                RoundedRectangle(cornerRadius: 8, style: .continuous)
                    .stroke(Color(.init(gray: 0.7, alpha: 1.0)), lineWidth: 1)
            )
            .frame(width: 50, height: 50)
            .padding(.leading, 14)
            .cornerRadius(8)

            VStack(alignment: .leading) {
                Text(item.title)
                    .font(.system(size: 16))
                    .fontWeight(.semibold)
                    .lineLimit(1)
                    .frame(maxWidth: .infinity, alignment: .leading)
                    .accessibilityIdentifier("appListItem_TitleLabel")
                    
                Text(item.info)
                    .font(.system(size: 12))
                    .lineLimit(2)
                    .padding(.top, -8)
                    .accessibilityIdentifier("appListItem_InfoLabel")
            }
            .padding(.leading, 7)
            .frame(maxWidth: .infinity)
                        
            Button(action: {
                withAnimation() {
                    item.isFavourite.toggle()
                }
                self.favouriteToggle(item.trackId)
            }) {
                Image(systemName: item.isFavourite ? "suit.heart.fill" : "suit.heart")
                    .font(.title3)
                    .foregroundColor(item.isFavourite ? .red : .gray)
            }
            .padding(.leading, 14)
            .padding(.trailing, 14)
            .accessibilityIdentifier("appListItem_FavBtn")

        }
        .frame(width: getRect().width - 28)
        .frame(height: 72)
        .background(
            Color.white.cornerRadius(12)
        )
 
    }
}

extension View {
    func getRect() -> CGRect {
        return UIScreen.main.bounds
    }
}
