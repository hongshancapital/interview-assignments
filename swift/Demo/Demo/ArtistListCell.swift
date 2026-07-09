//
//  ArtistListCell.swift
//  Demo
//
//  Created by csmac05 on 2023/2/7.
//

import SwiftUI
import Combine

struct ArtistListCell: View {
    @Binding var artist: ArtistModel
    
    var body: some View {
        HStack(alignment: .center) {
            CustomAsyncImage(url: artist.artworkUrl60URL) { image in
                image.resizable()
            } placeholder: {
                ProgressView()
            }
            .cornerRadius(12.0)
            .frame(width: 60.0, height: 60.0)
            
            VStack(alignment: .leading, spacing: 10.0) {
                Text(artist.trackCensoredName)
                    .lineLimit(1)
                    .font(.system(size: 16.0))
                    .fontWeight(.medium)
                
                Text(artist.description)
                    .lineLimit(2)
                    .font(.system(size: 12.0))
                
            }
            
            Spacer()
            
            Image(systemName: artist.isLike ? "suit.heart.fill" : "suit.heart")
                .foregroundColor(artist.isLike ? .red : .gray)
                .scaleEffect(CGFloat(artist.isLike ? 1.4 : 1))
                .animation(.interactiveSpring(), value: artist.isLike)
                .onTapGesture {
                    artist.isLike.toggle()
                }
        }
    }
}

struct ArtistListCell_Previews: PreviewProvider {
    
    static var previews: some View {
        Group {
            ArtistListCell(artist: .constant(mockData.results[0]))
            ArtistListCell(artist: .constant(mockData.results[1]))
            ArtistListCell(artist: .constant(mockData.results[2]))
            ArtistListCell(artist: .constant(mockData.results[3]))

        }
    }
    
}
