//
//  ItemCell.swift
//  AppStoreDemo
//
//  Created by wuxi on 2023/3/19.
//

import SwiftUI
import Combine

struct ItemCell: View {
    
    @EnvironmentObject var store: Store
    let item: AppItem
    
    var body: some View {
        Group {
            HStack {
                photoView
                VStack(alignment: .leading) {
                    Text(item.title)
                        .font(.system(size: 16, weight: .bold))
                        .lineLimit(1)
                        .frame(alignment: .leading)
                    Text(item.desc)
                        .font(.system(size: 14, weight: .light))
                        .lineLimit(2)
                        .frame(alignment: .leading)
                }
                Spacer()
                Button(action: {
                    store.dispatch(.clickFavor(favor: !item.collected, id: item.id))
                }) {
                    Image(item.collected ? "heart-fill" : "heart")
                }
                .frame(width: 20, height: 20)
            }
        }
        .frame(height: 60)
        .listRowBackground(
            RoundedRectangle(cornerRadius: 10).foregroundColor(Color.white)
                .padding(EdgeInsets(top: 5, leading: 0, bottom: 5, trailing: 0))
        )
        .listRowSeparator(.hidden)
    }
    
    private var photoView: some View {
        return AsyncImage(url: URL(string: item.photoUrl)) { phase in
            if let image = phase.image {
                image // Displays the loaded image.
            } else if phase.error != nil {
                Color.gray
            } else {
                ProgressView()
            }
        }
        .frame(width: 50, height: 50)
        .cornerRadius(6)
    }
}

struct ItemCell_Previews: PreviewProvider {
    static var previews: some View {
        ItemCell(item: AppItem(id: 112233,
                               title: "Cell",
                               desc: "cell desc 37917321937197979",
                               photoUrl: "")).environmentObject(Store())
    }
}
