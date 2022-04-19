//
//  AppItemView.swift
//  SwiftUIHW
//
//  Created by 施治昂 on 4/14/22.
//

import SwiftUI

struct AppItemView: View {
    @State var item: AppItem
    @State private var logoView = AppLogoView()
    var logoSize = CGSize(width: 60, height: 60)
    
    public var body: some View {
        HStack {
            logoView
                .frame(width: logoSize.width, height: logoSize.height)
            VStack(alignment: .leading) {
                Text(item.sellerName)
                    .fontWeight(.bold)
                    .lineLimit(1)
                Text(item.description.filter { !$0.isWhitespace })
                    .lineLimit(2)
            }
            .frame(maxWidth: .infinity)
            Spacer(minLength: 20)
            Image(systemName: item.liked ? "heart.fill" : "heart")
                .resizable()
                .frame(width: 20, height: 20)
                .aspectRatio(contentMode: .fill)
                .onTapGesture {
                    item.liked = !item.liked
                }
                .foregroundColor(item.liked ? .red : .black)
        }
        .onAppear {
            Task {
                self.logoView.image = try await ImageLoader().fetchImage(from: item.artworkUrl100, size: self.logoSize)
            }
        }
    }
}
