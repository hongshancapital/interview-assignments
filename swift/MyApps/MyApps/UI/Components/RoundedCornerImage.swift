//
//  RoundedCornerImage.swift
//  MyApps
//
//  Created by liangchao on 2022/4/16.
//

import SwiftUI

struct RoundedCornerImage: View {
    let cornerRadius: CGFloat = 6
    let imageUrl: String
    var body: some View {
        WebImageView(imageUrl).scaledToFit().cornerRadius(cornerRadius).overlay(
            RoundedRectangle(cornerRadius: cornerRadius, style: .circular)
                .stroke(Color(UIColor.systemGray3), lineWidth: 0.5)
        )
        .frame(maxWidth: .infinity, maxHeight: .infinity)
    }
}

struct RoundedCornerImage_Previews: PreviewProvider {
    static var previews: some View {
        RoundedCornerImage(imageUrl: "https://avatars.githubusercontent.com/u/75100086?s=200&v=4")
    }
}
