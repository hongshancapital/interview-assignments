//
//  HomeListCell.swift
//  App
//
//  Created by august on 2022/3/22.
//

import SwiftUI

struct HomeListCell: View {
    
    var information: AppInformation
    //用user defaults来记录状态
    @AppStorage var isFavorate: Bool
    
    init(information: AppInformation) {
        self.information = information
        _isFavorate = AppStorage<Bool>.init(wrappedValue: false, "\(information.trackName)-\(information.trackId)")
    }
        
    var body: some View {
        HStack {
            NetworkImage(imageUrl: information.artworkUrl60)
                .frame(width: 60, height: 60)
            VStack(alignment: .leading, spacing: 8) {
                Text(information.trackName)
                    .font(.title3)
                    .lineLimit(1)
                Text(information.description)
                    .font(.body)
                    .lineLimit(2)
            }
            Spacer()
            Image(systemName: isFavorate ? "heart.fill" : "heart")
                .foregroundColor(isFavorate ? .red : .gray)
                .scaleEffect(isFavorate ? 1.2 : 1)
                .animation(.interpolatingSpring(stiffness: 100, damping: 6), value: isFavorate)
                .onTapGesture {
                    isFavorate.toggle()
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
