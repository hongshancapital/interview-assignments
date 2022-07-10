//
//  FavoriteButton.swift
//  DemoApp
//
//  Created by Gao on 2022/7/9.
//

import SwiftUI

struct FavoriteButton: View {
    @Binding var isSet: Bool
    
    var body: some View {
        Button {
            isSet.toggle()
        }label: {
            Label("Toggle Favorite", systemImage: isSet ? "heart.fill": "heart")
                .labelStyle(.iconOnly)
                .foregroundColor(isSet ? .red : .gray)
        }
    }
}

struct FavoriteButton_Previews: PreviewProvider {
    static var previews: some View {
        FavoriteButton(isSet: .constant(false))
    }
}
