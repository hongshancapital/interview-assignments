//
//  ItemRow.swift
//  Assignment
//
//  Created by shinolr on 2022/7/27.
//

import SwiftUI

struct ItemRow: View {
  
  @EnvironmentObject var viewModel: AppListViewModel
  var item: Goods.ListItem
  
  var body: some View {
    HStack {
      AsyncImage(url: item.artworkUrl60) { image in
        image
          .resizable()
          .cornerRadius(8)
          .overlay {
            RoundedRectangle(cornerRadius: 8)
              .stroke(.gray, lineWidth: 1)
          }
      } placeholder: {
        ProgressView()
      }
      .frame(width: 60, height: 60)
      
      VStack(alignment: .leading) {
        Text(item.trackName)
          .font(.subheadline.bold())
          .lineLimit(1)
        Text(item.description)
          .lineLimit(2)
          .font(.caption)
      }
      
      Spacer()

      AsyncButton {
          await viewModel.toggleFavorite(for: item)
      } label: {
        Label("favorite", systemImage: item.isFavorite ? "heart.fill" : "heart")
          .labelStyle(.iconOnly)
          .symbolRenderingMode(item.isFavorite ? .multicolor : .monochrome)
          .foregroundColor(.gray)
      }
      .buttonStyle(.plain)
    }
    .padding(EdgeInsets(top: 10, leading: 10, bottom: 10, trailing: 10))
    .background(Color(uiColor: .systemBackground))
    .cornerRadius(18)
  }
}

struct ItemRow_Previews: PreviewProvider {
  static var previews: some View {
    ItemRow(item: stubItemList.results.last!)
  }
}
