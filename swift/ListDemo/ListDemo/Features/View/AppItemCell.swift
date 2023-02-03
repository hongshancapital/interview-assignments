//
//  AppItemCell.swift
//  ListDemo
//
//  Created by kent.sun on 2023/2/2.
//

import Foundation
import SwiftUI

struct AppItemCell: View {
    private let imageHeight = 120.0
    private let verticalPadding = 8.0
    let item: DataModel.AppItem
    @ObservedObject var viewModel: SearchListViewModel
    var body: some View {
        VStack {
            HStack {
                artworkImage()
                VStack(alignment: .leading, spacing: 6) {
                    Text("\(item.title)")
                        .lineLimit(1)
                        .font(.headline)
                    Text("\(item.description)")
                        .lineLimit(2)
                        .font(.footnote)
                }
                Spacer()
                collectHeart()
            }.padding(EdgeInsets(top: 16, leading: 16, bottom: 16, trailing: 16))

            .background(Color.white)
            .cornerRadius(16)
        }
    }
    
    @ViewBuilder
    private func artworkImage() -> some View {
        CachedAsyncImage(
            url: URL(string: item.artworkUrl100)
        ) { phase in
            switch phase {
            case .empty:
                ProgressView()
            case .success(let image):
                image.resizable()
                    .aspectRatio(contentMode: .fit)
            case .failure:
                Image(systemName: "photo")
            @unknown default:
                Image(systemName: "photo")
            }
        }.cornerRadius(8.0)
            .frame(width: 52, height: 52, alignment: .center)
            .overlay(
                RoundedRectangle(cornerRadius: 8,
                                 style: .continuous)
                .stroke(Color(hex: 0xD9D9D9), lineWidth: 1)
            )
    }
    
    @ViewBuilder
    private func collectHeart() -> some View {
        Image(systemName: viewModel.contains(item) ? "heart.fill" : "heart")
            .foregroundColor(viewModel.contains(item) ? .red : .gray)
            .scaleEffect(viewModel.contains(item) ? 1.4 : 1)
            .animation(.easeInOut, value: viewModel.contains(item))
            .onTapGesture {
                viewModel.toggleFav(item)
            }
            .frame(width: 20, height: 20)
    }
}

