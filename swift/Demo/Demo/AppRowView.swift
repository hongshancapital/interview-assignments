//
//  AppRowView.swift
//  Demo
//
//  Created by GNR on 10/27/22.
//

import SwiftUI

struct AppRowView: View {
    @EnvironmentObject var viewModel: AppsViewModel
    @State var app: AppModel

    var body: some View {
        HStack {
            WebImage(url: app.artworkUrl60)
                .cornerRadius(12.0)
                .frame(width: 60, height: 60)
                        
            VStack(alignment: .leading, spacing: 10) {
                Text(app.trackName)
                    .lineLimit(1)
                    .font(.system(size: 16))
                    .fontWeight(.semibold)
                
                Text(app.description)
                    .lineLimit(2)
                    .font(.system(size: 12))
            }
            
            Spacer()
            
            Image(systemName:app.isFavorite ? "suit.heart.fill" : "suit.heart")
                .foregroundColor(app.isFavorite ? .red : .gray)
                .scaleEffect(CGFloat(app.isFavorite ? 1.4 : 1))
                .animation(.interactiveSpring(), value: app.isFavorite)
                .onTapGesture {
                    //  BUG: 修改数据源 会触发Publisher 刷新整行 无法产生动画
//                    self.viewModel.favoriteApp(self.app, !self.app.isFavorite)
                    // 局部刷新有动画 数据源如何修改？
                    self.app.isFavorite = !self.app.isFavorite
                }
        }
    }
}

struct AppRowView_Previews: PreviewProvider {
    static var previews: some View {
        Group {
            AppRowView(app: DataManager.shared.appModels[0])
            AppRowView(app: DataManager.shared.appModels[1])
        }
    }
}
