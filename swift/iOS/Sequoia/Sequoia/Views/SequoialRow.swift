//
//  SequoialRow.swift
//  Sequoia
//
//  Created by 徐锐 on 2024/4/11.
//

import SwiftUI
import UIKit

struct SequoialRow: View {
    @Environment(DataManager.self) var managerData
    //数据
    var appleData: AppleData
        
    //所在列表中的序号
    var appleDataIndex: Int {
        managerData.appleDatas.results.firstIndex(where: { $0.id == appleData.id })!
    }
    
    @State private var image: UIImage?
        
    var body: some View {
        HStack {
            //应用程序图标:60*60
            let imageUrl = URL(string: appleData.artworkUrl60)
            AsyncImage(url: imageUrl ?? URL(fileURLWithPath: ""),
                       placeholder: {
                            ActivityIndicator(style: .medium)
                        },
                       image: { Image(uiImage: $0).resizable() })
            .frame(width: 60, height: 60)
            .cornerRadius(15)

            //应用程序名称+描述
            VStack (alignment: .leading, content: {
                Text(appleData.trackName)
                    .lineLimit(1)
                    .frame(alignment: .leading)
                    .font(.headline)
                Text(appleData.description)
                    .lineLimit(/*@START_MENU_TOKEN@*/2/*@END_MENU_TOKEN@*/)
                    .font(.subheadline)
            })
            
            Spacer()
            
            //爱心按钮
            Button {
                favoriteAction()
            } label: {
                if appleData.isFavorite {
                    Image(systemName: "heart.fill")
                        .foregroundStyle(.red)
                } else {
                    Image(systemName: "heart")
                        .foregroundStyle(.gray)
                }
            }
        }
        .padding(EdgeInsets(top: 15, leading: 15, bottom: 15, trailing: 15))
        .background(Color.white)
        .listRowBackground(Color(UIColor(red: 244.0/256.0, green: 244.0/256.0, blue: 247.0/256.0, alpha: 1)))
        .cornerRadius(15)
        .listRowSeparator(.hidden)
    }

    //收藏
    func favoriteAction() {
        @Bindable var managerData = managerData
        managerData.appleDatas.results[appleDataIndex].isFavorite = !managerData.appleDatas.results[appleDataIndex].isFavorite
    }
}

#Preview {
    return Group {
        let appleData1: AppleData = AppleData(id: 1, trackId: 1, artworkUrl60: "https://is1-ssl.mzstatic.com/image/thumb/Purple122/v4/81/b6/25/81b6255c-1972-7ce6-24a2-148a710ce65c/logo_chat_2023q4_color-0-1x_U007emarketing-0-0-0-6-0-0-0-85-220-0.png/60x60bb.jpg", trackName: "sdf", description: "描述文字里是江东父老考上吉林")
        SequoialRow(appleData: appleData1)
        
        let appleData2: AppleData = AppleData(id: 3, trackId: 3, artworkUrl60: "https://is1-ssl.mzstatic.com/image/thumb/Purple122/v4/73/04/7b/73047bfb-9146-03d6-b4f6-94d33a8471e5/AppIcon-0-1x_U007emarketing-0-4-0-sRGB-0-85-220.png/60x60bb.jpg", trackName: "bbbbb", description: "dfsdasdfasdfasdfwewefafsfdsf")
        SequoialRow(appleData: appleData2)
    }
}
