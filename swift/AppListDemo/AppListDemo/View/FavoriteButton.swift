//
//  FavoriteButton.swift
//  AppListDemo
//
//  Created by arthur on 2022/10/22.
//

import SwiftUI

struct FavoriteButton: View {
    
    @Binding var isFavorited: Bool
    
    var body: some View {
        Button {
            isFavorited.toggle()
        } label: {
            Image(isFavorited ? "love_red" : "love_gray")
                .resizable()
        }
        .buttonStyle(PlainButtonStyle())
    }
}

struct FavoriteButton_Previews: PreviewProvider {
    static var previews: some View {
        VStack {
            FavoriteButton(isFavorited: .constant(false))
                .frame(width: 30, height: 30)
        }
        .padding(.all, 40)
        .background(Color(uiColor: .systemGroupedBackground))
    }
}
