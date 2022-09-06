//
//  AppCell.swift
//  Homework
//
//  Created by Andy on 2022/9/2.
//  UI->cell

import SwiftUI

struct AppCell: View {
    
    @EnvironmentObject var viewModel: AppListViewModel
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
                    if viewModel.favoriteAppIds.contains(appInfo.trackId) {
                        viewModel.favoriteAppIds.remove(appInfo.trackId)
                    } else {
                        viewModel.favoriteAppIds.insert(appInfo.trackId)
                    }
                }
            } label: {
                Label("Toggle Favorite", systemImage: viewModel.favoriteAppIds.contains(appInfo.trackId) ? "heart.fill" : "heart")
                    .labelStyle(.iconOnly)
                    .foregroundColor(viewModel.favoriteAppIds.contains(appInfo.trackId) ? .red : .gray)
                    .scaleEffect(viewModel.favoriteAppIds.contains(appInfo.trackId) ? 1.2 : 1)
            }
            .frame(width: 40, height: 40, alignment: .center)
            
        }
        .padding()
        .background(Color.white)
        .cornerRadius(8)
    }
    
}

struct AppCell_Previews: PreviewProvider {
    static var previews: some View {
        AppCell(appInfo: AppListViewModel().appInfos[0])
                   .environmentObject(AppListViewModel())
    }
}
