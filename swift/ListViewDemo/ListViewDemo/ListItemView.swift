//
//  ListItemView.swift
//  ListViewDemo
//
//  Created by sky on 2022/9/30.
//

import SwiftUI
import SDWebImageSwiftUI

struct ListItemView: View {
    
    @State var item: Item
    
    func clickFavorite() {
        item.reverseFavorite()
    }
    
    var body: some View {
        HStack(spacing: 10) {
            WebImage(url: URL(string:item.image))
            .resizable()
            .indicator(.activity)
            .transition(.fade(duration: 0.5))
            .scaledToFit()
            .frame(width: CGFloat(60), height: CGFloat(60), alignment: .center)
            .background(Color.green)
            .cornerRadius(8)
            .padding(EdgeInsets(top: 15, leading: 15, bottom: 15, trailing: 0))
            

            VStack(alignment: .leading, spacing: 10, content: {
                Text(item.name)
                    .fontWeight(.bold)
                    .lineLimit(1)
                    .frame(maxWidth: .infinity, alignment: .leading)

                Text(item.desc)
                    .font(.caption)
                    .foregroundColor(.gray)
                    .lineLimit(2)
            })
            
            .frame(maxWidth: .infinity, alignment: .leading)

            Button(action: {self.clickFavorite()}, label: {
                Image(item.isFavorite ? "favoriteicon2" :"favoriteicon1")
                    .resizable()
                    .aspectRatio(contentMode: .fit)
                    .frame(width: CGFloat(30), height: CGFloat(30), alignment: .center)
            }).frame(width: CGFloat(30), height: CGFloat(30), alignment: .center)
                .padding(EdgeInsets(top: 30, leading: 0, bottom: 30, trailing: 15))
        }
        .background(Color.white)
        .cornerRadius(10)
        .frame(height: 90)
        .padding(EdgeInsets(top: 0, leading: 15, bottom: 10, trailing: 15))
    }
}
