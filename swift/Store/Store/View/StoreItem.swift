//
//  StoreIten.swift
//  Store
//
//  Created by 张欣宇 on 2022/11/3.
//

import SwiftUI

fileprivate enum Constants {
    enum Layout {
        static let pandding: CGFloat = 20
        static let imageSideLength: CGFloat = 60
        static let textSpacing: CGFloat = 8
    }
    
    enum Image {
        static let cornerRadius: CGFloat = 10
        static let borderWidth: CGFloat = 1
    }
    
    enum Text {
        enum LineLimit {
            static let title = 1
            static let subtitle = 2
        }
    }
    
    enum Button {
        enum Name {
            static let heartfill = "heart.fill"
            static let heart = "heart"
        }
        
        enum Scale {
            static let large: CGFloat = 1.6
            static let small: CGFloat = 1.4
        }
    }
}

struct StoreItem: View {
    let viewModel: StoreListViewModel
    let app: AppModel
    var body: some View {
        HStack {
            loadingImage
            descriptionText
            Spacer()
            likeButton
        }
        .padding(Constants.Layout.pandding)
    }
    
    var loadingImage: some View {
        LoadingImage(url: app.artworkUrl60)
            .frame(
                width: Constants.Layout.imageSideLength,
                height: Constants.Layout.imageSideLength
            )
            .cornerRadius(Constants.Image.cornerRadius)
            .overlay {
                RoundedRectangle(cornerRadius: Constants.Image.cornerRadius)
                    .stroke(
                        Color(.systemGray4) ,
                        lineWidth: Constants.Image.borderWidth
                    )
            }
    }
    
    var descriptionText: some View {
        VStack(
            alignment: .leading,
            spacing: Constants.Layout.textSpacing
        ) {
            Text(app.trackName)
                .lineLimit(Constants.Text.LineLimit.title)
                .font(.bold(.headline)())
            Text(app.description)
                .lineLimit(Constants.Text.LineLimit.subtitle)
                .font(.subheadline)
        }
    }
    
    var likeButton: some View {
        Button {
            viewModel.like(app.trackId)
        } label: {
            Image(systemName: app.like ? Constants.Button.Name.heartfill : Constants.Button.Name.heart)
                .scaleEffect(app.like ? Constants.Button.Scale.large : Constants.Button.Scale.small)
        }
        .foregroundColor(app.like ? Color(.systemRed) : Color(.systemGray2))
    }
}

