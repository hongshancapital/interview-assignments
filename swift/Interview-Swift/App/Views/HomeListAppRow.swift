//
//  AppRow.swift
//  ProduceImageType
//
//  Created by lizhao on 2022/9/16.
//

import SwiftUI
import GUIKit

struct HomeListAppRow: View {
    
    struct Layout {
        static let height: CGFloat = 100
        static let coverSize: CGFloat = 64
        static let contentInsets = EdgeInsets(top: 10, leading: UI.Space.medium, bottom: 10, trailing: UI.Space.small)
    }
    
    @EnvironmentObject var store: Store

    var model: AppModelWrapper
    var body: some View {
        VStack {
            Spacer()
            HStack {
                CoverImageView(url: model.app.artworkUrl60)
                // title & desprition
                VStack(alignment: .leading) {
                    Text(model.app.trackName)
                        .lineLimit(1)
                        .font(Font.body)
                    Spacer().frame(height: 10)
                    Text(model.app.description)
                        .lineLimit(2)
                        .font(.caption2)
                }
                Spacer()
                Button {
                    withAnimation {
                        self.store.dispatch(.toggleFavorite(trackId: model.app.trackId, isFavorite: model.isFavorite))
                    }
                } label: {
                    Image(systemName: model.isFavorite ? "heart.fill" : "heart")
                        .font(.headline)
                        .frame(maxHeight: .infinity)
                        .frame(width: 40)
                        .foregroundColor(model.isFavorite ? .red : .gray)
                }
            }
            .padding(Layout.contentInsets)
            Spacer()
        }
        .background(
            ZStack {
                RoundedRectangle(cornerRadius: UI.Corner.biggest)
                    .stroke(Color(UI.Color.border), style: StrokeStyle(lineWidth: UI.Border.light))
                RoundedRectangle(cornerRadius: UI.Corner.biggest)
                    .fill(Color.white)
            }
        )
        .frame(maxWidth: .infinity)
        .frame(height: Layout.height)
        .listRowSeparator(.hidden)
        .listRowBackground(Color.clear)
        .listRowInsets(EdgeInsets(top: 0, leading: 0, bottom: UI.Space.small, trailing: 0))
    }
} 
