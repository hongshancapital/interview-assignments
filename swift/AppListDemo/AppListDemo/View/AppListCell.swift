//
//  AppListCell.swift
//  AppListDemo
//
//  Created by arthur on 2022/10/22.
//

import SwiftUI

struct AppListCell: View {
    
    @State var info: AppInfo
    
    var body: some View {
        HStack(spacing: 10) {
            Spacer()
            LoadingImage(url: URL(string: info.imageUrl))
                .frame(width: 60, height: 60)
            
            VStack(alignment: .leading, spacing: 8) {
                Text(info.name)
                    .font(.headline)
                    .lineLimit(1)
                Text(info.description)
                    .font(.subheadline)
                    .lineLimit(2)
            }
            .frame(maxWidth: .infinity)
            
            FavoriteButton(isFavorited: $info.isFavorited)
                .frame(width: 30, height: 30)
            Spacer()
        }
        .frame(width: UIScreen.main.bounds.width - 30, height: 80)
        .background(.white)
        .cornerRadius(10)
    }
}

struct AppListCell_Previews: PreviewProvider {
    static var previews: some View {
        VStack {
            AppListCell(info: AppInfo.preview)
        }
        .padding(.all, 40)
        .background(Color(uiColor: .systemGroupedBackground))
    }
}
