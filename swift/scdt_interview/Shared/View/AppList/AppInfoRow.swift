//
//  AppInfoRow.swift
//  scdt_interview (iOS)
//
//  Created by Ray Tao on 2022/7/14.
//

import SwiftUI

struct AppInfoRow: View {
    @EnvironmentObject var store: Store

    let model: AppListViewModel

    var body: some View {
        HStack {
            AsyncImage(
                url: URL(string: model.appIcon),
                content: { image in
                    image.resizable()
                        .aspectRatio(contentMode: .fit)
                        .cornerRadius(8)
                },
                placeholder: {
                    ProgressView()
                }
            )
            .frame(width: 58, height: 58)
            .padding(.trailing, 5)

            VStack(alignment: .leading, spacing: 5.0) {
                Text(model.appName)
                    .lineLimit(1)
                    .font(.system(size: 16))
                    .foregroundColor(.black)
                Text(model.appDes)
                    .lineLimit(2)
                    .font(.system(size: 11))
                    .foregroundColor(.black)
            }.layoutPriority(1)
                .padding(.top, 12)
            Spacer()
            Button(action: {
                self.store.dispatch(.toggleFavorite(id: self.model.id))
            }) {
                Image(systemName: isFavorite ? "heart.fill" : "heart")
                    .renderingMode(.template)
                    .foregroundColor(isFavorite ? .red : .gray)

            }
        }
        .frame(height: 90)
        .padding(.leading, 15)
        .padding(.trailing, 15)
        .background(
            ZStack {
                RoundedRectangle(cornerRadius: 12)
                    .fill(Color.white)
            }
        )
        .padding(.horizontal)
    }

    var isFavorite: Bool {
        let user = store.appState.user
        return user.isFavorite(id: model.id)
    }
}

struct AppInfoRow_Previews: PreviewProvider {
    static var previews: some View {
        VStack {
            ForEach(AppListViewModel.firstPageSamples) { element in
                AppInfoRow(model: element)
            }
        }.environmentObject(Store())
    }
}
