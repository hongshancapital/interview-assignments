//
//  AppListCell.swift
//  AppList
//
//  Created by 大洋 on 2022/8/21.
//

import SwiftUI


struct AppListCell: View {
    @EnvironmentObject var viewModel: AppListViewModel
    
    var info: AppListModel
    
    @State var isFavorite = false {
        didSet {
            if isFavorite {
                viewModel.likeList.insert(info.trackId)
            } else {
                viewModel.likeList.remove(info.trackId)
            }
        }
    }
    
    var body: some View {
        HStack(spacing: 10) {
            AsyncImage(url: info.artworkUrl60) { image in
                image
                    .resizable()
                    .aspectRatio(contentMode: .fit)
                    .cornerRadius(10)
                    .padding(.leading, 10)
            } placeholder: {
                ProgressView()
            }.frame(width: 60, height: 60)

            VStack(alignment: .leading) {
                Text(info.trackName)
                    .font(.title3)
                    .lineLimit(1)
                Text(info.description)
                    .font(.subheadline)
                    .lineLimit(2)
            }
            Spacer()
            let like = viewModel.likeList.contains(info.trackId)
            Image(systemName: like ? "heart.fill" : "heart")
                .imageScale(.large)
                .foregroundColor(like ? .red : .gray)
                .foregroundColor(.red)
                .padding(.trailing, 15)
                .onTapGesture {
                    isFavorite.toggle()
                }
        }
        .frame(height: 80)
        .background(.white)
        .cornerRadius(10)
        .padding(.horizontal, -15)
    }
}

struct AppListCell_Previews: PreviewProvider {
    static var previews: some View {
        let model = AppListModel(trackId: 0, trackName: "", artworkUrl60: nil, description: "", isFavorite: false)
        AppListCell(info: model)
    }
}

