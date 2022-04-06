//
//  ArtWorkItem.swift
//  SwitUI实战
//
//

import SwiftUI

struct ArtWorkItemView: View {
    @ObservedObject var artworkFetcher: ArtWorkFetcher
    @State private var isFavourite: Bool = false
    let item: ArtWork
    let imageSize: CGFloat = 50
    var body: some View {
        HStack {
            if let image = artworkFetcher.imageCaches[item.id] {
                image
                    .resizable()
                    .aspectRatio(1 / 1, contentMode: .fit)
                    .frame(width: imageSize, height: imageSize)
                    .cornerRadius(5)
                    .clipped()
            } else {
                if let urlString = item.artistViewUrl {
                    let url = URL(string: urlString)
                    AsyncImage(url: url) { phase in
                        if let image = phase.image {
                            image
                                .cacheImage(id: item.id, cache: &artworkFetcher.imageCaches)
                                .resizable()
                                .aspectRatio(1 / 1, contentMode: .fit)
                                .frame(width: imageSize, height: imageSize)
                                .cornerRadius(5)
                                .clipped()
                                
                        } else if phase.error != nil {
                            Text(phase.error?.localizedDescription ?? "error")
                                .foregroundColor(Color.pink)
                                .frame(width: imageSize, height: imageSize)
                        } else {
                            ProgressView()
                                .frame(width: imageSize, height: imageSize)
                                .cornerRadius(5)
                        }
                    }
                } else {
                    Color.gray.frame(width: imageSize, height: imageSize)
                }
            }
            
            VStack(alignment: .leading, spacing: 5) {
                Text(item.trackName)
                    .font(.headline)
                    .foregroundColor(.primary)
                    .lineLimit(1)
                
                Text(item.description)
                    .font(.subheadline)
                    .foregroundColor(.secondary)
                    .lineLimit(2)
                    .multilineTextAlignment(.leading)
            }
            Spacer()
            
            Button(action: {
                self.isFavourite.toggle()
                artworkFetcher.isFavourite[item.id] = self.isFavourite
            }) {
                if self.isFavourite {
                    Image(systemName: "heart.fill")
                        .resizable()
                        .frame(width: 16, height: 16)
                        .foregroundColor(.red)
                        
                } else {
                    Image(systemName: "heart")
                        .resizable()
                        .frame(width: 16, height: 16)
                        .foregroundColor(.gray)
                }
            }
            
        }.padding(10)
            
            .background(.white)
            .onAppear {
                self.isFavourite = artworkFetcher.isFavourite[item.id] ?? false
            }
    }
}

extension Image {
    func cacheImage(id: String, cache: inout [String: Image]) -> Image {
        cache[id] = self
        return self
    }
}
