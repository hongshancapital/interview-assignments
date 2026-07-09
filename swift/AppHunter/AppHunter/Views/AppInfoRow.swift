//
//  AppInfoRow.swift
//  AppHunter
//
//  Created by zhang shijie on 2023/5/24.
//

import SwiftUI

struct AppInfoRow: View {
    @Binding var item: AppInfo
    let clickCollectBlock: (AppInfo) -> Void
    var body: some View {
        HStack(alignment: .top) {
            if let url = item.artworkUrl, let uURL = URL(string: url) {
                AppInfoRowImage(url: uURL).frame(width: 64, height: 64)
            } else {
                Image(systemName: "exclamationmark.icloud.fill").foregroundColor(.gray).frame(width: 64, height: 64)
            }
            VStack(alignment: .leading, spacing: 4) {
                Text("\(item.trackName ?? "")").font(.system(size: 18)).bold().lineLimit(1)

                Text("\(item.appDescription ?? "")").font(.system(size: 14)).lineLimit(2)
            }
            Spacer()
            VStack {
                Spacer()
                Image(systemName: item.isCollected ? "heart.fill" : "heart")
                    .foregroundColor(item.isCollected ? .red : .gray)
                    .scaleEffect(item.isCollected ? 1.4 : 1.2)
                    .frame(width: 44, height: 44)
                    .animation(.default, value: item.isCollected)
                    .onTapGesture { clickCollectBlock(item) }
                Spacer()
            }

        }.frame(height: 64).padding(.leading, 12).padding(.trailing, 12).padding(.top, 12).padding(.bottom, 12)
    }
}
