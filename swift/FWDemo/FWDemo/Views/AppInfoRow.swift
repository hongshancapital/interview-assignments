//
//  AppInfoRow.swift
//  FWDemo
//
//  Created by wei feng on 2022/6/21.
//

import SwiftUI

struct AppInfoRow: View {
    @State var appInfo : AppInfo
    @State var inRequesting = false
    var body: some View {
        HStack(alignment: .center){
            AsyncImage(url: URL(string: appInfo.thumbUrl)){image in
                image.resizable()
            } placeholder: {
                ProgressView()
            }
            .frame(width: 50, height: 50, alignment: .center)
            .cornerRadius(8)
            
            VStack(alignment: .leading){
                Text(appInfo.name)
                    .font(.headline)
                    .lineLimit(1)
                Spacer()
                Text(appInfo.description ?? "")
                    .font(Font.system(size: 12))
                    .foregroundColor(Color(uiColor: .darkGray))
                    .lineLimit(2)
            }
            
            Spacer()
            
            if inRequesting {
                ProgressView()
            }else {
                Image(systemName: appInfo.isFavorite ? "heart.fill" : "heart")
                    .foregroundColor(appInfo.isFavorite ? .red : .black)
                    .onTapGesture {
                        inRequesting = true
                        /// 模拟一下收藏\取消收藏的网络请求
                        DispatchQueue.main.asyncAfter(deadline: .now() + 1) {
                            inRequesting = false
                            appInfo.isFavorite.toggle()
                        }
                    }
            }
            
        }
        .padding(10)
        .background(Color(uiColor: .white))
        .cornerRadius(8)
    }
}

struct AppInfoRow_Previews: PreviewProvider {
    static var previews: some View {
        let appInfo = AppInfo(
            id: 123456789,
            thumbUrl: """
                
            """,
            name: "Boloco",
            description: """
                Boloco 是我自主研发的一款创意类小App，欢迎大家下载体验，很有意思哦。
            """,
            isFavorite: false
        )
        AppInfoRow(appInfo: appInfo)
    }
}
