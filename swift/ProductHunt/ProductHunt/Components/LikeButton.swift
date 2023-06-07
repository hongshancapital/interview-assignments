//
//  LikeButton.swift
//  ProductHunt
//
//  Created by Jinya on 2022/10/6.
//

import SwiftUI

struct LikeButton: View {
    @Binding var isLiked: Bool

    var body: some View {
        Button {
            isLiked.toggle()
        } label: {
            Image(systemName: isLiked ? "heart.fill" : "heart")
                .resizable()
                .scaledToFit()
                .frame(width: 22, height: 22)
                .scaleEffect(isLiked ? 1.2 : 1)
                .animation(.easeOut(duration: 0.35), value: isLiked)
        }
        .foregroundColor(isLiked ? .red : .secondary)
    }
}

#if DEBUG
struct HeartButton_Previews: PreviewProvider {
    static var previews: some View {
        VStack {
            LikeButton(isLiked: .constant(true))
            LikeButton(isLiked: .constant(false))
        }
    }
}
#endif
