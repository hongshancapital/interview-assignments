//
//  FavoriteButton.swift
//  ChatApps
//
//  Created by XuNing on 2022/7/23.
//

import SwiftUI

struct FavoriteButton: View {
  var isSet: Bool
  var onClick: (() -> Void)?
  
  var body: some View {
    Button {
      onClick?()
    } label: {
      Label("Toggle Favorite", systemImage: isSet ? "heart.fill" : "heart")
        .labelStyle(.iconOnly)
        .foregroundColor(isSet ? .red : .gray)
    }
    // Set to .plain, otherwise if it is placed in a list row,
    // it will cause the entire row to respond to button clicks
    .buttonStyle(.plain)
    .scaleEffect(isSet ? 1.2 : 1)
    .animation(.easeOut(duration: 0.25), value: isSet)
  }
}

struct FavoriteButton_Previews: PreviewProvider {
  static var previews: some View {
    FavoriteButton(isSet: true)
  }
}
