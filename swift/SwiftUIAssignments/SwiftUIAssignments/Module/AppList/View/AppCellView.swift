//
//  AppCellView.swift
//  SwiftUIAssignments
//
//  Created by zcj on 2023/6/6.
//

import SwiftUI

struct AppCellView: View {

    private let appModel: AppModel

    // MARK: - system
    init(_ appModel: AppModel) {
        self.appModel = appModel
    }

    var body: some View {
        HStack {
            // icon
            CachedAsyncImage(url: appModel.artworkUrl)
                .frame(width: 60, height: 60)
                .cornerRadius(10)

            // info
            VStack(alignment: .leading, spacing: 3) {
                Text(appModel.trackCensoredName)
                    .font(.headline)
                    .lineLimit(1)
                Text(appModel.description)
                    .font(.subheadline)
                    .lineLimit(2)
            }
            .padding(.trailing, 4)
            .frame(maxHeight: .infinity)

            Spacer()

            // favorite
            Image(systemName: appModel.isLike ? "heart.fill" : "heart")
                .foregroundColor(appModel.isLike ? .red : .gray)
                .frame(width: 20, height: 30)

        }
        .padding(.symmetric(horizontal: 15, vertical: 10))
        .background(.white, in: RoundedRectangle(cornerRadius: 10))
        .listRowInsets(.symmetric(vertical: 6))
        .listRowSeparator(.hidden)
        .listRowBackground(Color.clear)
    }
}

struct AppCellView_Previews: PreviewProvider {
    static var previews: some View {
        AppCellView(.mock)
    }
}
