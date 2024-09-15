//
//  AppCell.swift
//  AppList
//
//  Created by 钟逊超 on 2022/8/2.
//

import SwiftUI

struct AppCell: View {
    
    let model: AppModel
    
    @State var faviroute: Bool = false
    
    var body: some View {
        HStack {
            AsyncImage(
                url: URL(string: model.iconUrl),
                content: { image in
                    image.resizable()
                        .aspectRatio(contentMode: .fit)
                        .cornerRadius(8)
                },
                placeholder: {
                    ProgressView()
                }
            )
            .frame(width: 48, height: 48)
            
            VStack(alignment: .leading, spacing: 5) {
                Text(model.name)
                    .font(.title3)
                    .lineLimit(1)
                    .padding(.top, 10)
                Text(model.description)
                    .font(.caption)
                    .lineLimit(2)
                    .padding(.bottom, 10)
            }
            Spacer()
            Button(action: {
                self.faviroute = !self.faviroute
            }) {
                Image(systemName: faviroute ? "heart.fill" : "heart")
            }
        }
        .frame(height: 80)
        .padding(.leading, 16)
        .padding(.trailing, 16)
        .background(
            ZStack {
                RoundedRectangle(cornerRadius: 12)
                    .fill(Color.white)
            }
        )
        .padding(.horizontal)
    }
}

struct AppCell_Previews: PreviewProvider {
    static let appModel = AppModel(iconUrl: "https://is5-ssl.mzstatic.com/image/thumb/Purple122/v4/2c/ff/0f/2cff0f08-85a7-898d-d209-6f47e43d8ac5/logo_chat_2020q4_color-0-1x_U007emarketing-0-0-0-6-0-0-85-220.png/512x512bb.jpg", name: "App Track Name", description: "这是App的描述，可能会很长很长很长很长", id: "com.app.track")
    
    static var previews: some View {
        AppCell(model: self.appModel)
    }
}
