//
//  Cell.swift
//  HongShanDemo
//
//  Created by Peng Shu on 2023/2/24.
//

import SwiftUI

struct Cell: View {
    @Binding var item: Item
    
    var body: some View {
        HStack {
            ItemAvatarView(url: item.artworkUrl100)
            VStack(alignment: .leading) {
                Text(item.trackName)
                    .font(.body.weight(.semibold))
                Text(item.description)
                    .font(.subheadline)
                    .lineLimit(2)
            }
            Spacer()
            
            Button {
                withAnimation {
                    item.isLiked.toggle()
                }
            } label: {
                if item.isLiked == false {
                    Image(systemName: "heart")
                        .foregroundColor(.secondary)
                } else {
                    AnimateHeartView()
                }
            }
        }
        .padding()
        .background(Color(.systemBackground))
        .cornerRadius(8)
    }
}
