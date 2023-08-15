//
//  FavoriteButton.swift
//  SCDTAppList
//
//  Created by freeblow on 2023/2/14.
//

import SwiftUI

struct FavoriteButton: View {
    @Binding var isFavorite: Bool
    
    var body: some View {
        
        Button {
            isFavorite.toggle()
        } label: {
            Image(systemName: self.isFavorite ? "heart.fill" : "heart")
                .resizable()
                .frame(width:20, height: 20)
                .scaleEffect(isFavorite ? 1.2 : 1.0)
                .animation(.easeOut(duration: 0.5), value: isFavorite)
                .foregroundColor(isFavorite ? .red : .secondary)
        }
        .padding(0)

    }
}

#if DEBUG
struct FavoriteButton_Previews: PreviewProvider {
    static var previews: some View {
        VStack{
            FavoriteButton(isFavorite: .constant(true))
            FavoriteButton(isFavorite: .constant(false))
        }
    }
}
#endif
