//
//  AppRow.swift
//  DemoApp
//
//  Created by 王俊 on 2022/8/30.
//

import SwiftUI

struct AppRow: View {
    @EnvironmentObject var modelData: ModelData
    var appInfo: AppInfo
    var animation: Animation {
        Animation.linear
    }
    var body: some View {
        HStack {
            AsyncImage(url: URL(string: appInfo.artworkUrl60)) { image in
                image.resizable()
            } placeholder: {
                ProgressView()
            }
                .frame(width: 50, height: 50)
                .aspectRatio(1, contentMode: .fit)
                .border(Color(hue: 1.0, saturation: 0.0, brightness: 0.564, opacity: 0.585), width: 0.5)
                .cornerRadius(5)
            VStack(alignment: .leading, spacing: 4) {
                Text(appInfo.trackName)
                    .font(.title3)
                    .fontWeight(.medium)
                    .lineLimit(1)
                Text(appInfo.description)
                    .font(.footnote)
                    .fontWeight(.regular)
                    .lineLimit(2)
            }
            Spacer()
            Button {
                withAnimation {
                    if modelData.favoriteAppIds.contains(appInfo.trackId) {
                        modelData.favoriteAppIds.remove(appInfo.trackId)
                    } else {
                        modelData.favoriteAppIds.insert(appInfo.trackId)
                    }
                }
            } label: {
                Label("Toggle Favorite", systemImage: modelData.favoriteAppIds.contains(appInfo.trackId) ? "heart.fill" : "heart")
                    .labelStyle(.iconOnly)
                    .foregroundColor(modelData.favoriteAppIds.contains(appInfo.trackId) ? .red : .gray)
                    .scaleEffect(modelData.favoriteAppIds.contains(appInfo.trackId) ? 1.2 : 1)
            }
                .frame(width: 40, height: 40, alignment: .center)
        }
        .padding()
        .background(Color.white)
        .cornerRadius(8)
    }
}

struct AppRow_Previews: PreviewProvider {
    static var previews: some View {
        AppRow(appInfo: ModelData().appInfos[0])
            .environmentObject(ModelData())
    }
}
