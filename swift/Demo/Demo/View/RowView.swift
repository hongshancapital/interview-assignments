//
//  RowView.swift
//  Demo
//
//  Created by 李永杰 on 2022/7/5.
//

import SwiftUI

struct RowView: View {
    
    @State var info: AppInfo
    
    var body: some View {
        HStack {

            AppImageView(imageUrl: info.iconUrl)
            .frame(width: 50, height: 50)
            .cornerRadius(10)
            VStack(alignment: .leading, spacing: 4) {
                Text(info.name)
                    .font(.headline)
                    .lineLimit(1)
                Text(info.description)
                    .font(.subheadline)
                    .lineLimit(2)
            }
            Spacer()
            Image(systemName: info.isLiked ? "heart.fill" : "heart")
                .foregroundColor(info.isLiked ? .red : .black)
                .onTapGesture {
                    info.isLiked.toggle()
                    AppViewModel.shared.updateLikeStatus(with: info.id, isLike: info.isLiked)
                }
        }
        .padding(10)
        .background(Color.white)
        .cornerRadius(10)
    }
}
