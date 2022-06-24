//
//  FavoriteButton.swift
//  Demo
//
//  Created by 石超 on 2022/6/22.
//

import SwiftUI

struct FavoriteButton: View {
    @Binding var isSet: Bool
    var action: () -> Void
    
    init(isSet: Binding<Bool>, action: @escaping () -> Void = {}) {
        self._isSet = isSet
        self.action = action
    }
    
    var body: some View {
        Button(action: {
            isSet.toggle()
            action()
        }) {
            Image(systemName: isSet ? "heart.fill" : "heart")
                .foregroundColor(isSet ? .red : .gray)
        }
        .buttonStyle(.plain)
    }
}

struct FavoriteButton_Previews: PreviewProvider {
    static var previews: some View {
        FavoriteButton(isSet: .constant(true))
    }
}
