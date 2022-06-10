//
//  AppInfoRow.swift
//  AppList
//
//  Created by 黄伟 on 2022/6/9.
//

import SwiftUI

struct AppInfoRow: View {
    @ObservedObject var appInfoVM: AppInfoVM
    
    var body: some View {
        if let appInfo = appInfoVM.appInfo {
            HStack {
                appIcon
                appContentView
                favoriteButton
            }
            .frame(height: 70)
        }
    }
    
    private var appIcon: some View {
        Group {
            if let appInfo = appInfoVM.appInfo {
                AsyncImage(url: URL(string: appInfo.artworkUrl60)) { image in
                    image.resizable()
                } placeholder: {
                    ProgressView()
                }
                .frame(width: 60, height: 60, alignment: .center)
                .cornerRadius(8)
            }
        }
    }
    
    private var appContentView: some View {
        Group {
            if let appInfo = appInfoVM.appInfo {
                VStack(spacing: 5) {
                    Text(appInfo.trackName)
                        .bold()
                        .lineLimit(1)
                        .frame(maxWidth: .infinity, alignment: .leading)
                    Text(appInfo.description)
                        .font(.subheadline)
                        .lineLimit(2)
                        .frame(maxWidth: .infinity, alignment: .leading)
                }
            }
        }
    }
    
    private var favoriteButton: some View {
        Button {
            clickFavorite()
        } label: {
            favoriteIcon
        }
        .buttonStyle(.plain)
    }
    
    private var favoriteIcon: some View {
        Group {
            if appInfoVM.isFavorited {
                Image(systemName: "heart.fill")
                    .renderingMode(.original)
                    .scaleEffect(1.2)
            } else {
                Image(systemName: "heart")
            }
        }
    }
    
    private func clickFavorite() {
        guard let appInfo = self.appInfoVM.appInfo else { return }
        if appInfoVM.isFavorited {
            if let index = favoritedAppIds.firstIndex(of: appInfo.id) {
                favoritedAppIds.remove(at: index)
            }
        } else {
            favoritedAppIds.append(appInfo.id)
        }
        appInfoVM.isFavorited = !appInfoVM.isFavorited
    }
}

struct AppInfoRow_Previews: PreviewProvider {
    static var previews: some View {
        let appInfoVM = AppInfoVM(appInfo: applistData.results[0], isFavorited: true)
        AppInfoRow(appInfoVM: appInfoVM)
    }
}
