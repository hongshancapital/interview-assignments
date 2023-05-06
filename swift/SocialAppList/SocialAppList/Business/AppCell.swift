//
//  AppCell.swift
//  SocialAppList
//
//  Created by 刘飞 on 2023/4/29.
//

import SwiftUI
import Kingfisher

struct AppCell: View {
    
    @Binding var model: SocialApp
    @EnvironmentObject private var vm: ListViewModel
    
    var body: some View {
        HStack {
            KFImage(URL(string: model.artworkUrl60))
                .placeholder({ ProgressView() })
                .cancelOnDisappear(true)
                .resizable()
                .scaledToFit()
                .cornerRadius(8)
                .border(Color(uiColor: .lightGray), cornerRadius: 8, width: 0.5)
                .background(Color("color_1"))
                .frame(width: 60, height: 60)
            
            VStack(alignment: .leading, spacing: 5) {
                Text(model.trackName)
                    .lineLimit(1)
                    .font(.system(size: 18).bold())
                Text(model.description)
                    .lineLimit(2)
                    .lineSpacing(4)
                    .font(.system(size: 15))
            }
            
            Spacer()
            
            Button {
                model.isLiked.toggle()
                vm.database[model.id] = model.isLiked
                
            } label: {
                Image(systemName: model.isLiked ? "heart.fill" : "heart")
                    .resizable()
                    .scaledToFit()
                    .frame(width: 22, height: 22)
                    .scaleEffect(model.isLiked ? 1.2 : 1)
                    .animation(.easeInOut, value: model.isLiked)
            }
            .foregroundColor(model.isLiked ? .red : .secondary)
        }
        .padding(16)
        .background(Color.white)
        .cornerRadius(10)
    }
}
