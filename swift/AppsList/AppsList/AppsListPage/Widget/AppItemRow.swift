//
//  AppItemRow.swift
//  AppsList
//
//  Created by 贺建华 on 2023/4/2.
//

import SwiftUI

struct AppItemRow: View {
    
    @State private var isFavorite = false
    var model: AppItemModel
    
    var body: some View {
        HStack {
            AsyncImage(url: URL(string: model.artworkUrl100)) { phase in
                if let image = phase.image {
                    image
                        .resizable()
                        .aspectRatio(contentMode: .fit)
                        .cornerRadius(6)
                        .overlay {
                            RoundedRectangle(cornerRadius: 6, style: .continuous)
                                .stroke(Color.init(white: 0.8), lineWidth: 0.5)
                        }
                } else if phase.error == nil {
                    ProgressView()
                } else {
                    Text("failed")
                }
            }
            .frame(width: 50, height: 50)
            
            VStack (alignment: .leading, spacing: 5) {
                
                Text(model.trackName)
                    .font(.system(size: 16))
                    .fontWeight(.medium)
                    .lineLimit(1)
                
                Text(model.description)
                    .font(.system(size: 12))
                    .fontWeight(.regular)
                    .lineLimit(2)
            }
            
            Spacer()
            
            Button {
                isFavorite.toggle()
            } label: {
                if isFavorite {
                    Image(systemName: "heart.fill")
                        .resizable()
                        .aspectRatio(contentMode: .fill)
                        .foregroundColor(.pink)
                        .frame(width: 22, height: 22)
                } else {
                    Image(systemName: "heart")
                        .resizable()
                        .aspectRatio(contentMode: .fill)
                        .foregroundColor(.gray)
                        .frame(width: 18, height: 18)
                }
            }
            .frame(width: 22, height: 22)
        }
        .padding(EdgeInsets(top: 8, leading: 10, bottom: 8, trailing: 10))
        .listRowSeparator(.hidden)
        .listRowBackground(
            RoundedRectangle(cornerRadius: 10)
                .background(.clear)
                .foregroundColor(.white)
                .padding(EdgeInsets(top: 5, leading: 15, bottom: 5, trailing: 15))
        )
    }
}

struct AppItemRow_Previews: PreviewProvider {
    static var previews: some View {
        List {
            AppItemRow(model: AppItemModel.testItem)
            AppItemRow(model: AppItemModel.testItem)
        }
        .listStyle(.plain)
        .background(Const.backgroundColor)
    }
}
