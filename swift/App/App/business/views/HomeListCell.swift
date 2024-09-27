//
//  HomeListCell.swift
//  App
//
//  Created by august on 2022/3/22.
//

import SwiftUI

struct HomeListCell: View {
    
    var information: AppInformation
        
    var body: some View {
        HStack {
            NetworkImage(imageUrl: information.artworkUrl60)
                .frame(width: 60, height: 60)
            VStack(alignment: .leading, spacing: 8) {
                Text(information.trackName)
                    .font(.system(size: 16, weight: .bold, design: .default))
                    .lineLimit(1)
                Text(information.description)
                    .font(.system(size: 13, weight: .regular, design: .default))
                    .lineLimit(2)
            }
            Spacer()
            Image(systemName: information.isFavorate ? "heart.fill" : "heart")
                .foregroundColor(information.isFavorate ? .red : .gray)
                .scaleEffect(information.isFavorate ? 1.2 : 1)
                .animation(.interpolatingSpring(stiffness: 100, damping: 6), value: information.isFavorate)
                .onTapGesture {
                    information.isFavorate.toggle()
                }
        }
        .padding(12)
        .background(Color.white)
        .cornerRadius(16.0)
    }
}


struct HomeListCell_Previews: PreviewProvider {
    static var previews: some View {
        HomeListCell(information: AppInformation(trackId: 12345, trackName: "testTrackName", description: "test description", artworkUrl60: URL(string: "https:www.baidu.com")!))
    }
}
