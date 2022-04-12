//
//  AppInfoCell.swift
//  
//
//  Created by 黄磊 on 2022/4/10.
//

import SwiftUI
import MJDataFlow

struct AppInfoCell: View {
    
    @ObservedObject private var appListStore = Store<AppListState>.shared
    let appInfo: AppInfo
    var isFavorite: Bool {
        appListStore.state.isThisAppFavorite(appInfo.bundleId)
    }
    
    var body: some View {
        HStack(spacing: 6) {
            AppIconImage(artworkUrl: appInfo.artworkUrl100)
                .frame(width: 50, height: 50)
            
            VStack(alignment: .leading) {
                Text(verbatim: appInfo.artistName)
                    .bold()
                    .lineLimit(1)
                    .font(.footnote)
                    .padding(.bottom, 1)
                HStack {
                    Text(verbatim: appInfo.description)
                        .lineLimit(2)
                        .font(.caption2)
                    Spacer()
                }
            }
            .frame(maxWidth: .infinity, maxHeight: .infinity)
        
            Button {
                appListStore.send(action: .toggleFavoriteOf(appInfo))
            } label: {
                Image(systemName: isFavorite ? "heart.fill" : "heart")
                    .resizable()
                    .scaledToFit()
                    .foregroundColor(isFavorite ? .red : .gray)
                    .frame(width: isFavorite ? 22 : 20, height: isFavorite ? 22 : 20, alignment: .center)
                    .animation(.default, value: isFavorite)
            }
            .frame(width: 30)
        }
        .frame(maxHeight: .infinity)
        .padding(14)
        .background(.white)
        .cornerRadius(10)
    }
}

struct AppInfoCell_Preview: PreviewProvider {
    static var previews: some View {
        AppInfoCell(appInfo: .exampleFour)
    }
}
