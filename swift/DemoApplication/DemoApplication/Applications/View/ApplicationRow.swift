//
//  ApplicationRow.swift
//  DemoApplication
//
//  Created by aut_bai on 2022/6/25.
//

import SwiftUI

struct ApplicationRow: View {
    @State var item: ApplicationItem
    
    var body: some View {
        HStack {
            AsyncImage(url: URL(string: item.iconUrl)) {
                $0.resizable()
            } placeholder: {
                ProgressView()
            }
            .frame(width: 50, height: 50)
            .cornerRadius(8)
            
            VStack(alignment: .leading) {
                Text(item.name)
                    .font(.headline)
                    .lineLimit(1)
                
                Text(item.description)
                    .font(.subheadline)
//                    .padding(.top, 3)
                    .lineLimit(2)
            }
            
            Spacer()
            
            Image(systemName: item.isFavorite ? "heart.fill" : "heart")
                .imageScale(.medium)
                .foregroundColor(item.isFavorite ? .red : .black)
                .onTapGesture {
                    item.isFavorite.toggle()
                }
        }
        .padding(10)
        .background(Color(uiColor: .white))
        .cornerRadius(8)
    }
}

struct ApplicationRow_Previews: PreviewProvider {
    static var previews: some View {
        let item = ApplicationItem(
            id: 1163852619,
            iconUrl:"""
            https://is4-ssl.mzstatic.com/image/thumb/\
            Purple126/v4/e7/a6/28/e7a62890-5938-b2c0-bf38-60d787419d5c/\
            logo_chat_2020q4_color-0-1x_U007emarketing-0-0-0-6-0-0-85-220.png/\
            60x60bb.jpg
            """,
            name: "Google Chat",
            description: """
            Google Chat is an intelligent and secure communication and\
            collaboration tool, built for teams.
            """
        )
        ApplicationRow(item: item)
            .previewLayout(.fixed(width: 390, height: 80))
    }
}
