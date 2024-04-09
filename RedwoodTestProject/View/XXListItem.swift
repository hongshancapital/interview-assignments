//
//  XXListItem.swift
//  RedwoodTestProject
//
//  Created by 徐栋 on 2023/2/7.
//

import SwiftUI
import SDWebImageSwiftUI
struct XXAdListitem: View {
    public var model: XXAdsItemModel
    @State var isSeleted: Bool = false
    @State var isWaitting: Bool = true
    var body: some View {
        return HStack {
            WebImage(url: URL(string: model.artworkUrl))
                            .placeholder{Color.white}
                            .resizable()
                            .indicator(.activity)
                            .scaledToFit()
                            .frame(width: 56, height:56)
                            .clipped()
                            .cornerRadius(5)
                            .padding([.leading], 16)

            VStack(alignment: .leading) {
                Spacer(minLength: 6)
                Text(model.trackName)
                    .font(.title2)
                    .lineLimit(1)
                Spacer(minLength: 2)
                Text(model.description)
                    .lineLimit(2)
                    .font(.subheadline)
                Spacer(minLength: 6)
            }
            .padding([.leading], 8)
            Spacer()
            Button {
                self.isSeleted.toggle()
            } label: {
                if self.isSeleted {
                    Image(systemName: "heart.fill")
                        .foregroundColor(.red)
                        .font(.system(size: 28))
                } else {
                    Image(systemName: "heart")
                        .foregroundColor(.gray)
                        .font(.system(size: 28))
                }
            }
            .frame(width: 56)
        }
        .background(Color.white)
    }
}


