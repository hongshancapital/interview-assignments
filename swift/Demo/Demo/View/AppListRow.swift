//
//  AppListRow.swift
//  Demo
//
//  Created by jyt on 2023/3/21.
//

import SwiftUI

struct AppListRow: View {
    @EnvironmentObject var viewModel: AppListViewModel
    var item: AppItem
    
    var body: some View {
        HStack {
            AsyncImage(url: item.thumbnailURL) { image in
                image
                    .cornerRadius(10)
                    .overlay {
                        RoundedRectangle(cornerRadius: 10)
                            .stroke(.secondary.opacity(0.7))
                    }
            } placeholder: {
                ProgressView()
            }
            .frame(width: 60, height: 60)
            
            VStack(alignment:.leading) {
                Text(item.name).font(.title3).fontWeight(.medium)
                Text(item.description).font(.caption)
                    .lineLimit(2)
                    .truncationMode(.tail)
            }
            
            Spacer(minLength: 20)
            LikeBtn(isFavorite: viewModel.isFavorite(item.id))
                .onTapGesture {
                    viewModel.toggleFavorite(item.id)
                }
        }
    }
}

struct LikeBtn: View {
    var isFavorite: Bool
    var body: some View {
        Image(systemName: isFavorite ? "heart.fill" : "heart")
            .font(.title2)
            .foregroundColor(isFavorite ? .red : .secondary)
            .scaleEffect(isFavorite ? 1.2 : 1)
            .animation(.spring(), value: isFavorite)
    }
}


struct AppListRow_Previews: PreviewProvider {
    static let appList: AppItemList = try! loadJSON("test.json")
    static var previews: some View {
        AppListRow(item: appList.results.first!)
            .environmentObject(AppListViewModel())
    }
}
