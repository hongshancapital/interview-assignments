//
//  AppCell.swift
//  ListDemo
//
//  Created by 王明友 on 2023/6/11.
//

import SwiftUI

struct AppCell: View {
    @EnvironmentObject var store: AppStore
    let viewModel: AppListViewModel
    var body: some View {
        HStack {
            // better should use cache image, here just simple show
            AsyncImage(url: viewModel.iconImageURL) { image in
                image.resizable()
            } placeholder: {
                ProgressView()
            }
            .frame(width: 50, height: 50, alignment: .center)
            .cornerRadius(8)
            VStack(alignment: .leading) {
                Text(viewModel.name)
                    .font(.headline)
                    .lineLimit(1)
                    .foregroundColor(.black)
                Text(viewModel.description)
                    .font(.subheadline)
                    .lineLimit(2)
                    .foregroundColor(.black)
            }
            Spacer()
            Image(systemName: store.appState.isFavoriteApp(id: viewModel.id) ? "heart.fill" : "heart")
                .foregroundColor(store.appState.isFavoriteApp(id: viewModel.id) ? .red : .black)
                .onTapGesture {
                    self.store.dispatch(.toggleFavorite(id: self.viewModel.id))
                }
                .onAppear {
                }
        }
        .padding(10)
        .background(Color(uiColor: .white))
        .cornerRadius(8)
    }
}

struct AppCell_Previews: PreviewProvider {
    static var previews: some View {
        let AppItem = AppListViewModel(app: AppItem(
            id: 1234567890,
            iconUrl:"""
            https://is4-ssl.mzstatic.com/image/thumb/Purple116/v4/a3/c4/62/a3c4624b-b16a-ceac-2cde-ed96694f1247/AppIcon-0-1x_U007emarketing-0-4-0-sRGB-0-85-220.png/60x60bb.jpg
            """,
            name: "Test Name",
            description: """
            your selected plan. You can manage your subscription and turn off auto-renewal at any time by going to your iTunes account settings on your device.\n\nViber is part of the Rakuten Group, a world leader in e-commerce and financial services.
            """
        ))
        AppCell(viewModel: AppItem)
            .previewLayout(.fixed(width: 300, height: 70))
    }
}
