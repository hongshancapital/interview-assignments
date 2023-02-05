//
// Homework
// AppInfoRow.swift
//
// Created by wuyikai on 2022/4/27.
//

import SwiftUI

struct AppInfoRow: View {
    var appInfo: AppInfo
    
    var body: some View {
        HStack(alignment: .center) {
            WebImageView(appInfo.icon)
                .frame(width: 50, height: 50, alignment: .center)
                .cornerRadius(10)
                .background(
                    RoundedRectangle(cornerRadius: 10)
                        .stroke(.gray, style: StrokeStyle(lineWidth: 0.5))
                )
            
            AppIntroView(title: appInfo.title, desc: appInfo.description)
                .padding(.leading, 5)
            
            Spacer()
            
            FavoriteButton(appInfo: appInfo)
        }.padding([.top, .bottom], 10)
    }
}

struct AppIntroView: View {
    let title: String
    let desc: String
    var body: some View {
        VStack(alignment: .leading, spacing: 5) {
            Text(title)
                .font(.headline)
            Text(desc)
                .lineLimit(2)
        }
    }
}

struct FavoriteButton: View {
    @StateObject var favoriteAppManager = FavoriteAppManager.sharedInstance
    var appInfo: AppInfo
    
    var isFavorite: Bool {
        favoriteAppManager.isFavorite(app: appInfo)
    }
    
    var body: some View {
        Button {
            favoriteAppManager.toggleFavorite(appID: appInfo.id)
        } label: {
            Image(systemName: isFavorite ? "heart.fill" : "heart")
                .foregroundColor(isFavorite ? .red : .gray)
                .scaleEffect(isFavorite ? 1.25 : 1.0)
                .animation(.default, value: isFavorite)
        }
    }
}

struct AppInfoRow_Previews: PreviewProvider {
    static var previews: some View {
        ForEach(AppInfo.sampleData) {
            AppInfoRow(appInfo: $0)
        }
    }
}

