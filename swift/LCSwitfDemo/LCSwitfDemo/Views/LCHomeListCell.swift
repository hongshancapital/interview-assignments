//
//  LCHomeListCell.swift
//  LCSwitfDemo
//
//  Created by 梁杰 on 2022/3/23.
//

import SwiftUI

struct LCHomeListCell: View {
    var model : LCHomeListModel
    @State var isFavorite: Bool=false
    var body: some View {
        HStack(){
            LCImageView(imageUrlStr: model.artworkUrl512)
                .frame(width: 60, height: 60, alignment: .center)
                .cornerRadius(12, antialiased: true)
            VStack(alignment: .leading)
            {
                Text(model.trackName).font(.title3).lineLimit(1)
                Text(model.description).font(.body).lineLimit(2)
            }
            .frame(maxWidth: .infinity,maxHeight: .infinity,  alignment: .leading)
            Button(action: {isFavorite = !isFavorite})
            {
                if isFavorite{
                    Image(systemName: "star.fill")
                        .foregroundColor(Color.red)
                } else {
                    Image(systemName: "star")
                        .foregroundColor(Color.gray)
                }
            }
            .frame(width: 40, height: 40, alignment: .center)
        }
        .padding(EdgeInsets(top: 0, leading: 12, bottom: 0, trailing: 12))
        .background(Color.white)
        .frame(height: 100, alignment: .center)
    }
}

